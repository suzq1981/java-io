package com.badou.javaio.netty.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class EchoServerInHandler2 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("************************in2 执行************************");
        ByteBuf in = (ByteBuf) msg;
        System.out.println("in Server2 received: " + in.toString(CharsetUtil.UTF_8));
        super.channelRead(ctx, msg);
        ctx.channel().writeAndFlush(Unpooled.wrappedBuffer("in server2-response".getBytes()));
    }
}
