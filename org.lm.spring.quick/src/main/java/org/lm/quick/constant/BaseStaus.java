package org.lm.quick.constant;

public class BaseStaus {
	public final static int SUCCESS=0;
	public final static int ERROR=-1;
	private int code=SUCCESS;
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
	public BaseStaus(){}
	public BaseStaus(int code,String msg){
		this.code=code;
		this.msg=msg;
	}
	public static  BaseStaus success=new BaseStaus(SUCCESS,null);
	
}
