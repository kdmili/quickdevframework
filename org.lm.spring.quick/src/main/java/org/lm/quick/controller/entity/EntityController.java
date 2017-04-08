package org.lm.quick.controller.entity;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.metamodel.EntityType;
import javax.servlet.http.HttpServletRequest;

import org.lm.quick.controller.BaseEntityClass;
import org.lm.quick.entity.BaseEntity;
import org.lm.quick.entity.Dictionary;
import org.lm.quick.entity.Users;
import org.lm.quick.meta.ColumnMeta;
import org.lm.quick.meta.EntityMeta;
import org.lm.quick.service.MyJpaReposotoryFactory;
import org.lm.quick.service.Pager;
import org.lm.quick.service.WebFormDataBinder;
import org.lm.quick.ui.PostStatus;
import org.lm.quick.util.EntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("entity")
@Transactional(propagation = Propagation.REQUIRED)
public class EntityController {

	private static final Logger logger = LoggerFactory.getLogger(EntityController.class);
	private static final String dic_key_Cache = "CACHE_DICTIONARY";
	@Autowired
	EntityManager em;
	static final Map<String, Class<? extends BaseEntity>> entityTypes = new HashMap<String, Class<? extends BaseEntity>>();
	@Autowired
	private WebFormDataBinder webDataBinder;

	@PostConstruct
	void initEntites() {
		for (EntityType<?> e : em.getMetamodel().getEntities()) {
			entityTypes.put(e.getName(), (Class<? extends BaseEntity>) e.getJavaType());
			entityTypes.put(e.getJavaType().getSimpleName(), (Class<? extends BaseEntity>) e.getJavaType());
			logger.debug(" add entity {} : javaType={}", e.getName(), e.getJavaType());
		}

	}

	protected <T> JpaRepository<T, Integer> getJpaRepository(Class<T> entityType) {
		MyJpaReposotoryFactory jpafac = new MyJpaReposotoryFactory(this.em, entityType);
		return jpafac.getRepository(JpaRepository.class);
	}

	@ModelAttribute(name = "entityType")
	public Class<? extends BaseEntity> entityType(@PathVariable("entity") String entity) {
		return entityTypes.get(entity);
	}

	@ModelAttribute(name = "entityName")
	public String entityName(@PathVariable("entity") String entity) {
		return entity;
	}

	@ModelAttribute(name = "entityMeta")
	public EntityMeta entityMeta(@ModelAttribute(name = "entityType") Class<?> entityType) {
		return EntityUtil.getEntityMeta(em.getMetamodel(), entityType);
	}

	@ModelAttribute(name = "columns")
	public List<ColumnMeta> columns(@ModelAttribute(name = "entityType") Class<?> entityType) {
		return EntityUtil.getColumnMeta(em.getMetamodel(), entityType);
	}

	@ModelAttribute(name = "dictionary")
	@Cacheable(key = dic_key_Cache)
	public Map<String, Object> dictionary() {
		HashMap<String, Object> mapDictionary = new HashMap<String, Object>();
		Iterator alldict = getJpaRepository(Dictionary.class).findAll().iterator();
		while (alldict.hasNext()) {
			Dictionary l = (Dictionary) alldict.next();
			String k = l.getKey();
			mapDictionary.put(k, l.getValue());
		}
		logger.debug("load dictionary from db");
		return mapDictionary;
	}

	@RequestMapping("/list/{entity}")
	public ModelAndView list(@ModelAttribute(name = "entityType") Class<?> entityType, Pager pager, Sort sort) {
		JpaRepository<?, Integer> repository = getJpaRepository(entityType);
		ModelAndView mv = new ModelAndView("/entity/list");
		Pageable page = new PageRequest(pager.getPage(), pager.getPageSize(), sort);
		Page<?> result = repository.findAll(page);
		mv.addObject("page", result);

		return mv;
	}

	@RequestMapping("/pick/{entity}")
	public ModelAndView pick(@ModelAttribute(name = "entityType") Class<?> entityType, Pager pager, boolean multivalue) {
		JpaRepository<?, Integer> repository = getJpaRepository(entityType);
		ModelAndView mv = new ModelAndView("/entity/pick");
		Pageable page = new PageRequest(pager.getPage(), pager.getPageSize());
		Page<?> result = repository.findAll(page);
		mv.addObject("page", result);
		mv.addObject("multivalue", multivalue);
		return mv;
	}

	@RequestMapping("/detail/{entity}/{id}")
	public ModelAndView detail(@ModelAttribute(name = "entityType") Class<?> entityType, @PathVariable("id") Integer id) {
		JpaRepository<?, Integer> repository = getJpaRepository(entityType);
		ModelAndView mv = new ModelAndView("/entity/detail");
		Object detail = repository.findOne(id);
		mv.addObject("entity", detail);

		return mv;
	}

	@RequestMapping(value = "/edit/{entity}/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute(name = "entityType") Class<?> entityType, @PathVariable("id") Integer id) {
		JpaRepository<?, Integer> repository = getJpaRepository(entityType);
		ModelAndView mv = new ModelAndView("/entity/edit");
		Object detail = repository.findOne(id);
		mv.addObject("entity", detail);

		return mv;
	}

	@RequestMapping(value = "/edit_save/{entity}", method = RequestMethod.POST)
	public ModelAndView edit_post(final WebRequest req,
			@ModelAttribute(name = "entityType") Class<? extends BaseEntity> entityType,
			@ModelAttribute("columns") List<ColumnMeta> columMeta, @PathVariable("entity") String entity

	) {
		JpaRepository repository = getJpaRepository(entityType);
		ModelAndView mv = new ModelAndView("/entity/edit");
		String  id = req.getParameter("id");
		Object obj = repository.findOne(Integer.parseInt(id));
		if (obj == null) {
			logger.error("修改entity失败，根据id {},未找到{}  entity", id, entityType);
			throw new EntityNotFoundException("未找到entity " + entityType);
		}
		BindingResult bindResult = bindEntity(req, obj);
		if (bindResult.hasErrors()) {
			ModelAndView errormv = new ModelAndView("/entity/edit");
			errormv.addObject(entityType.getSimpleName(), obj);
			errormv.addAllObjects(bindResult.getModel());
			em.clear();
			return errormv;
		}
		repository.saveAndFlush(obj);

		logger.debug("=======update entity value ");

		mv.addObject("postStatus", PostStatus.Success);

		mv.setViewName("redirect:/entity/list/" + entity);
		return mv;
	}

	@RequestMapping("/delete/{entity}")
	public String delete(@ModelAttribute("entityType") Class<?> entityType, @PathVariable String entity, String ids) {

		JpaRepository repository = getJpaRepository(entityType);
		for (String id : ids.split("[,]")) {
			if (StringUtils.hasLength(id))
				repository.delete(Integer.parseInt(id));
		}

		return "redirect:/entity/list/" + entity;
	}

	@RequestMapping(value = "/create/{entity}")
	public ModelAndView create(@ModelAttribute("entityType") Class<?> entityType) {
		ModelAndView mv = new ModelAndView("entity/create");
		Object entity;
		try {
			entity = entityType.newInstance();
			mv.addObject("entity", entity);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return mv;
	}

	@RequestMapping(value = "/create/{entity}", method = RequestMethod.POST)
	public ModelAndView create(WebRequest req, @ModelAttribute("entityType") Class<?> entityType,
			@PathVariable String entity) {

		ModelAndView mv = new ModelAndView("redirect:/entity/list/" + entity);
		JpaRepository repository = getJpaRepository(entityType);
		BaseEntity f = (BaseEntity) repository.findOne(1);
		repository.save(f);
		Object obj = ReflectUtils.newInstance(entityType);

		BindingResult bindResult = bindEntity(req, obj);
		if (bindResult.hasErrors()) {
			mv.setViewName("entity/create");
			mv.addAllObjects(bindResult.getModel());
			return mv;
		}
		repository.saveAndFlush(obj);
		return mv;

	}

	protected BindingResult bindEntity(WebRequest req, Object entity) {

		if (req instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest multireq = ((MultipartHttpServletRequest) req);
			Map<String, MultipartFile> fileMap = multireq.getFileMap();
			for (Entry<String, MultipartFile> m : fileMap.entrySet()) {

				if (m.getValue().isEmpty()) {
					continue;
				}
				String columName = m.getKey();
				String fileName = m.getValue().getOriginalFilename();
				int index = fileName.indexOf(".");
				fileName = UUID.randomUUID().toString() + fileName.substring(index);
				logger.info("===upload file，colunm={},file name {}", columName, fileName);
				try {
					FileCopyUtils.copy(m.getValue().getBytes(), new File(((MultipartHttpServletRequest) req)
							.getServletContext().getRealPath("/upload/" + fileName)));
					req.getParameterMap().put(columName, new String[] { "upload/" + fileName });
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return this.webDataBinder.bind(entity, req);

	}

}
