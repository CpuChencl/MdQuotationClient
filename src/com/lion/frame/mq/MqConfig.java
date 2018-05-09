package com.lion.frame.mq;

public class MqConfig {
	
	public enum SocketType {  
		PUB, SUB, REQ, REP; 
	} 
	
	public enum MqType{
		CONNECT, BIND;
	}
	
	private String addr;
	private int port;
	private String model;
	private String receiveHighWatermark;
	private String[] subObj;
	private String mqType;
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getReceiveHighWatermark() {
		return receiveHighWatermark;
	}
	public void setReceiveHighWatermark(String receiveHighWatermark) {
		this.receiveHighWatermark = receiveHighWatermark;
	}
	public String[] getSubObj() {
		return subObj;
	}
	public void setSubObj(String[] subObj) {
		this.subObj = subObj;
	}
	public String getMqType() {
		return mqType;
	}
	public void setMqType(String mqType) {
		this.mqType = mqType;
	}
}