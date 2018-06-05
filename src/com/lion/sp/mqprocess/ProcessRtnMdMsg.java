package com.lion.sp.mqprocess;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.lion.common.utils.DateUtil;
import com.lion.flatbuffers.FbEsTapAPIQuoteWhole;
import com.lion.frame.websocket.SocketServer;
import com.lion.redis.dao.ContractRedisDao;
import com.lion.sp.push.PushThread;

public class ProcessRtnMdMsg {

	private Logger log = Logger.getLogger(ProcessRtnMdMsg.class);
	
	private byte[] b;
	
	private ContractRedisDao contractRedisDao;
	
	private SocketServer socketServer;
	
	private PushThread pushThread;
	
	public ProcessRtnMdMsg(byte[] b, ContractRedisDao contractRedisDao, SocketServer socketServer, PushThread pushThread){
		this.b = b;
		this.contractRedisDao = contractRedisDao;
		this.socketServer = socketServer;
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
		ByteBuffer bb = ByteBuffer.wrap(req);
		FbEsTapAPIQuoteWhole marketData = FbEsTapAPIQuoteWhole.getRootAsFbEsTapAPIQuoteWhole(bb);
//		String contructNo = marketData.Contract().Commodity().CommodityNo() + marketData.Contract().ContractNo1();
//		log.info(req.length+"|C|futureCode:[{"+contructNo+"}],markettime:[{"+marketData.DateTimeStamp()+"}],qlprice:[{"+marketData.QLastPrice()+"}]");
		if (StringUtils.isEmpty(marketData.DateTimeStamp()) || marketData.QLastPrice() == 0) {//如果当前价为0则过滤到
			return;
		}
//		String contructNo = marketData.Contract().Commodity().CommodityNo() + marketData.Contract().ContractNo1();
//		log.info(req.length+"|C|futureCode:[{"+contructNo+"}],markettime:[{"+marketData.DateTimeStamp()+"}]");
		Long timeS = DateUtil.strToDate(marketData.DateTimeStamp(), "yyyy-MM-dd HH:mm:ss.SSS").getTime();
		Map<String, String> map = new HashMap<String, String>();
//		map.put("currencyNo", marketData.CurrencyNo());//币种编号
//		map.put("tradingState", String.valueOf(marketData.TradingState()));//交易状态。1,集合竞价;2,集合竞价撮合;3,连续交易;4,交易暂停;5,闭市
		map.put("dateTimeStamp", String.valueOf(timeS));//时间戳
		map.put("dateTimeStampStr", marketData.DateTimeStamp());//时间戳
		map.put("preClosingPrice", String.valueOf(new BigDecimal(String.valueOf(marketData.QPreClosingPrice())).setScale(7, RoundingMode.DOWN).floatValue()));//昨收盘价
		map.put("preSettlePrice", String.valueOf(new BigDecimal(String.valueOf(marketData.QPreSettlePrice())).setScale(7, RoundingMode.DOWN).floatValue()));//昨结算价
//		map.put("prePositionQty", String.valueOf(marketData.QPrePositionQty()));//昨持仓量
		map.put("openingPrice", String.valueOf(new BigDecimal(String.valueOf(marketData.QOpeningPrice())).setScale(7, RoundingMode.DOWN).floatValue()));//开盘价
		map.put("lastPrice", String.valueOf(new BigDecimal(String.valueOf(marketData.QLastPrice())).setScale(7, RoundingMode.DOWN).floatValue()));//最新价
		map.put("highPrice", String.valueOf(new BigDecimal(String.valueOf(marketData.QHighPrice())).setScale(7, RoundingMode.DOWN).floatValue()));//最高价
		map.put("lowPrice", String.valueOf(new BigDecimal(String.valueOf(marketData.QLowPrice())).setScale(7, RoundingMode.DOWN).floatValue()));//最低价
//		map.put("hisHighPrice", String.valueOf(new BigDecimal(String.valueOf(marketData.QHisHighPrice())).setScale(7, RoundingMode.DOWN).floatValue()));//历史最高价
//		map.put("hisLowPrice", String.valueOf(new BigDecimal(String.valueOf(marketData.QHisLowPrice())).setScale(7, RoundingMode.DOWN).floatValue()));//历史最低价
		map.put("limitUpPrice", String.valueOf(new BigDecimal(String.valueOf(marketData.QLimitUpPrice())).setScale(7, RoundingMode.DOWN).floatValue()));//涨停价
		map.put("limitDownPrice", String.valueOf(new BigDecimal(String.valueOf(marketData.QLimitDownPrice())).setScale(7, RoundingMode.DOWN).floatValue()));//跌停价
		map.put("totalQty", String.valueOf(marketData.QTotalQty()));//当日总成交量
//		map.put("totalTurnover", String.valueOf(new BigDecimal(String.valueOf(marketData.QTotalTurnover())).setScale(7, RoundingMode.DOWN).floatValue()));//当日成交金额
		map.put("positionQty", String.valueOf(marketData.QPositionQty()));//持仓量
//		map.put("averagePrice", String.valueOf(new BigDecimal(String.valueOf(marketData.QAveragePrice())).setScale(7, RoundingMode.DOWN).floatValue()));//均价
		map.put("closingPrice", String.valueOf(new BigDecimal(String.valueOf(marketData.QClosingPrice())).setScale(7, RoundingMode.DOWN).floatValue()));//收盘价
		map.put("settlePrice", String.valueOf(new BigDecimal(String.valueOf(marketData.QSettlePrice())).setScale(7, RoundingMode.DOWN).floatValue()));//结算价
		map.put("lastQty", String.valueOf(marketData.QLastQty()));//最新成交量
		for (int i = 0; i < 5; i++) {
			if (i < marketData.QBidPriceLength()) {
				map.put("bidPrice" + (i+1), String.valueOf(new BigDecimal(String.valueOf(marketData.QBidPrice(i))).setScale(7, RoundingMode.DOWN).floatValue()));//买价1-20档
			}
			if (i < marketData.QBidQtyLength()) {
				map.put("bidQty" + (i+1), String.valueOf(marketData.QBidQty(i)));//买量1-20档
			}
			if (i < marketData.QAskPriceLength()) {
				map.put("askPrice" + (i+1), String.valueOf(new BigDecimal(String.valueOf(marketData.QAskPrice(i))).setScale(7, RoundingMode.DOWN).floatValue()));//卖价1-20档
			}
			if (i < marketData.QAskQtyLength()) {
				map.put("askQty" + (i+1), String.valueOf(marketData.QAskQty(i)));//卖量1-20档
			}
		}
//		map.put("impliedBidPrice", String.valueOf(new BigDecimal(String.valueOf(marketData.QImpliedBidPrice())).setScale(7, RoundingMode.DOWN).floatValue()));//隐含买价
//		map.put("impliedBidQty", String.valueOf(marketData.QImpliedBidQty()));//隐含买量
//		map.put("impliedAskPrice", String.valueOf(new BigDecimal(String.valueOf(marketData.QImpliedAskPrice())).setScale(7, RoundingMode.DOWN).floatValue()));//隐含卖价
//		map.put("impliedAskQty", String.valueOf(marketData.QImpliedAskQty()));//隐含卖量
//		map.put("preDelta", String.valueOf(new BigDecimal(String.valueOf(marketData.QPreDelta())).setScale(7, RoundingMode.DOWN).floatValue()));//昨虚实度
//		map.put("currDelta", String.valueOf(new BigDecimal(String.valueOf(marketData.QCurrDelta())).setScale(7, RoundingMode.DOWN).floatValue()));//今虚实度
//		map.put("insideQty", String.valueOf(marketData.QInsideQty()));//内盘量
//		map.put("outsideQty", String.valueOf(marketData.QOutsideQty()));//外盘量
//		map.put("turnoverRate", String.valueOf(new BigDecimal(String.valueOf(marketData.QTurnoverRate())).setScale(7, RoundingMode.DOWN).floatValue()));//换手率
//		map.put("5DAvgQty", String.valueOf(marketData.Q5DAvgQty()));//五日均量
//		map.put("pERatio", String.valueOf(new BigDecimal(String.valueOf(marketData.QPERatio())).setScale(7, RoundingMode.DOWN).floatValue()));//市盈率
//		map.put("totalValue", String.valueOf(new BigDecimal(String.valueOf(marketData.QTotalValue())).setScale(7, RoundingMode.DOWN).floatValue()));//总市值
//		map.put("negotiableValue", String.valueOf(new BigDecimal(String.valueOf(marketData.QNegotiableValue())).setScale(7, RoundingMode.DOWN).floatValue()));//流通市值
//		map.put("positionTrend", String.valueOf(marketData.QPositionTrend()));//持仓走势
//		map.put("changeSpeed", String.valueOf(new BigDecimal(String.valueOf(marketData.QChangeSpeed())).setScale(7, RoundingMode.DOWN).floatValue()));//涨速
		map.put("changeRate", String.valueOf(new BigDecimal(String.valueOf(marketData.QChangeRate())).setScale(7, RoundingMode.DOWN).floatValue()));//涨幅
		map.put("changeValue", String.valueOf(new BigDecimal(String.valueOf(marketData.QChangeValue())).setScale(7, RoundingMode.DOWN).toPlainString()));//涨跌值
		map.put("swing", String.valueOf(new BigDecimal(String.valueOf(marketData.QSwing())).setScale(7, RoundingMode.DOWN).floatValue()));//振幅
//		map.put("totalBidQty", String.valueOf(marketData.QTotalBidQty()));//委买总量
//		map.put("totalAskQty", String.valueOf(marketData.QTotalAskQty()));//委卖总量
		//封装合约信息
		putContract("Contract", marketData, map);
		String contractNo = map.get("Contract_CommodityNo") + map.get("Contract_ContractNo1");
		map.put("ContractNo", contractNo);
		Map<String, String> cMap = contractRedisDao.getRedisContract(map.get("Contract_CommodityNo"), map.get("Contract_ContractNo1"));
//		log.info("TTT futureCode:[{"+contractNo+"}],markettime:[{"+marketData.DateTimeStamp()+"}]");
		if (cMap != null) {//取不到则代表该合约不存在
			map.putAll(cMap);
			pushThread.setPushData(contractNo, map, timeS);
//			socketServer.sendMdData(map);
//			log.info("futureCode:[{"+contractNo+"}],markettime:[{"+marketData.DateTimeStamp()+"}]");

		}
//		putContract("UnderlyContract", marketData, map);
//		ChannelManage.broadcast(new TextWebSocketFrame(Tools.toJson(map)));
	}
	
	private void putContract(String key, FbEsTapAPIQuoteWhole marketData, Map<String, String> map){
		if (marketData.Contract() != null) {
			map.put(key + "_ContractNo1", marketData.Contract().ContractNo1());//合约代码1
			map.put(key + "_StrikePrice1", marketData.Contract().StrikePrice1());//执行价1
//			map.put(key + "_CallOrPutFlag1", String.valueOf(marketData.Contract().CallOrPutFlag1()));//看涨看跌标示1
//			map.put(key + "_ContractNo2", marketData.Contract().ContractNo2());//合约代码2
//			map.put(key + "_StrikePrice2", marketData.Contract().StrikePrice2());//执行价2
//			map.put(key + "_CallOrPutFlag2", String.valueOf(marketData.Contract().CallOrPutFlag2()));//看涨看跌标示2
			if (marketData.Contract().Commodity() != null) {
				map.put(key + "_ExchangeNo", marketData.Contract().Commodity().ExchangeNo());//交易所编码
				map.put(key + "_CommodityType", String.valueOf(marketData.Contract().Commodity().CommodityType()));//品种类型
				map.put(key + "_CommodityNo", marketData.Contract().Commodity().CommodityNo());//品种编号
			}
		}
	}
}