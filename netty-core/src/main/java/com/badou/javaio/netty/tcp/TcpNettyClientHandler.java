package com.badou.javaio.netty.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class TcpNettyClientHandler extends SimpleChannelInboundHandler<Message> {

    private int count = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 1; i <= 10; i++) {
            String msg = "Hi, Godson " + i;
            byte[] content = msg.getBytes(CharsetUtil.UTF_8);
            ctx.writeAndFlush(Message.builder().content(content).length(content.length).build());
        }
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        System.out.println(new String(msg.getContent()));
        System.out.println("客户端接收次数：" + (++count));
    }
}
