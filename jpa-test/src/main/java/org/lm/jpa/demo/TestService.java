package org.lm.jpa.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.lm.jpa.entity.Book;
import org.lm.jpa.entity.User;
import org.lm.jpa.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TestService {
	@Autowired
	UserRepository userRepository;
	
	
	 
	protected void initData() {
		User u = new User();
		u.setName("test user name ");

		List<Book> books = new ArrayList<Book>();
		u.setUserBooks(books);
		Book b = new Book();
		b.setBookName("java");
		b.setProductDate(new Date());
		 
		books.add(b);

		Book a = new Book();
		a.setBookName("C#");
		a.setProductDate(new Date());
		 
		books.add(a);

		userRepository.save(u);
	}

	public void testQuery() {

		List<User> users = userRepository.findAll();
		System.out.println("users::" + users.size());
		System.out.println(users);
		Collection<Book> books = users.get(0).getUserBooks();
		System.out.println("books::" + books.size());
		System.out.println(books);
		
		
		 
		

	}

}
