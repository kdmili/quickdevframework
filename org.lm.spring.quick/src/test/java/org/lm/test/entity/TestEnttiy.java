package org.lm.test.entity;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lm.quick.Booter;
import org.lm.quick.config.EntityAutoConfig;
import org.lm.quick.entity.Dictionary;
import org.lm.quick.entity.Users;
import org.lm.test.cust.MyJpaReposotoryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.core.support.AbstractRepositoryMetadata;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest(showSql = true)
@SpringBootTest(classes = Booter.class)
public class TestEnttiy {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	DataSource ds;

	private SimpleJpaRepository<Users, Integer> res;

	@Before
	public void setup() {
		res = new SimpleJpaRepository<Users, Integer>(Users.class, entityManager.getEntityManager());

		new EntityAutoConfig().init_data(ds);

	}

	@Test
	public void test_insert() throws Exception {
		// roomRes.save(classRoom);
		// }
		Users u = new Users();
		u.setUsername("test");
		res.saveAndFlush(u);

		u.setUsername("test2");
		res.save(u);

		Users find = res.findOne(1);
		System.out.println(find.getUsername());
		// mock web request update classroom


	}

	@Test
	public void test_update() throws Exception {
//		ClassRoom classRoom = null;
//		// for (int i = 0; i < 10; i++) {
//		classRoom = new ClassRoom();
//		classRoom.setClassName("cn union ");
//		classRoom.setClassNo("no1");
//		roomRes.saveAndFlush(classRoom);
		// }

		Users u = new Users();
		u.setUsername("test");
//		classRoom = new ClassRoom();
//		classRoom.setClassNo("no2");
//		u.setClassRoom(classRoom);
		res.saveAndFlush(u); // insert new user and classroom

		u.setUsername("test2");// update username and classroom id

//		ClassRoom room2 = roomRes.findOne(1);

//		u.setClassRoom(room2);
		res.saveAndFlush(u);

		Users find = res.findOne(1);
		System.out.println(find.getUsername());
//		System.out.println(find.getClassRoom().getClassNo());
		// mock web request update classroom

//		ClassRoom room = roomRes.findOne(1);
//		System.out.println(room.getClassNo());
//		Assert.isTrue(find.getClassRoom().getClassNo().equals("no1"));
	}

	@Test
	public void test_jpa_query() throws Exception {
		Users u = new Users();
		u.setUsername("test");
		List<Users> users = res.findAll();
		Assert.notEmpty(users);
		for (Users uu : users) {
			System.out.println(uu.getUsername() + "|" + uu.getAge());
		}
		Users find = res.findOne(Example.of(u));
		Assert.notNull(find);
		System.out.println(find.getPassword());
	}

	@Test
	public void test_respositoryFactory() throws Exception {
		JpaRepositoryFactory jpaFact = new JpaRepositoryFactory(this.entityManager.getEntityManager());
		UserRepository jpaRepo = jpaFact.getRepository(UserRepository.class);
		System.out.println(jpaRepo);
	}

	@Test
	public void test_cust_repositoryFact() throws Exception {
		MyJpaReposotoryFactory f = new MyJpaReposotoryFactory(this.entityManager.getEntityManager(), Users.class);
		JpaRepository res = f.getRepository(JpaRepository.class);
		Assert.notNull(res);
		System.out.println(res);
		List findlist = res.findAll();
		System.out.println(findlist.size());
	}

	interface UserRepository extends Repository<Users, Long> {

	}

	@RepositoryDefinition(domainClass = Users.class, idClass = Long.class)
	interface t {

	}

	public static void main(String[] args) {
		Class<?> t = AbstractRepositoryMetadata.getMetadata(JpaRepository.class).getDomainType();
		System.out.println(t);
		t = AbstractRepositoryMetadata.getMetadata(t.class).getDomainType();
		System.out.println(t);
	}

	@Test
	public void test_loadDictionary() throws Exception {
		MyJpaReposotoryFactory f = new MyJpaReposotoryFactory(this.entityManager.getEntityManager(), Dictionary.class);
		JpaRepository<Dictionary, Long> res = f.getRepository(JpaRepository.class);
		List<Dictionary> lst = res.findAll();
		for (Dictionary d : lst) {
			System.out.println(d.getKey());
			System.out.println(d.getValue());
		}
	}

}
