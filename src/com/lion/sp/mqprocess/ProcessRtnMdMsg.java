package com.lion.sp.mqprocess;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lion.frame.websocket.SocketServer;
import com.lion.redis.dao.ContractRedisDao;
import com.lion.sp.push.PushThread;

public class ProcessRtnMdMsg {

	private Logger log = Logger.getLogger(ProcessRtnMdMsg.class);
	
	private byte[] b;
	
	private ContractRedisDao contractRedisDao;
	
	private PushThread pushThread;
	
	public ProcessRtnMdMsg(byte[] b, ContractRedisDao contractRedisDao, SocketServer socketServer, PushThread pushThread){
		this.b = b;
		this.contractRedisDao = contractRedisDao;
		this.pushThread = pushThread;
		try {
			call();
		} catch (Exception e) {
			log.error("client error:", e);
			e.printStackTrace();
		}
	}
	
	public String call() throws Exception {
		try {
			parseResp(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 *  0001 - 成交回报
	 *  0002 - 
	 */
	private void parseResp(byte[] req){
		Map<String, String> map = new HashMap<String, String>();
		//封装合约信息
		putContract("Contract", req, map);
		String contractNo = map.get("Contract_CommodityNo") + map.get("Contract_ContractNo1");
		map.put("ContractNo", contractNo);
		Map<String, String> cMap = contractRedisDao.getRedisContract(map.get("Contract_CommodityNo"), map.get("Contract_ContractNo1"));
		if (cMap != null) {//取不到则代表该合约不存在
			map.putAll(cMap);
			pushThread.setPushData(contractNo, map, System.currentTimeMillis());
		}
	}
	
	private void putContract(String key, byte[] req, Map<String, String> map){
		if (req != null) {
			map.put(key + "_ContractNo1", req.toString());//合约代码1
		}
	}
}