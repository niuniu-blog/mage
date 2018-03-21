package com.qiang.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;

/**
 * 服务处理类工厂
 *
 * @author zhangxinqiang
 * @date 21/03/2018
 */
public class ServerHandlerFactory {
    /**
     * 获取一个实际处理类
     *
     * @return 实际做处理的类
     */
    public static ChannelHandler getHandler() {
        return new ChannelDuplexHandler();
    }
}
