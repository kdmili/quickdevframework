package org.lm.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Book extends BaseEntity{
	
	private String bookName;
	private Date productDate;
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Date getProductDate() {
		return productDate;
	}

	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}

	@Override
	public String toString() {
		return this.bookName + "@" + this.userId;
	}

}
