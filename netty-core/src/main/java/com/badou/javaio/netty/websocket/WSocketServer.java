package com.badou.javaio.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.GlobalEventExecutor;

public class WSocketServer {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(5);
        DefaultChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            //HttpServerCodec是netty针对http编解码的处理类，但是这些只能处理像http get的请求,也就是数据带在url问号后面的http请求
                            ch.pipeline().addLast(new HttpServerCodec());
                            ch.pipeline().addLast(new ChunkedWriteHandler());
                            //HttpObjectAggregator这个netty的处理器就是为了解决Post请求获取Message-body内容.它把HttpMessage和HttpContent聚合成为一个FullHttpRquest或者FullHttpRsponse
                            ch.pipeline().addLast(new HttpObjectAggregator(8192));
                            ch.pipeline().addLast(new WebSocketServerProtocolHandler("/talk"));
                            ch.pipeline().addLast(new WSocketServerHandler(channelGroup));
                        }
                    });

            ChannelFuture channelFuture = bootstrap.bind(1000).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
