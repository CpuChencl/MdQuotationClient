package com.lion.sp.push;

import java.util.Map;

public class PushData {
	private volatile Map<String, String> map;
	private volatile boolean pushStatus;//true推送,false数据不是最新,不推送
	private volatile long timeS = 0;//时间戳
	
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public boolean getPushStatus() {
		return pushStatus;
	}
	public void setPushStatus(boolean pushStatus) {
		this.pushStatus = pushStatus;
	}
	public long getTimeS() {
		return timeS;
	}
	public void setTimeS(long timeS) {
		this.timeS = timeS;
	}
}
