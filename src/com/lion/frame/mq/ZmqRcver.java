package com.lion.frame.mq;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.zeromq.ZMQException;

public class ZmqRcver extends _ZMQ {
	
	private IMqCallInterface callInterface;

	public ZmqRcver(MqConfig mqConfig, IMqCallInterface mqCallback) {
		super(mqConfig);
		// TODO Auto-generated constructor stub
		this.callInterface = mqCallback;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
		newSingleThreadExecutor.submit(new RecverThread());
	}
	
	class RecverThread implements Runnable {

		boolean wait = true;
		public void run() {
			// TODO Auto-generated method stub
			while (wait) {
	            byte[] request;
	            try {
	                request = zmqSocket.recv(0);
	                callInterface.onMessage(request);
	                
	            } catch (ZMQException e) {
	                throw e;
	            }
	        } // while(wait)
		}
		
	}

	
}