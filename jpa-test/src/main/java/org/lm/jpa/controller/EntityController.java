package org.lm.jpa.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.metamodel.EntityType;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.lm.jpa.entity.Book;
import org.lm.jpa.entity.Home;
import org.lm.jpa.entity.Recorded;
import org.lm.jpa.entity.User;
import org.lm.jpa.meta.ColumnMeta;
import org.lm.jpa.meta.EntityMeta;
import org.lm.jpa.service.Pager;
import org.lm.jpa.service.WebFormDataBinder;
import org.lm.jpa.ui.PostStatus;
import org.lm.jpa.util.EntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("entity")
@Transactional
public class EntityController {

	private static final Logger logger = LoggerFactory.getLogger(EntityController.class);

	@Autowired
	EntityManager em;
	static Map<String, Class<?>> entityTypes = new HashMap<String, Class<?>>();
	@Autowired
	private WebFormDataBinder webDataBinder;

	boolean inited = false;
	

	@PostConstruct
	void initEntites() {
		for (EntityType<?> e : em.getMetamodel().getEntities()) {
			entityTypes.put(e.getName(), e.getJavaType());
			logger.debug(" add entity {} : javaType={}", e.getName(), e.getJavaType());
		}

	}

	void mockData() {
		SimpleJpaRepository<User, Long> mock = new SimpleJpaRepository<User, Long>(User.class, em);
		for (int i = 0; i < 50; i++) {
			User u = new User();
			Home home = new Home();
			home.setAddress("长沙市");
			home.setName("home name ");

			u.setHome(home);
			u.setName("LMLML");

			Recorded recordInfo = new Recorded();
			recordInfo.setCreateDate(new Date());
			recordInfo.setUpDate(new Date());
			u.setRecordInfo(recordInfo);
			u.setRegDate(new Date());
			List<Book> userBooks = new ArrayList<Book>();
			Book b = new Book();
			b.setBookName("开发大全");
			b.setProductDate(new Date());
			userBooks.add(b);

			Book bb = new Book();
			bb.setBookName("开发大全2222");
			bb.setProductDate(new Date());
			userBooks.add(bb);

			u.setUserBooks(userBooks);
			mock.saveAndFlush(u);

		}

		inited = true;
	}

	@ModelAttribute(name = "entityType")
	public Class<?> entityType(@PathVariable("entity") String entity) {
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
	public Map<String, Object> dictionary() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<Object, Object> sexMap = new HashMap<>();
		sexMap.put(0,"男");
		sexMap.put(1,"女");
		map.put("sex", sexMap);
		return map;
	}

	@RequestMapping("/list/{entity}")
	public ModelAndView list(@ModelAttribute(name = "entityType") Class<?> entityType, Pager pager) {
		PagingAndSortingRepository repository = new SimpleJpaRepository(entityType, em);
		if (!inited)
			mockData();
		ModelAndView mv = new ModelAndView("/entity/list");
		Pageable page = new PageRequest(pager.getPage(), pager.getPageSize());
		Page result = repository.findAll(page);
		mv.addObject("page", result);

		return mv;
	}
	
	@RequestMapping("/pick/{entity}")
	public ModelAndView pick(@ModelAttribute(name = "entityType") Class<?> entityType, Pager pager,boolean multivalue) {
		PagingAndSortingRepository repository = new SimpleJpaRepository(entityType, em);
		if (!inited)
			mockData();
		ModelAndView mv = new ModelAndView("/entity/pick");
		Pageable page = new PageRequest(pager.getPage(), pager.getPageSize());
		Page result = repository.findAll(page);
		mv.addObject("page", result);
		mv.addObject("multivalue",multivalue);
		return mv;
	}
	
 
	

	@RequestMapping("/detail/{entity}/{id}")
	public ModelAndView detail(@ModelAttribute(name = "entityType") Class<?> entityType, @PathVariable("id") Integer id) {
		SimpleJpaRepository repository = new SimpleJpaRepository(entityType, em);

		ModelAndView mv = new ModelAndView("/entity/detail");
		Object detail = repository.findOne(id);
		mv.addObject("entity", detail);

		return mv;
	}

	@RequestMapping(value = "/edit/{entity}/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute(name = "entityType") Class<?> entityType, @PathVariable("id") Integer id) {
		SimpleJpaRepository repository = new SimpleJpaRepository(entityType, em);

		ModelAndView mv = new ModelAndView("/entity/edit");
		Object detail = repository.findOne(id);
		mv.addObject("entity", detail);

		return mv;
	}

	@RequestMapping(value = "/edit_save/{entity}", method = RequestMethod.POST)
	public ModelAndView edit_post(final HttpServletRequest req,
			@ModelAttribute(name = "entityType") Class<?> entityType,
			@ModelAttribute("columns") List<ColumnMeta> columMeta,
			@PathVariable("entity") String entity,
			@RequestParam MultiValueMap<String, Object> map 
		 

	) {
		SimpleJpaRepository repository = new SimpleJpaRepository(entityType, em);

		ModelAndView mv = new ModelAndView("/entity/edit");
		Map<String, Object> singleValueMap = map.toSingleValueMap();
		logger.debug("===edit post request body ::{}", singleValueMap);
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
					FileCopyUtils.copy(m.getValue().getBytes(), new File(req.getServletContext().getRealPath("/upload/" + fileName)));
					singleValueMap.put(columName, "upload/" + fileName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		Object id = singleValueMap.get("id");
		Object obj = repository.findOne(Integer.parseInt(id.toString()));
		if (obj == null) {
			logger.error("修改entity失败，根据id {},未找到{}  entity", id,entityType);
			throw new  EntityNotFoundException("未找到entity "+entityType);
		}
		webDataBinder.bind(obj, new MutablePropertyValues(singleValueMap));

		repository.save(obj);
		mv.addObject("entity", obj);
		logger.debug("=======update entity value ");
		//TODO:: validate entity
		
		mv.addObject("postStatus", PostStatus.Success);
		
		
		mv.setViewName("redirect:/entity/list/"+entity);
		return mv;
	}
	
	@RequestMapping("/delete/{entity}")
	public String delete(@ModelAttribute("entityType") Class<?> entityType,@PathVariable String entity,String ids){

		SimpleJpaRepository repository = new SimpleJpaRepository(entityType, em);
		for(String id:ids.split("[,]")){
			if(StringUtils.hasLength(id))
			repository.delete(Integer.parseInt( id));
		}
		
		return "redirect:/entity/list/"+entity;
	}
}
