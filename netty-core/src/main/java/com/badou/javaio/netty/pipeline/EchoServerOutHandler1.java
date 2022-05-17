package com.badou.javaio.netty.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.nio.charset.Charset;

public class EchoServerOutHandler1 extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("************************out1 执行************************");
        ByteBuf byteMsg = (ByteBuf) msg;
        System.out.println("out1 received:" + byteMsg.toString(Charset.forName("utf8")));
        super.write(ctx, msg, promise);
    }
}