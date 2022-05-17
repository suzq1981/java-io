package com.badou.javaio.netty.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class EchoServerInHandler1 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("************************in1 执行************************");
        ByteBuf in = (ByteBuf) msg;
        System.out.println("in Server1 received: "+in.toString(CharsetUtil.UTF_8));
        //ctx.channel().writeAndFlush(Unpooled.wrappedBuffer((in.toString(CharsetUtil.UTF_8)+"-in server1-response").getBytes()));
        super.channelRead(ctx, Unpooled.wrappedBuffer(("test11111111111111111").getBytes()));
    }
}
