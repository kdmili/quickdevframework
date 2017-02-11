package org.lm.test.entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lm.jpa.Booter;
import org.lm.jpa.entity.ClassRoom;
import org.lm.jpa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest(showSql = true)
@SpringBootTest(classes = Booter.class)
public class TestEnttiy {
	@Autowired
	private TestEntityManager entityManager;

	private SimpleJpaRepository<User, Integer> res;
	private SimpleJpaRepository<ClassRoom, Integer> roomRes;

	@Before
	public void setup() {
		res = new SimpleJpaRepository<User, Integer>(User.class, entityManager.getEntityManager());
		roomRes = new SimpleJpaRepository<ClassRoom, Integer>(ClassRoom.class, entityManager.getEntityManager());
	}

	@Test
	public void test_insert() throws Exception {
		ClassRoom classRoom = null;
		// for (int i = 0; i < 10; i++) {
		classRoom = new ClassRoom();
		classRoom.setClassName("cn union ");
		classRoom.setClassNo("no88888888");
		// roomRes.save(classRoom);
		// }

		User u = new User();
		u.setName("test");

		u.setClassRoom(classRoom);
		res.saveAndFlush(u);

		u.setName("test2");
		res.save(u);

		User find = res.findOne(1);
		System.out.println(find.getName());
		System.out.println(find.getClassRoom().getClassName());
		// mock web request update classroom

		ClassRoom room = roomRes.findOne(1);
		System.out.println(room.getClassName());
		Assert.isTrue(find.getClassRoom().getClassNo().equals("no88888888"));

	}

	@Test
	public void test_update() throws Exception {
		ClassRoom classRoom = null;
		// for (int i = 0; i < 10; i++) {
		classRoom = new ClassRoom();
		classRoom.setClassName("cn union ");
		classRoom.setClassNo("no1");
		roomRes.saveAndFlush(classRoom);
		// }

		User u = new User();
		u.setName("test");
		classRoom = new ClassRoom();
		classRoom.setClassNo("no2");
		u.setClassRoom(classRoom);
		res.saveAndFlush(u); // insert new user and classroom

		u.setName("test2");// update username and classroom id

		ClassRoom room2 = roomRes.findOne(1);

		u.setClassRoom(room2);
		res.saveAndFlush(u);

		User find = res.findOne(1);
		System.out.println(find.getName());
		System.out.println(find.getClassRoom().getClassNo());
		// mock web request update classroom

		ClassRoom room = roomRes.findOne(1);
		System.out.println(room.getClassNo());
		Assert.isTrue(find.getClassRoom().getClassNo().equals("no1"));
	}

}
