package com.badou.javaio.netty.simple;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class NettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //IdleStateHandler心跳检测主要是通过向线程任务队列中添加定时任务，判断channelRead()方法或write()方法是否调用空闲超时，
        // 如果超时则触发超时事件执行自定义userEventTrigger()方法；
        //最重要是他加入任务队列中，如果任务队列中有其它任务，心跳任务延迟时任务延时执行，当它执行指定多少时间间隔执行，超过了指定的时间，
        //也会触发相应的超时
        ch.pipeline().addLast("IdleStateHandler",new IdleStateHandler(5, 5, 20, TimeUnit.SECONDS));
        ch.pipeline().addLast("NettyServerHeartbeat",new NettyServerHeartbeat());
        ch.pipeline().addLast("NettyServerHandler", new NettyServerHandler());
    }

}
