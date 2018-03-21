package com.qiang.netty.filter;

import com.qiang.netty.handler.BaseServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Title: HelloServerInitializer
 * Description: Netty 服务端过滤器
 * Version:1.0.0
 *
 * @author Administrator
 * @date 2017-8-31
 */
public class NettyServerFilter extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline ph = ch.pipeline();
        ph.addLast(new LineBasedFrameDecoder(1));
        ph.addLast("decoder", new StringDecoder());
        ph.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
        ph.addLast("handler", new BaseServerHandler());
    }
}