package com.badou.javaio.netty.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

public class TcpNettyServerHandler extends SimpleChannelInboundHandler<Message> {

    private int count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

        System.out.println(new String(msg.getContent()));
        System.out.println("服务端接收次数：" + (++count));

        byte[] content = UUID.randomUUID().toString().getBytes(CharsetUtil.UTF_8);
        ctx.writeAndFlush(Message.builder().content(content).length(content.length).build());
    }

}
