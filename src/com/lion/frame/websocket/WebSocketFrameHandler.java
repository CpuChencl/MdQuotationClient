package com.lion.frame.websocket;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.lion.common.utils.SpringContextUtil;
import com.lion.common.utils.Tools;
import com.lion.frame.enu.ExceptionEnu;
import com.lion.frame.logger.Log4jManager;
import com.lion.sp.exception.ClientException;
import com.lion.sp.service.ServiceInterface;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * Echoes uppercase content of text frames.
 */
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

	private static final Logger log = Log4jManager.get();
	
	@Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // ping and pong frames already handled

        if (frame instanceof TextWebSocketFrame) {
        	// 判断是否是关闭链路的指令
        	if (frame instanceof CloseWebSocketFrame) {
        		ctx.close();
        	    return;
        	}
        	// 判断是否是Ping消息
        	if (frame instanceof PingWebSocketFrame) {
        	    ctx.channel().write(
        	            new PongWebSocketFrame(frame.content().retain()));
        	    return;
        	}
        	// 本例程仅支持文本消息，不支持二进制消息
        	if (!(frame instanceof TextWebSocketFrame)) {
        	    throw new UnsupportedOperationException(String.format(
        	            "%s frame types not supported", frame.getClass().getName()));
        	}
            String req = ((TextWebSocketFrame) frame).text();
            log.info("{} received {}", ctx.channel(), req);
            Map<String, Object> ret = null;
            try {
            	//生成head和params
            	Map<String, Object> requestData = Tools.jsonToObj(req, HashMap.class);
            	HeadInfo head = makeHead(requestData);
            	Map<String, Object> params = Tools.checkNull(requestData.get("params")) ? null : (Map<String, Object>)requestData.get("params");
            	active(head, params, ctx);
			} catch (Exception e) {
				e.printStackTrace();
				ret = new HashMap<String, Object>();
				ret.put("code", ExceptionEnu.ERROR.getCode());
				ret.put("msg", ExceptionEnu.ERROR.getDesc());
				ctx.writeAndFlush(new TextWebSocketFrame(Tools.toJson(ret)));
			}
        } else {
            String message = "unsupported frame type： " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }
	
	/**
	 * 
	 * @Title: active 
	 * @Description: 执行具体方法并返回请求数据
	 * @param head
	 * @param params
	 * @param channelGroup void    返回类型 
	 * @throws
	 */
	private void active(HeadInfo head, Map<String, Object> params, ChannelHandlerContext ctx){
		//flag是否是推送
		Map<String, Object> ret = null;
        try {
        	
        	Object obj = SpringContextUtil.getBean(head.getUrl());
        	if (obj instanceof ServiceInterface) {
    			((ServiceInterface) obj).valParams(params);
        		ret = ((ServiceInterface) obj).active(head, params);
        		Channel channel = ctx.channel();
        		ChannelManage.addChannel(channel);
        	} else {
        		throw new ClientException(ExceptionEnu.CHANNEL_ERROR);
        	}
        	if (ret == null) {
        		ret = new HashMap<String, Object>();
			}
        	ret.put("code", ExceptionEnu.SUCCESS.getCode());
			ret.put("msg", ExceptionEnu.SUCCESS.getDesc());
		} catch (ClientException e) {
			ret = new HashMap<String, Object>();
			ret.put("code", e.getCode());
			ret.put("msg", e.getMsg());
			e.printStackTrace();
		} catch (Exception e) {
			ret = new HashMap<String, Object>();
			ret.put("code", ExceptionEnu.ERROR.getCode());
			ret.put("msg", ExceptionEnu.ERROR.getDesc());
			e.printStackTrace();
		}
        
        ctx.writeAndFlush(new TextWebSocketFrame(Tools.toJson(ret)));
	}
	
	/**
	 * 
	 * @Title: makeHead 
	 * @Description: 创建head类
	 * @param params
	 * @return
	 * @throws ClientException HeadInfo    返回类型 
	 * @throws
	 */
	private HeadInfo makeHead(Map<String, Object> params) throws ClientException{
		Map<String, String> headMap = (Map<String, String>) params.get("head");
		if (headMap == null) {
			throw new ClientException(ExceptionEnu.PARAMTER_ERROR);
		}
		HeadInfo head = new HeadInfo();
		head.setUrl(headMap.get("url"));
		if (Tools.checkNull(head.getUrl())) {
			throw new ClientException(ExceptionEnu.PARAMTER_ERROR);
		}
		return head;
	}
	
//	@Override
//	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//	}
//
//	@Override
//	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//	}
//
//	@Override
//	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
//	}

//	@Override
//	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		Channel channel = ctx.channel();
//		ChannelManage.addChannel(channel);
//		log.info("Client：[{}]建立连接", new String[] { channel.remoteAddress().toString() });
//	}
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		ChannelManage.removeChannel(channel);
		log.info("Client：[{}]断开连接", new String[] { channel.remoteAddress().toString() });
		ctx.close();
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Channel channel = ctx.channel();
		ChannelManage.removeChannel(channel);
		log.info("Client：[{}]异常，强制关闭连接", new String[] { channel.remoteAddress().toString() });
		cause.printStackTrace();
		ctx.close();
	}
}