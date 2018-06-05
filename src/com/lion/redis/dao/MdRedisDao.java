package com.lion.redis.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.lion.flatbuffers.FbEsTapAPIQuoteWhole;

@Component
public class MdRedisDao extends BaseRedisDao {
	private final List<Object> FIELDS = new ArrayList<Object>() {
		{
			add("currencyNo");
			add("tradingState");
			add("dateTimeStamp");
			add("preClosingPrice");
			add("preSettlePrice");
			add("prePositionQty");
			add("openingPrice");
			add("lastPrice");
			add("highPrice");
			add("lowPrice");
			add("hisHighPrice");
			add("hisLowPrice");
			add("limitUpPrice");
			add("limitDownPrice");
			add("totalQty");
			add("totalTurnover");
			add("positionQty");
			add("averagePrice");
			add("closingPrice");
			add("settlePrice");
			add("lastQty");
			add("bidPrice1");
			add("bidPrice2");
			add("bidPrice3");
			add("bidPrice4");
			add("bidPrice5");
			add("bidQty1");
			add("bidQty2");
			add("bidQty3");
			add("bidQty4");
			add("bidQty5");
			add("askPrice1");
			add("askPrice2");
			add("askPrice3");
			add("askPrice4");
			add("askPrice5");
			add("askQty1");
			add("askQty2");
			add("askQty3");
			add("askQty4");
			add("askQty5");
			add("impliedBidPrice");
			add("impliedBidQty");
			add("impliedAskPrice");
			add("impliedAskQty");
			add("preDelta");
			add("currDelta");
			add("insideQty");
			add("outsideQty");
			add("turnoverRate");
			add("5DAvgQty");
			add("pERatio");
			add("totalValue");
			add("negotiableValue");
			add("positionTrend");
			add("changeSpeed");
			add("changeRate");
			add("changeValue");
			add("swing");
			add("totalBidQty");
			add("totalAskQty");
			add("Contract_ContractNo1");
			add("Contract_StrikePrice1");
			add("Contract_CallOrPutFlag1");
			add("Contract_ContractNo2");
			add("Contract_StrikePrice2");
			add("Contract_CallOrPutFlag2");
			add("Contract_ExchangeNo");
			add("Contract_CommodityType");
			add("Contract_CommodityNo");
			add("ContractNo");
			add("dateTimeStampStr");
		}
	};
	private final String KEY = "Fetures_";
	
	@Autowired
	private ContractRedisDao contractRedisDao;
	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	@Override
	protected RedisTemplate<Serializable, Serializable> getRedisTemplate() {
		return redisTemplate;
	}

	public void saveFuturesInfo(Map<String, String> map) {

		// 存数据库
		mapPutAll(KEY + map.get("Contract_ContractNo1"), map);
	}

	public Map<String, String> getFuturesInfoByNo(String contractNo) {
		if (!hasKey(KEY + contractNo)) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		List<Object> l = mapMultiGet(KEY + contractNo, FIELDS);
		map.put("currencyNo", l.get(0) == null ? null : l.get(0).toString());
		map.put("tradingState", l.get(1) == null ? null : l.get(1).toString());
		map.put("dateTimeStamp", l.get(2) == null ? null : l.get(2).toString());
		map.put("preClosingPrice", l.get(3) == null ? null : l.get(3).toString());
		map.put("preSettlePrice", l.get(4) == null ? null : l.get(4).toString());
		map.put("prePositionQty", l.get(5) == null ? null : l.get(5).toString());
		map.put("openingPrice", l.get(6) == null ? null : l.get(6).toString());
		map.put("lastPrice", l.get(7) == null ? null : l.get(7).toString());
		map.put("highPrice", l.get(8) == null ? null : l.get(8).toString());
		map.put("lowPrice", l.get(9) == null ? null : l.get(9).toString());
		map.put("hisHighPrice", l.get(10) == null ? null : l.get(10).toString());
		map.put("hisLowPrice", l.get(11) == null ? null : l.get(11).toString());
		map.put("limitUpPrice", l.get(12) == null ? null : l.get(12).toString());
		map.put("limitDownPrice", l.get(13) == null ? null : l.get(13).toString());
		map.put("totalQty", l.get(14) == null ? null : l.get(14).toString());
		map.put("totalTurnover", l.get(15) == null ? null : l.get(15).toString());
		map.put("positionQty", l.get(16) == null ? null : l.get(16).toString());
		map.put("averagePrice", l.get(17) == null ? null : l.get(17).toString());
		map.put("closingPrice", l.get(18) == null ? null : l.get(18).toString());
		map.put("settlePrice", l.get(19) == null ? null : l.get(19).toString());
		map.put("lastQty", l.get(20) == null ? null : l.get(20).toString());
		map.put("bidPrice1", l.get(21) == null ? null : l.get(21).toString());
		map.put("bidPrice2", l.get(22) == null ? null : l.get(22).toString());
		map.put("bidPrice3", l.get(23) == null ? null : l.get(23).toString());
		map.put("bidPrice4", l.get(24) == null ? null : l.get(24).toString());
		map.put("bidPrice5", l.get(25) == null ? null : l.get(25).toString());
		map.put("bidQty1", l.get(26) == null ? null : l.get(26).toString());
		map.put("bidQty2", l.get(27) == null ? null : l.get(27).toString());
		map.put("bidQty3", l.get(28) == null ? null : l.get(28).toString());
		map.put("bidQty4", l.get(29) == null ? null : l.get(29).toString());
		map.put("bidQty5", l.get(30) == null ? null : l.get(30).toString());
		map.put("askPrice1", l.get(31) == null ? null : l.get(31).toString());
		map.put("askPrice2", l.get(32) == null ? null : l.get(32).toString());
		map.put("askPrice3", l.get(33) == null ? null : l.get(33).toString());
		map.put("askPrice4", l.get(34) == null ? null : l.get(34).toString());
		map.put("askPrice5", l.get(35) == null ? null : l.get(35).toString());
		map.put("askQty1", l.get(36) == null ? null : l.get(36).toString());
		map.put("askQty2", l.get(37) == null ? null : l.get(37).toString());
		map.put("askQty3", l.get(38) == null ? null : l.get(38).toString());
		map.put("askQty4", l.get(39) == null ? null : l.get(39).toString());
		map.put("askQty5", l.get(40) == null ? null : l.get(40).toString());
		map.put("impliedBidPrice", l.get(41) == null ? null : l.get(41).toString());
		map.put("impliedBidQty", l.get(42) == null ? null : l.get(42).toString());
		map.put("impliedAskPrice", l.get(43) == null ? null : l.get(43).toString());
		map.put("impliedAskQty", l.get(44) == null ? null : l.get(44).toString());
		map.put("preDelta", l.get(45) == null ? null : l.get(45).toString());
		map.put("currDelta", l.get(46) == null ? null : l.get(46).toString());
		map.put("insideQty", l.get(47) == null ? null : l.get(47).toString());
		map.put("outsideQty", l.get(48) == null ? null : l.get(48).toString());
		map.put("turnoverRate", l.get(49) == null ? null : l.get(49).toString());
		map.put("5DAvgQty", l.get(50) == null ? null : l.get(50).toString());
		map.put("pERatio", l.get(51) == null ? null : l.get(51).toString());
		map.put("totalValue", l.get(52) == null ? null : l.get(52).toString());
		map.put("negotiableValue", l.get(53) == null ? null : l.get(53).toString());
		map.put("positionTrend", l.get(54) == null ? null : l.get(54).toString());
		map.put("changeSpeed", l.get(55) == null ? null : l.get(55).toString());
		map.put("changeRate", l.get(56) == null ? null : l.get(56).toString());
		map.put("changeValue", l.get(57) == null ? null : l.get(57).toString());
		map.put("swing", l.get(58) == null ? null : l.get(58).toString());
		map.put("totalBidQty", l.get(59) == null ? null : l.get(59).toString());
		map.put("totalAskQty", l.get(60) == null ? null : l.get(60).toString());
		map.put("Contract_ContractNo1", l.get(61) == null ? null : l.get(61).toString());
		map.put("Contract_StrikePrice1", l.get(62) == null ? null : l.get(62).toString());
		map.put("Contract_CallOrPutFlag1", l.get(63) == null ? null : l.get(63).toString());
		map.put("Contract_ContractNo2", l.get(64) == null ? null : l.get(64).toString());
		map.put("Contract_StrikePrice2", l.get(65) == null ? null : l.get(65).toString());
		map.put("Contract_CallOrPutFlag2", l.get(66) == null ? null : l.get(66).toString());
		map.put("Contract_ExchangeNo", l.get(67) == null ? null : l.get(67).toString());
		map.put("Contract_CommodityType", l.get(68) == null ? null : l.get(68).toString());
		map.put("Contract_CommodityNo", l.get(69) == null ? null : l.get(69).toString());
		map.put("Contract_CommodityNo", l.get(69) == null ? null : l.get(69).toString());
		map.put("ContractNo", l.get(70) == null ? null : l.get(70).toString());
		map.put("dateTimeStampStr", l.get(71) == null ? null : l.get(71).toString());
		return map;
	}
	public List<Map<String, String>> getFuturesInfoList() {
		Set<Serializable> keys = keys(KEY + "*");
		List<Object> l = null;
		List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
		Map<String, String> map = null;
		for (Serializable key : keys) {
			map = new HashMap<String, String>();
			l = mapMultiGet(key, FIELDS);
			map.put("currencyNo", l.get(0) == null ? null : l.get(0).toString());
			map.put("tradingState", l.get(1) == null ? null : l.get(1).toString());
			map.put("dateTimeStamp", l.get(2) == null ? null : l.get(2).toString());
			map.put("preClosingPrice", l.get(3) == null ? null : l.get(3).toString());
			map.put("preSettlePrice", l.get(4) == null ? null : l.get(4).toString());
			map.put("prePositionQty", l.get(5) == null ? null : l.get(5).toString());
			map.put("openingPrice", l.get(6) == null ? null : l.get(6).toString());
			map.put("lastPrice", l.get(7) == null ? null : l.get(7).toString());
			map.put("highPrice", l.get(8) == null ? null : l.get(8).toString());
			map.put("lowPrice", l.get(9) == null ? null : l.get(9).toString());
			map.put("hisHighPrice", l.get(10) == null ? null : l.get(10).toString());
			map.put("hisLowPrice", l.get(11) == null ? null : l.get(11).toString());
			map.put("limitUpPrice", l.get(12) == null ? null : l.get(12).toString());
			map.put("limitDownPrice", l.get(13) == null ? null : l.get(13).toString());
			map.put("totalQty", l.get(14) == null ? null : l.get(14).toString());
			map.put("totalTurnover", l.get(15) == null ? null : l.get(15).toString());
			map.put("positionQty", l.get(16) == null ? null : l.get(16).toString());
			map.put("averagePrice", l.get(17) == null ? null : l.get(17).toString());
			map.put("closingPrice", l.get(18) == null ? null : l.get(18).toString());
			map.put("settlePrice", l.get(19) == null ? null : l.get(19).toString());
			map.put("lastQty", l.get(20) == null ? null : l.get(20).toString());
			map.put("bidPrice1", l.get(21) == null ? null : l.get(21).toString());
			map.put("bidPrice2", l.get(22) == null ? null : l.get(22).toString());
			map.put("bidPrice3", l.get(23) == null ? null : l.get(23).toString());
			map.put("bidPrice4", l.get(24) == null ? null : l.get(24).toString());
			map.put("bidPrice5", l.get(25) == null ? null : l.get(25).toString());
			map.put("bidQty1", l.get(26) == null ? null : l.get(26).toString());
			map.put("bidQty2", l.get(27) == null ? null : l.get(27).toString());
			map.put("bidQty3", l.get(28) == null ? null : l.get(28).toString());
			map.put("bidQty4", l.get(29) == null ? null : l.get(29).toString());
			map.put("bidQty5", l.get(30) == null ? null : l.get(30).toString());
			map.put("askPrice1", l.get(31) == null ? null : l.get(31).toString());
			map.put("askPrice2", l.get(32) == null ? null : l.get(32).toString());
			map.put("askPrice3", l.get(33) == null ? null : l.get(33).toString());
			map.put("askPrice4", l.get(34) == null ? null : l.get(34).toString());
			map.put("askPrice5", l.get(35) == null ? null : l.get(35).toString());
			map.put("askQty1", l.get(36) == null ? null : l.get(36).toString());
			map.put("askQty2", l.get(37) == null ? null : l.get(37).toString());
			map.put("askQty3", l.get(38) == null ? null : l.get(38).toString());
			map.put("askQty4", l.get(39) == null ? null : l.get(39).toString());
			map.put("askQty5", l.get(40) == null ? null : l.get(40).toString());
			map.put("impliedBidPrice", l.get(41) == null ? null : l.get(41).toString());
			map.put("impliedBidQty", l.get(42) == null ? null : l.get(42).toString());
			map.put("impliedAskPrice", l.get(43) == null ? null : l.get(43).toString());
			map.put("impliedAskQty", l.get(44) == null ? null : l.get(44).toString());
			map.put("preDelta", l.get(45) == null ? null : l.get(45).toString());
			map.put("currDelta", l.get(46) == null ? null : l.get(46).toString());
			map.put("insideQty", l.get(47) == null ? null : l.get(47).toString());
			map.put("outsideQty", l.get(48) == null ? null : l.get(48).toString());
			map.put("turnoverRate", l.get(49) == null ? null : l.get(49).toString());
			map.put("5DAvgQty", l.get(50) == null ? null : l.get(50).toString());
			map.put("pERatio", l.get(51) == null ? null : l.get(51).toString());
			map.put("totalValue", l.get(52) == null ? null : l.get(52).toString());
			map.put("negotiableValue", l.get(53) == null ? null : l.get(53).toString());
			map.put("positionTrend", l.get(54) == null ? null : l.get(54).toString());
			map.put("changeSpeed", l.get(55) == null ? null : l.get(55).toString());
			map.put("changeRate", l.get(56) == null ? null : l.get(56).toString());
			map.put("changeValue", l.get(57) == null ? null : l.get(57).toString());
			map.put("swing", l.get(58) == null ? null : l.get(58).toString());
			map.put("totalBidQty", l.get(59) == null ? null : l.get(59).toString());
			map.put("totalAskQty", l.get(60) == null ? null : l.get(60).toString());
			map.put("Contract_ContractNo1", l.get(61) == null ? null : l.get(61).toString());
			map.put("Contract_StrikePrice1", l.get(62) == null ? null : l.get(62).toString());
			map.put("Contract_CallOrPutFlag1", l.get(63) == null ? null : l.get(63).toString());
			map.put("Contract_ContractNo2", l.get(64) == null ? null : l.get(64).toString());
			map.put("Contract_StrikePrice2", l.get(65) == null ? null : l.get(65).toString());
			map.put("Contract_CallOrPutFlag2", l.get(66) == null ? null : l.get(66).toString());
			map.put("Contract_ExchangeNo", l.get(67) == null ? null : l.get(67).toString());
			map.put("Contract_CommodityType", l.get(68) == null ? null : l.get(68).toString());
			map.put("Contract_CommodityNo", l.get(69) == null ? null : l.get(69).toString());
			map.put("Contract_CommodityNo", l.get(69) == null ? null : l.get(69).toString());
			map.put("ContractNo", l.get(70) == null ? null : l.get(70).toString());
			map.put("dateTimeStampStr", l.get(71) == null ? null : l.get(71).toString());
			
			Map<String, String> cMap = contractRedisDao.getRedisContract(map.get("Contract_CommodityNo"), map.get("Contract_ContractNo1"));
			if (cMap != null) {//取不到则代表该合约不存在
				map.putAll(cMap);
				dataList.add(map);
			}
		}
		return dataList;
	}
}
