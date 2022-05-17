package com.badou.javaio.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

public class NettyGroupChatClient {

    public static void main(String[] args) throws InterruptedException {
        NettyGroupChatClient client = new NettyGroupChatClient();
        ChannelFuture future = client.connect("127.0.0.1", 1000);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            future.channel().writeAndFlush(message);
        }
        scanner.close();
    }

    public ChannelFuture connect(String host, int port) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new NettyGroupChatClientChannelInitializer());

        return bootstrap.connect(host, port).sync();
    }

}
