package com.lion.sp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lion.frame.websocket.HeadInfo;
import com.lion.redis.dao.ContractRedisDao;
import com.lion.redis.dao.MdRedisDao;
import com.lion.sp.exception.ClientException;

@Component("MdQuotation")
public class MdQuotationImpl implements ServiceInterface{

	@Autowired
	private MdRedisDao mdRedisDao;
	@Autowired
	private ContractRedisDao contractRedisDao;
	private final Comparator<Map<String, String>> com = new Comparator<Map<String, String>>() {//排序类
		@Override
		public int compare(Map<String, String> o1, Map<String, String> o2) {
			return (o1.get("Contract_CommodityNo") + o1.get("Contract_ContractNo1")).hashCode() - (o2.get("Contract_CommodityNo") + o2.get("Contract_ContractNo1")).hashCode();
		}
	};
	@Override
	public Map<String, Object> active(HeadInfo head, Map<String, Object> params) {
//		List<Map<String, String>> dataList = mdRedisDao.getFuturesInfoList();
		List<Map<String, String>> list = contractRedisDao.getRedisContractList();
		Map<String, String> tmp = null;
		Map<String, String> fiMap = null;
		List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
		for (int i = 0; i < list.size(); i++) {
			tmp = new HashMap<String, String>();
			tmp.putAll(list.get(i));
			fiMap = mdRedisDao.getFuturesInfoByNo(tmp.get("contractNo"));
			if (fiMap == null) {
				continue;
			}
			tmp.remove("contractNo");
			tmp.putAll(fiMap);
			dataList.add(tmp);
		}
		Collections.sort(dataList, com);
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("dataList", dataList);
		return ret;
	}
	
	public void valParams(Map<String, Object> params) throws ClientException{
		
	}

}
