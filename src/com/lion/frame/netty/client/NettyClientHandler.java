package com.lion.frame.netty.client;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.lion.flatbuffers.FbEsTapAPIQuoteWhole;
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
	private NettyClientStart client;
	private volatile static int count = 0;
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object serverMsg) throws Exception {
		count++;
		ByteBuf buf = (ByteBuf) serverMsg;  
		byte[] data = new byte[buf.readableBytes()];  
        buf.readBytes(data);
        if(count == 1500){
        	ByteBuffer bb = ByteBuffer.wrap(data);
    		FbEsTapAPIQuoteWhole marketData = FbEsTapAPIQuoteWhole.getRootAsFbEsTapAPIQuoteWhole(bb);
    		String contructNo = marketData.Contract().Commodity().CommodityNo() + marketData.Contract().ContractNo1();
    		log.info("fromNS:futureCode:[{"+contructNo+"}],markettime:[{"+marketData.DateTimeStamp()+"}],qlprice:[{"+marketData.QLastPrice()+"}]");
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
		log.info("与netty server 断开连接");
		super.channelInactive(ctx);
	    //重新连接服务器
	    ctx.channel().eventLoop().schedule(new Runnable() {
	        @Override
	        public void run() {
	            client.doConnect();
	        }
	    }, 3, TimeUnit.SECONDS);
	    ctx.close();
	}
	
	public NettyClientHandler(ContractRedisDao contractRedisDao, SocketServer socketServer, PushThread pushThread,NettyClientStart client){
		this.contractRedisDao = contractRedisDao;
    	this.socketServer = socketServer;
    	this.pushThread = pushThread;
    	this.client = client;
	}
}
