package org.lm.jpa.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import org.hibernate.annotations.Type;
import org.lm.jpa.ui.annotation.Ordered;
import org.lm.jpa.ui.annotation.UIField;
import org.lm.jpa.ui.annotation.UIField.FType;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Ordered("id,name,photo,file,recordInfo,home,userBooks,classRoom")
public class User extends BaseEntity {

	private String name;
	@Embedded
	private Recorded recordInfo;
	
	@Column(updatable = false)
	private Date regDate;

	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private List<Book> userBooks;

	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="homeId")
	private Home home;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="classId")
	private ClassRoom classRoom;
	
	@UIField(ftype=FType.Img)
	private String photo;
	
	@UIField(ftype=FType.File)
	private String file;
	@UIField(ftype=FType.Dictionary,dictionaryKey="sex")
	private int sex;
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
	
		
	public Home getHome() {
		return home;
	}
	public void setHome(Home home) {
		this.home = home;
	}
	
	public void setClassRoom(ClassRoom classRoom) {
		this.classRoom = classRoom;
	}
	public ClassRoom getClassRoom() {
		return classRoom;
	}
	
	
	public List<Book> getUserBooks() {
		return userBooks;
	}

	public void setUserBooks(List<Book> userBooks) {
		this.userBooks = userBooks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public Recorded getRecordInfo() {
		return recordInfo;
	}

	public void setRecordInfo(Recorded recordInfo) {
		this.recordInfo = recordInfo;
	}

}
