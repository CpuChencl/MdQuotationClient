package com.lion.sp.push;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lion.frame.websocket.SocketServer;

@Component("pushThread")
public class PushThread {
	@Value("${WEBSOCKET.PUSH.TIME}")
	private Long pushTime;
	@Value("${WEBSOCKET.TICK.FLUSH.TIME}")
	private Long tickFlushTime;
	@Autowired
	private SocketServer socketServer;
	private Map<String, PushData> dataMap = new ConcurrentHashMap<String, PushData>();
	private Thread pushThread;
	
	@PostConstruct
	public void push(){
		pushThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					List<PushData> dataList = new ArrayList<PushData>(dataMap.values());
					List<Map<String, String>> sendList = new ArrayList<Map<String, String>>();
					for (PushData pd : dataList) {
						if (pd.getPushStatus()) {
							pd.setPushStatus(false);
							socketServer.sendMdData(pd.getMap());
							sendList.add(pd.getMap());
						}
					}
					if (!sendList.isEmpty()) {
						socketServer.sendMdDataList(sendList);
					}
					dataList.clear();
					dataList = null;
					try {
						Thread.sleep(pushTime);//每隔100毫秒推送一次
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		});
		pushThread.start();
	}
	
	public void setPushData(String contractNo, Map<String, String> map, Long timeS){
		PushData pd = dataMap.get(contractNo);
		if (pd == null) {
			createPushData(contractNo, map, timeS);
		} else {
			push(pd, map, timeS);
		}
	}
	
	private synchronized void createPushData(String contractNo, Map<String, String> map, Long timeS){
		PushData pd = dataMap.get(contractNo);
		if (pd == null) {
			pd = new PushData();
			dataMap.put(contractNo, pd);
		}
		push(pd, map, timeS);
	}
	
	private void push(PushData pd, Map<String, String> map, Long timeS){
		if (timeS - pd.getTimeS() >= tickFlushTime && !pd.getPushStatus()) {
			pd.setPushStatus(true);
			pd.setTimeS(timeS);
			pd.setMap(map);
		}
	}
}
