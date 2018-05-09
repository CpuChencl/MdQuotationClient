package com.lion.redis.bean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class StockInfo {
	public final static List<Object> FIELDS = new ArrayList<Object>();
	static{
		Field[] fs = StockInfo.class.getDeclaredFields();
		for (Field f : fs) {
			if ("FIELDS".equals(f.getName())) {
				continue;
			}
			FIELDS.add(f.getName());
		}
	}
	private String stockCode; // 证券代码
	private String stockName; // 证券名称
	private String preclose; // 前收盘价格
	private String open; // 今开盘价格
	private String turnover; // 今成交金
	private String high; // 最高价格
	private String low; // 最低价格
	private String lastprice; // 最新价格
	private String volume; // 成交数量
	private String stockNameBIG5; // 证券繁体中文名称
	private String askp1; // 当前买入价格
	private String askvol1; // 申买量一
	private String askp2; // 申买价二
	private String askvol2; // 申买量二
	private String askp3; // 申买价三
	private String askvol3; // 申买量三
	private String askp4; // 申买价四
	private String askvol4; // 申买量四
	private String askp5; // 申买价五
	private String askvol5; // 申买量五
	private String bidp1; // 当前卖出价格
	private String bidvol1; // 申卖量一
	private String bidp2; // 申卖价二
	private String bidvol2; // 申卖量二
	private String bidp3; // 申卖价三
	private String bidvol3; // 申卖量三
	private String bidp4; // 申卖价四
	private String bidvol4; // 申卖量四
	private String bidp5; // 申卖价五
	private String bidvol5; // 申卖量五
	private String stockNameCN; // 证券简体中文名称
	private String stockNameGlob; // 国际股票代码信息
	private String stockType; // 证券分类
	private String market;//市场
	
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getPreclose() {
		return preclose;
	}
	public void setPreclose(String preclose) {
		this.preclose = preclose;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getTurnover() {
		return turnover;
	}
	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	public String getLastprice() {
		return lastprice;
	}
	public void setLastprice(String lastprice) {
		this.lastprice = lastprice;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getStockNameBIG5() {
		return stockNameBIG5;
	}
	public void setStockNameBIG5(String stockNameBIG5) {
		this.stockNameBIG5 = stockNameBIG5;
	}
	public String getAskp1() {
		return askp1;
	}
	public void setAskp1(String askp1) {
		this.askp1 = askp1;
	}
	public String getAskvol1() {
		return askvol1;
	}
	public void setAskvol1(String askvol1) {
		this.askvol1 = askvol1;
	}
	public String getAskp2() {
		return askp2;
	}
	public void setAskp2(String askp2) {
		this.askp2 = askp2;
	}
	public String getAskvol2() {
		return askvol2;
	}
	public void setAskvol2(String askvol2) {
		this.askvol2 = askvol2;
	}
	public String getAskp3() {
		return askp3;
	}
	public void setAskp3(String askp3) {
		this.askp3 = askp3;
	}
	public String getAskvol3() {
		return askvol3;
	}
	public void setAskvol3(String askvol3) {
		this.askvol3 = askvol3;
	}
	public String getAskp4() {
		return askp4;
	}
	public void setAskp4(String askp4) {
		this.askp4 = askp4;
	}
	public String getAskvol4() {
		return askvol4;
	}
	public void setAskvol4(String askvol4) {
		this.askvol4 = askvol4;
	}
	public String getAskp5() {
		return askp5;
	}
	public void setAskp5(String askp5) {
		this.askp5 = askp5;
	}
	public String getAskvol5() {
		return askvol5;
	}
	public void setAskvol5(String askvol5) {
		this.askvol5 = askvol5;
	}
	public String getBidp1() {
		return bidp1;
	}
	public void setBidp1(String bidp1) {
		this.bidp1 = bidp1;
	}
	public String getBidvol1() {
		return bidvol1;
	}
	public void setBidvol1(String bidvol1) {
		this.bidvol1 = bidvol1;
	}
	public String getBidp2() {
		return bidp2;
	}
	public void setBidp2(String bidp2) {
		this.bidp2 = bidp2;
	}
	public String getBidvol2() {
		return bidvol2;
	}
	public void setBidvol2(String bidvol2) {
		this.bidvol2 = bidvol2;
	}
	public String getBidp3() {
		return bidp3;
	}
	public void setBidp3(String bidp3) {
		this.bidp3 = bidp3;
	}
	public String getBidvol3() {
		return bidvol3;
	}
	public void setBidvol3(String bidvol3) {
		this.bidvol3 = bidvol3;
	}
	public String getBidp4() {
		return bidp4;
	}
	public void setBidp4(String bidp4) {
		this.bidp4 = bidp4;
	}
	public String getBidvol4() {
		return bidvol4;
	}
	public void setBidvol4(String bidvol4) {
		this.bidvol4 = bidvol4;
	}
	public String getBidp5() {
		return bidp5;
	}
	public void setBidp5(String bidp5) {
		this.bidp5 = bidp5;
	}
	public String getBidvol5() {
		return bidvol5;
	}
	public void setBidvol5(String bidvol5) {
		this.bidvol5 = bidvol5;
	}
	public String getStockNameCN() {
		return stockNameCN;
	}
	public void setStockNameCN(String stockNameCN) {
		this.stockNameCN = stockNameCN;
	}
	public String getStockNameGlob() {
		return stockNameGlob;
	}
	public void setStockNameGlob(String stockNameGlob) {
		this.stockNameGlob = stockNameGlob;
	}
	public String getStockType() {
		return stockType;
	}
	public void setStockType(String stockType) {
		this.stockType = stockType;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
}
