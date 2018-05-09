package com.lion.frame.mq;

public interface IMqCallInterface {
	
	public void onMessage(byte[] req);
	
}
