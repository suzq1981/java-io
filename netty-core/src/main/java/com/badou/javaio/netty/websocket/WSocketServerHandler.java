package com.badou.javaio.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class WSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ChannelGroup channelGroup;

    public WSocketServerHandler(ChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        String content = "客户端[" + ctx.channel().remoteAddress().hashCode() + "] 上线";
        channelGroup.writeAndFlush(new TextWebSocketFrame(content));
        channelGroup.add(ctx.channel());
        super.handlerAdded(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        StringBuilder content = new StringBuilder();
        content.append(ctx.channel().remoteAddress().hashCode()).append(" Say:").append(msg.text());
        channelGroup.forEach(ch -> {
            if (ch != ctx.channel()) {
                ch.writeAndFlush(new TextWebSocketFrame(content.toString()));
            } else {
                ch.writeAndFlush(new TextWebSocketFrame("[自己]：" + msg.text()));
            }
        });
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String content = "客户端[" + ctx.channel().remoteAddress().hashCode()  + "] 离线";
        channelGroup.writeAndFlush(new TextWebSocketFrame(content));
        super.handlerRemoved(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }

}
