package com.lion.frame.websocket;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * netty广播类
 * @author Administrator
 *
 */
public class ChannelManage {
	
	private static ChannelGroup GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	public static void addChannel(Channel c){
		if (!GROUP.contains(c)) {
			GROUP.add(c);
		}
	}
	
	public static void removeChannel(Channel c){
		GROUP.remove(c);
	}
	
	/**
	 * 
	 * @Title: broadcast 
	 * @Description: 广播
	 * @param msg
	 * @param matcher
	 * @return ChannelGroupFuture    返回类型 
	 * @throws
	 */
	public static ChannelGroupFuture broadcast(Object msg, ChannelMatcher matcher) {
        return GROUP.writeAndFlush(msg, matcher);
    }
	/**
	 * 
	 * @Title: broadcast 
	 * @Description: 广播
	 * @param msg
	 * @return ChannelGroupFuture    返回类型 
	 * @throws
	 */
	public static ChannelGroupFuture broadcast(Object msg) {
		return GROUP.writeAndFlush(msg);
	}
	
}