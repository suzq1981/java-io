package com.badou.javaio.netty.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.ImmediateEventExecutor;

public class NettyGroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channelGroup.writeAndFlush("客户端[" + ctx.channel().remoteAddress().toString().substring(1) + "] 上线");
        channelGroup.add(ctx.channel());
        super.handlerAdded(ctx);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        StringBuilder content = new StringBuilder();
        content.append(ctx.channel().remoteAddress().toString().substring(1)).append(" Say:").append(msg);

        channelGroup.forEach(ch -> {
            if (ch != ctx.channel()) {
                ch.writeAndFlush(content);
            }
        });
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        channelGroup.writeAndFlush("客户端[" + ctx.channel().remoteAddress().toString().substring(1) + "] 离线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }

}
