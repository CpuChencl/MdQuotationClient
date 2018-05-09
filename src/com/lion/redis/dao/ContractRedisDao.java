package com.lion.redis.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.lion.common.utils.DateUtil;

@Component
public class ContractRedisDao extends BaseRedisDao {
	private final String CONTRACT_NO_KEY_PROFIX = "contract_no_";
	private final String CONTRACT_KEY_PROFIX = "contract_id_";
	private final String GOODS_NO_KEY_PROFIX = "goods_no_";
	private final String GOODS_KEY_PROFIX = "goods_id_";
	
	private volatile Map<String, Map<String, String>> contractsMap;
	private Thread flushContractThread;
	
	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate2;

	@Override
	protected RedisTemplate<Serializable, Serializable> getRedisTemplate() {
		return redisTemplate2;
	}
	
	@PostConstruct
	public void init(){//初始化合约信息
		loadData();
		Runnable r = new Runnable() {//线程60秒刷新一次合约信息
			
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(60000);
						loadData();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		flushContractThread = new Thread(r);
		flushContractThread.start();
	}
	
	public void loadData(){
		Map<String, Map<String, String>> cMap = new ConcurrentHashMap<String, Map<String,String>>();
		List<Map<String, String>> dataList = getRedisContractList1();
		Map<String, String> map = null;
		for (int i = 0; i < dataList.size(); i++) {
			map = dataList.get(i);
			cMap.put(map.get("contractNo"), map);
		}
		contractsMap = cMap;
	}

	public Map<String, String> getRedisContract(String goodsNo, String contractNo) {
		Map<String, String> map = contractsMap.get(goodsNo + contractNo);
		if (map == null) {
			return null;
		}
		Date lastDate = DateUtil.strToDate1(map.get("lastTradeDate"));
		if (lastDate == null || System.currentTimeMillis() > lastDate.getTime()) {
			return null;
		}
		return map;
	}
	
	public List<Map<String, String>> getRedisContractList() {
		List<Map<String,String>> dataList = new ArrayList<Map<String,String>>(contractsMap.values());
		return dataList;
	}
	public List<Map<String, String>> getRedisContractList1() {
		List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
		Map<String,String> dataMap = null;
		//合约
		List<Object> cFields = new ArrayList<Object>(4){{add("contractNameBase64");add("lastTradeDate");add("status");add("goodsId");add("contractNo");}};
		List<Object> gFields = new ArrayList<Object>(5){{add("minChange");add("priceUnit");add("maxOneBuy");add("deposit");add("contractMultiplier");add("overNightDeposit");}};
		List<Object> data = null;
		Set<Serializable> keys = keys(CONTRACT_KEY_PROFIX + "*");
		for (Serializable key : keys) {
			data = mapMultiGet(key, cFields);
			dataMap = new HashMap<String, String>();
			dataMap.put("contractName", data.get(0).toString());
			if (data.get(1) != null) {//如果最后交易日有值,并且小于当前时间则不推送也获取不到列表
				Date lastDate = DateUtil.strToDate1(data.get(1).toString());
				if (System.currentTimeMillis() > lastDate.getTime()) {
					continue;
				}
			}
			dataMap.put("lastTradeDate", data.get(1) == null ? null : data.get(1).toString());
			dataMap.put("contractStatus", data.get(2) == null ? null : data.get(2).toString());
			dataMap.put("contractNo", data.get(4) == null ? null : data.get(4).toString());
			//商品
			data = mapMultiGet(GOODS_KEY_PROFIX + data.get(3), gFields);
			if (data == null) {
				continue;
			}
			dataMap.put("minChange", data.get(0) == null ? null : new BigDecimal(data.get(0).toString()).toPlainString());
			dataMap.put("priceUnit", data.get(1) == null ? null : data.get(1).toString());
			dataMap.put("maxOneBuy", data.get(2) == null ? null : data.get(2).toString());
			dataMap.put("deposit", data.get(3) == null ? null : data.get(3).toString());
			dataMap.put("contractMultiplier", data.get(4) == null ? null : data.get(4).toString());
			dataMap.put("onDeposit", data.get(5) == null ? null : new BigDecimal(data.get(5).toString()).toPlainString());
			dataList.add(dataMap);
		}
		return dataList;
	}

//	public Map<String, String> getRedisContract(String goodsNo, String contractNo) {
//		Map<String,String> dataMap = new HashMap<String, String>();
//		//合约
//		List<Object> fields = new ArrayList<Object>(3){{add("contractNameBase64");add("lastTradeDate");add("status");}};
//		List<Object> data = null;
//		String contractId = get(CONTRACT_NO_KEY_PROFIX + goodsNo + contractNo);
//		if (contractId == null) {
//			return null;
//		}
//		data = mapMultiGet(CONTRACT_KEY_PROFIX + contractId, fields);
//		dataMap.put("contractName", data.get(0).toString());
//		if (data.get(1) != null) {//如果最后交易日有值,并且小于当前时间则不推送也获取不到列表
//			Date lastDate = DateUtil.strToDate1(data.get(1).toString());
//			if (System.currentTimeMillis() > lastDate.getTime()) {
//				return null;
//			}
//		}
//		dataMap.put("contractStatus", data.get(2) == null ? null : data.get(2).toString());
//		//商品
//		fields = new ArrayList<Object>(5){{add("minChange");add("priceUnit");add("maxOneBuy");add("deposit");add("contractMultiplier");}};
//		String goodsId = get(GOODS_NO_KEY_PROFIX + goodsNo);
//		if (goodsId == null) {
//			return null;
//		}
//		data = mapMultiGet(GOODS_KEY_PROFIX + goodsId, fields);
//		dataMap.put("minChange", data.get(0) == null ? null : new BigDecimal(data.get(0).toString()).toPlainString());
//		dataMap.put("priceUnit", data.get(1) == null ? null : data.get(1).toString());
//		dataMap.put("maxOneBuy", data.get(2) == null ? null : data.get(2).toString());
//		dataMap.put("deposit", data.get(3) == null ? null : data.get(3).toString());
//		dataMap.put("contractMultiplier", data.get(4) == null ? null : data.get(4).toString());
//		return dataMap;
//	}
}
