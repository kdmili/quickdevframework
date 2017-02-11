package org.lm.jpa;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.lm.jpa.meta.ColumnMeta;
import org.lm.jpa.service.UserRepository;
import org.lm.jpa.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Booter {
	@Autowired
	UserRepository userRepository;
	@Autowired
	EntityManager entityManager;

	public static void main(String[] args) {
	System.setProperty("org.springframework.boot.logging.LoggingSystem","org.springframework.boot.logging.logback.LogbackLoggingSystem");
		SpringApplication app=new SpringApplication(Booter.class);
		app.setBannerMode(Mode.OFF);
		app.setHeadless(true);
		
		//app.setWebEnvironment(false);
		ConfigurableApplicationContext ap =app.run(Booter.class, args);
	//	ap.getBean(TestService.class).testQuery();

	}

	 
	void test_entity_manager() {

//		for (EntityType<?> en : this.entityManager.getMetamodel().getEntities()) {
//			if (!en.getName().equals(User.class.getSimpleName()))
//				continue;
//			System.out.println(en.getName());
//			for (Attribute<?, ?> at : en.getAttributes()) {
//				System.out.println(at.getName());
//			}
//		}

//		for(ManagedType<?> mt:this.entityManager.getMetamodel().getManagedTypes()){
//			System.out.println(mt);
//		}
		// for(EmbeddableType<?>
		// emb:this.entityManager.getMetamodel().getEmbeddables()){
		// System.out.println(emb);
		// }
	
		List<ColumnMeta> list = EntityUtil.getColumnMeta(this.entityManager.getMetamodel(), org.lm.jpa.entity.Book.class);
		for(ColumnMeta c:list){
			System.out.println(c.getColumnName()+","+c.getPersistype());
					
		}
		
	}

}
