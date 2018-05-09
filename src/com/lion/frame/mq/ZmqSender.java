package com.lion.frame.mq;

public class ZmqSender extends _ZMQ{

	public ZmqSender(MqConfig mqConfig) {
		super(mqConfig);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	public void send(byte[] msg){
		zmqSocket.send(msg, 0);
	}
	
}