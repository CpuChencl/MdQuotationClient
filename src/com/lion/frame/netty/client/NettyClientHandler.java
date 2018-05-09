package com.lion.frame.netty.client;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.lion.frame.logger.Log4jManager;
import com.lion.frame.websocket.SocketServer;
import com.lion.redis.dao.ContractRedisDao;
import com.lion.sp.mqprocess.ProcessRtnMdMsg;
import com.lion.sp.push.PushThread;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Component
public class NettyClientHandler extends SimpleChannelInboundHandler<Object> {

	private static final Logger log = Log4jManager.get();
	
	private PushThread pushThread;
	private ContractRedisDao contractRedisDao;
	private SocketServer socketServer;
	private volatile static int count = 0;
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object serverMsg) throws Exception {
		count++;
		ByteBuf buf = (ByteBuf) serverMsg;  
		byte[] data = new byte[buf.readableBytes()];  
        buf.readBytes(data);
        if(count == 800){
        	ByteBuffer bb = ByteBuffer.wrap(data);
    		log.info("fromNS:"+bb.toString());
    		count = 0;
        }
		new ProcessRtnMdMsg(data, contractRedisDao, socketServer, pushThread);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("连接netty server 成功");
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("与netty server 断开连接");
		super.channelInactive(ctx);
	}
	
	public NettyClientHandler(ContractRedisDao contractRedisDao, SocketServer socketServer, PushThread pushThread){
		this.contractRedisDao = contractRedisDao;
    	this.socketServer = socketServer;
    	this.pushThread = pushThread;
	}
}
