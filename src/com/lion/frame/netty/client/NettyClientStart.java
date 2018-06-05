package com.lion.frame.netty.client;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import com.lion.frame.logger.Log4jManager;
import com.lion.frame.websocket.SocketServer;
import com.lion.redis.dao.ContractRedisDao;
import com.lion.sp.push.PushThread;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

public class NettyClientStart {
	private String host;
	private int port;
	private String clientInfo;
	private static final Logger log = Log4jManager.get();
	private PushThread pushThread;
	private ContractRedisDao contractRedisDao;
	private SocketServer socketServer;
    private NioEventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;
    private Bootstrap bootstrap;
    private ChannelFuture future;
    
    public NettyClientStart(String host,int port,String clientInfo,PushThread pushThread,ContractRedisDao contractRedisDao,SocketServer socketServer){
    	this.host = host;
    	this.port = port;
    	this.clientInfo = clientInfo;
    	this.pushThread = pushThread;
    	this.contractRedisDao = contractRedisDao;
    	this.socketServer = socketServer;
    }

    public void sendData() throws Exception {
    	future.channel().writeAndFlush(Unpooled.copiedBuffer((clientInfo+"$$$").getBytes())); 
    }

    public void start(final NettyClientStart client) {
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {  
                @Override  
                protected void initChannel(SocketChannel socketChannel) throws Exception {  
        	        ByteBuf delimiter = Unpooled.copiedBuffer("$$$".getBytes());
                    socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(5120,delimiter)).addLast(new NettyClientHandler(contractRedisDao, socketServer, pushThread,client));  
                }  
            });
            doConnect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void doConnect() {
        if (channel != null && channel.isActive()) {
            return;
        }
        future = bootstrap.connect(host, port);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture futureListener) throws Exception {
                if (futureListener.isSuccess()) {
                    channel = futureListener.channel();
                    log.info("连接server成功");
                    try {
            			sendData();
            		} catch (Exception e) {
            			log.error("发送数据失败："+e.getMessage());
            		}
                } else {
                    log.info("连接失败, try connect after 10s");
                    futureListener.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                            doConnect();
                        }
                    }, 10, TimeUnit.SECONDS);
                }
            }
        });
    }
}
