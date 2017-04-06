package org.lm.quick.ui.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UIField {
	public enum FType {
		Auto,Email,Date,DateTime,Time,Number,Url,File,Img, Dictionary, Cust
	}

	public FType ftype()  default FType.Auto;
	
	public String dictionaryKey() default "";
	//自定义ui的显示
	public String readHtml() default "";
	//自定义ui编辑
	public String editHtml() default "";
	
	public boolean hidden() default false;
}
