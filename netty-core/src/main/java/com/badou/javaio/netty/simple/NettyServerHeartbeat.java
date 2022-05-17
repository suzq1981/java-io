package com.badou.javaio.netty.simple;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class NettyServerHeartbeat extends ChannelInboundHandlerAdapter {

    //IdleStateHandler心跳检测主要是通过向线程任务队列中添加定时任务，判断channelRead()方法或write()方法是否调用空闲超时，
    // 如果超时则触发超时事件执行自定义userEventTrigger()方法；
    //最重要是他加入任务队列中，如果任务队列中有其它任务，心跳任务延迟时任务延时执行，当它执行指定多少时间间隔执行，超过了指定的时间，
    //也会触发相应的超时
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.ALL_IDLE) {//读写空闲时关闭通道
                ctx.channel().close();
            }
            System.out.println("IdleState: " + event.state());
        }
    }
}
