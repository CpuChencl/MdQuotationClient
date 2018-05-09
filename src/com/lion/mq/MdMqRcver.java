package com.lion.mq;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.lion.frame.mq.IMqCallInterface;
import com.lion.frame.mq.MqConfig;
import com.lion.frame.mq.ZmqRcver;
import com.lion.frame.websocket.SocketServer;
import com.lion.redis.dao.ContractRedisDao;
import com.lion.sp.mqprocess.ProcessRtnMdMsg;
import com.lion.sp.push.PushThread;

//@Lazy(false)
//@Component
public class MdMqRcver {
	
	@Value("${mdRcvMq.addr}")
	private String addr;
	
	@Value("${mdRcvMq.port}")
	private int port;
	
	@Value("${mdRcvMq.model}")
	private String model;
	
	@Value("${mdRcvMq.type}")
	private String type;
	
	@Autowired
	private ContractRedisDao contractRedisDao;
	@Autowired
	private SocketServer socketServer;
	
	private ZmqRcver mdRcver;
	
	private ExecutorService executor;
	@Autowired
	private PushThread pushThread;
	
//	@PostConstruct
	public void init(){
		
		MqConfig mdRcverConfig = new MqConfig();
		mdRcverConfig.setAddr(addr);
		mdRcverConfig.setPort(port);
		mdRcverConfig.setModel(model);
		mdRcverConfig.setMqType(type);
		
		executor = Executors.newFixedThreadPool(5);
		mdRcver = new ZmqRcver(mdRcverConfig, new IMqCallInterface() {
			
			@Override
			public void onMessage(byte[] req) {
				ProcessRtnMdMsg process = new ProcessRtnMdMsg(req, contractRedisDao, socketServer, pushThread);
//			    executor.submit(process);
			}
		}); 
	}
	
	
}