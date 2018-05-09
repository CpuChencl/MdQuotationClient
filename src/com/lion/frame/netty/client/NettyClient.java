package com.lion.frame.netty.client;

import java.io.IOException;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.lion.frame.logger.Log4jManager;
import com.lion.frame.websocket.SocketServer;
import com.lion.redis.dao.ContractRedisDao;
import com.lion.sp.push.PushThread;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

@Lazy(false)
@Component
public class NettyClient {
	@Value("${NETTY.SERVER.IP}")
	private String host;
	@Value("${NETTY.SERVER.PORT}")
	private int port;
	@Value("${NETTY.CLIENT.INFO}")
	private String clientInfo;
	private static final Logger log = Log4jManager.get();
	
	@Autowired
	private PushThread pushThread;
	@Autowired
	private ContractRedisDao contractRedisDao;
	@Autowired
	private SocketServer socketServer;
	
	@PostConstruct
	public void connectServer() throws InterruptedException, IOException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				EventLoopGroup group = new NioEventLoopGroup();  
				ChannelFuture future = null;
				try {
					Bootstrap b = new Bootstrap();
					b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {  
	                    @Override  
	                    protected void initChannel(SocketChannel socketChannel) throws Exception {  
	            	        ByteBuf delimiter = Unpooled.copiedBuffer("$$$".getBytes());
	                        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(5120,delimiter)).addLast(new NettyClientHandler(contractRedisDao, socketServer, pushThread));  
	                    }  
	                });
		
					// 连接服务端
					future = b.connect(host, port).sync();
					log.info("netty client 启动成功");
					//向netty服务端发送信息
				    future.channel().writeAndFlush(Unpooled.copiedBuffer((clientInfo+"$$$").getBytes()));  
				} catch(Exception e){
					log.info("netty client 启动失败:"+e.getMessage());
				} finally {
					if(future != null){
						try {
							future.channel().closeFuture().sync();
						} catch (InterruptedException e) {
							log.info("netty client 启动失败:"+e.getMessage());
						}
					}
					group.shutdownGracefully();
				}
			}
		}).start();
	}

}
