package org.lm.quick.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.lm.quick.ui.annotation.Ordered;
import org.lm.quick.ui.annotation.UIField;
import org.lm.quick.ui.annotation.UIField.FType;

@Entity(name = "users")
@Ordered("id,username,photo,file,recordInfo,home,userBooks,classRoom")
public class Users extends BaseEntity {

	@NotBlank(message = "登录账号不能为空")
	@NaturalId
	private String username;
	@Length(min = 6, max = 50, message = "密码长度6~50")
	@UIField(hidden = true)
	private String password;
	@Embedded
	private Recorded recordInfo;
	@org.hibernate.validator.constraints.Range(min = 1, max = 200, message = "年龄只能1~200之间")
	private Integer age  ;

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(updatable = false)
	private Date regDate;


	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "homeId")
	private Home home;

	@UIField(ftype = FType.Img)
	private String photo;

	@UIField(ftype = FType.File)
	private String file;
	@UIField(ftype = FType.Dictionary, dictionaryKey = "sex")
	private Integer sex;

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return this.username;
	}

	public Recorded getRecordInfo() {
		return recordInfo;
	}

	public void setRecordInfo(Recorded recordInfo) {
		this.recordInfo = recordInfo;
	}

}
