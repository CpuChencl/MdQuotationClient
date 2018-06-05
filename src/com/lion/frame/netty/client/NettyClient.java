package com.lion.frame.netty.client;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.lion.frame.logger.Log4jManager;
import com.lion.frame.websocket.SocketServer;
import com.lion.redis.dao.ContractRedisDao;
import com.lion.sp.push.PushThread;

@Lazy(false)
@Component
public class NettyClient {
	@Value("${NETTY.SERVER.IP}")
	private String host;
	@Value("${NETTY.SERVER.PORT}")
	private int port;
	@Value("${NETTY.CLIENT.INFO}")
	private String clientInfo;
	private static final Logger log = Log4jManager.get();
	
	@Autowired
	private PushThread pushThread;
	@Autowired
	private ContractRedisDao contractRedisDao;
	@Autowired
	private SocketServer socketServer;
	private NettyClientStart client;
	
	@PostConstruct
	public void connectServer(){
		client = new NettyClientStart(host, port, clientInfo, pushThread, contractRedisDao, socketServer);
		client.start(client);
        try {
			client.sendData();
		} catch (Exception e) {
			log.error("发送数据失败："+e.getMessage());
		}
	}

}
