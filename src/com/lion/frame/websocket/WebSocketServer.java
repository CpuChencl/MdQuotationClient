package com.lion.frame.websocket;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.lion.frame.logger.Log4jManager;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

//@Component
//@Lazy(false)
public final class WebSocketServer {
	private static final Logger log = Log4jManager.get();

	@Value("${WEBSOCKET.SLL}")
    private Boolean SSL;
    @Autowired
    private WebSocketServerInitializer webSocketServerInitializer;
    private Channel ch = null;
    EventLoopGroup bossGroup = null;
    EventLoopGroup workerGroup = null;
    
    @PostConstruct
    public void init(){
    	// Configure SSL.
	    int port = SSL != null && SSL ? 8444 : 9000;
        try {
	        bossGroup = new NioEventLoopGroup(1);
	        workerGroup = new NioEventLoopGroup();
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(webSocketServerInitializer);

            ChannelFuture sync = b.bind(port).sync();
            
            ch = sync.channel();
        } catch (Exception e) {
        	log.error("启动websocket服务失败！！");
			e.printStackTrace();
			if (bossGroup != null) {
				bossGroup.shutdownGracefully();
			}
			if (workerGroup != null) {
				workerGroup.shutdownGracefully();
			}
		}
        log.info("启动websocket服务成功！！");
    }
    @PreDestroy
    public void destory(){
    	log.error("销毁websocket服务！！");
    	if (ch != null) {
			ch.close();
		}
    	if (bossGroup != null) {
    		bossGroup.shutdownGracefully();
    	}
    	if (workerGroup != null) {
    		workerGroup.shutdownGracefully();
    	}
    }
}