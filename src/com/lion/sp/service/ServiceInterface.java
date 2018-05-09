package com.lion.sp.service;

import java.util.Map;

import com.lion.frame.websocket.HeadInfo;
import com.lion.sp.exception.ClientException;

public interface ServiceInterface {
	public Map<String, Object> active(HeadInfo head, Map<String, Object> params) throws ClientException;
	public void valParams(Map<String, Object> params) throws ClientException;
}
