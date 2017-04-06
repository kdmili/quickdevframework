package org.lm.quick.ui;

public class PostStatus {
	
	private int code;
	private String msg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public PostStatus(int code,String msg){
		this.code=code;
		this.msg=msg;
	}
	public static PostStatus Success=new PostStatus(0, null);
	
	
}
