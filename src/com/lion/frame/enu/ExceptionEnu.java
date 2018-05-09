package com.lion.frame.enu;

public enum ExceptionEnu {
	SUCCESS("0","成功"),
	PARAMTER_ERROR("000001","参数错误"),
	CHANNEL_ERROR("000002","通道错误"),
	
	ERROR("999999","系统异常，请联系客服人员或管理员"),
	;
	
	
	
	private ExceptionEnu(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	private String code;
	private String desc;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
