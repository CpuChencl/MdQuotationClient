package com.lion.frame.websocket;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.lion.common.utils.Tools;
import com.lion.frame.logger.Log4jManager;
import com.lion.sp.service.MdQuotationImpl;


@Component
@Lazy(false)
public class SocketServer {
	private static final Logger log = Log4jManager.get();
	private SocketIOServer server;
	
	private Map<UUID, SocketIOClient> sessionMap = new ConcurrentHashMap<UUID, SocketIOClient>();
	
	private Map<UUID, SocketIOClient> sessionMapList = new ConcurrentHashMap<UUID, SocketIOClient>();
	
	@Value("${WEBSOCKET.IP}")
	private String ip;
	@Value("${WEBSOCKET.PORT}")
	private Integer port;
	
	@Autowired
	private MdQuotationImpl mdQuotationImpl;
	
	@PostConstruct
	public void init(){
		Configuration config = new Configuration();
		config.setHostname(ip);
	    config.setPort(port);
	    config.setPingTimeout(30000);
	    config.getSocketConfig().setReuseAddress(true);
	    server = new SocketIOServer(config);
	    server.addEventListener("registerEvent", String.class, new DataListener<String>() {
            @Override
            public void onData(final SocketIOClient client, String data, final AckRequest ackRequest) {
            	UUID id = client.getSessionId();
            	Map<String, Object> ret = mdQuotationImpl.active(null, null);
//            	log.info("return datalist [{}]", Tools.toJson(ret));
            	Map<String,Object> params = Tools.jsonToObj(data, Map.class);
            	client.sendEvent("mdListEvent", ret);
            	//支持list方式和一个一个发送
            	if (params != null && params.get("flag") != null && "list".equals(params.get("flag").toString())) {
            		sessionMapList.put(id, client);
				} else {
					sessionMap.put(id, client);
				}
            }
        });
	    
	    server.addDisconnectListener(new DisconnectListener() {
			
			@Override
			public void onDisconnect(SocketIOClient client) {
				UUID id = client.getSessionId();
				log.info("链接关闭：[{}]", id.toString());
				sessionMap.remove(id);
				sessionMapList.remove(id);
			}
		});
	    
	    server.addConnectListener(new ConnectListener() {
			
			@Override
			public void onConnect(SocketIOClient client) {
				UUID id = client.getSessionId();
//				sessionMap.put(id, client);
				log.info("创建链接：[{}]", id.toString());
			}
		});
        server.start();
	}
	
	/*
	 * 推送期货行情信息
	 */
	public void sendMdData(Map<String, String> data){
		BroadcastOperations bo = new BroadcastOperations(Collections.unmodifiableCollection(sessionMap.values()), server.getConfiguration().getStoreFactory());
//		log.info("return push data [{}]", Tools.toJson(data));
		bo.sendEvent("mdDataEvent", data);
	}
	
	public void sendMdDataList(List<Map<String, String>> data){
		BroadcastOperations bo = new BroadcastOperations(Collections.unmodifiableCollection(sessionMapList.values()), server.getConfiguration().getStoreFactory());
//		log.info("return push data [{}]", Tools.toJson(data));
		bo.sendEvent("mdDataEvent", data);
	}
	
}