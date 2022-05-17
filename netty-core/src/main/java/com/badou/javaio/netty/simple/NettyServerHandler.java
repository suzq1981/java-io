package com.badou.javaio.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.channel().eventLoop().execute(() -> {
            long start = System.currentTimeMillis();
            for (; System.currentTimeMillis() - start < 1000 * 5; ) {
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("Commit to TaskQueue.", CharsetUtil.UTF_8));
        });

        ctx.channel().eventLoop().scheduleWithFixedDelay(() -> ctx.writeAndFlush(Unpooled.copiedBuffer("scheduleWithFixedDelay", CharsetUtil.UTF_8)), 1, 2, TimeUnit.SECONDS);

        ByteBuf buf = (ByteBuf) msg;

        System.out.println("客户端发送的内容：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello, Welcome too.", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
