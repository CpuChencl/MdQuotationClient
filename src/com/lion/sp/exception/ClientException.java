package com.lion.sp.exception;

import com.lion.frame.enu.ExceptionEnu;

public class ClientException extends Exception {
	
	private static final long serialVersionUID = -2846006885368179292L;
	
	private ExceptionEnu enu;
	private String code;
	private String msg;
	
	public ClientException (ExceptionEnu enu){ 
		super(enu.getDesc()); 
		this.code = enu.getCode();
		this.msg = enu.getDesc();
		this.enu = enu;
	}

	public ClientException (ExceptionEnu enu, String msg){ 
		super(msg); 
		this.code = enu.getCode();
		this.msg = msg;
		this.enu = enu;
	}
	
	public ClientException (String code, String msg){ 
		super("code : " +code + "，msg : " + msg); 
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ExceptionEnu getEnu() {
		return enu;
	}
	
}