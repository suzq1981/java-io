package com.badou.javaio.netty.pipeline;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        try {
            new EchoServer(3333).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() throws Exception {
        EchoServerInHandler1 serverInHandler1 = new EchoServerInHandler1();
        EchoServerInHandler2 serverInHandler2 = new EchoServerInHandler2();
        EchoServerOutHandler1 serverOutHandler1 = new EchoServerOutHandler1();
        EchoServerOutHandler2 serverOutHandler2 = new EchoServerOutHandler2();

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel socketChannel) throws Exception {
                            System.out.println(socketChannel.pipeline().getClass().getName());
                            socketChannel.pipeline().addLast(serverInHandler1);
                            socketChannel.pipeline().addLast(serverInHandler2);
                            socketChannel.pipeline().addLast(serverOutHandler1);
                            socketChannel.pipeline().addLast(serverOutHandler2);//先于serverOutHandler1
                        }
                    });
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
