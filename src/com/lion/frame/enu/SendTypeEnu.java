package com.lion.frame.enu;

public enum SendTypeEnu {
	NO("no","不推送"),
	TASK("task","定时任务"),
	MQ("mq","mq推送")
	
	;
	
	
	
	private SendTypeEnu(String code, String desc) {
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
