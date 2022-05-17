package com.badou.javaio.netty.simple;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class NettyClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //ch.pipeline().addLast("IdleStateHandler",new IdleStateHandler(4, 4, 20, TimeUnit.SECONDS));
        ch.pipeline().addLast("NettyClientHandler", new NettyClientHandler());
    }

}
