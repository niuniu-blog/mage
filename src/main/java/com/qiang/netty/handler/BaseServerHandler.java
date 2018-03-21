package com.qiang.netty.handler;

import com.qiang.netty.session.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author zhangxinqiang
 */
public class BaseServerHandler extends ChannelInboundHandlerAdapter {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 空闲次数
     */
    private int idleCount = 1;

    /**
     * 最大空闲次数
     */
    private int maxIdleCount = 2;
    /**
     * 发送次数
     */
    private int count = 1;

    /**
     * 超时处理
     * 如果5秒没有接受客户端的心跳，就触发;
     * 如果超过两次，则直接关闭;
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) obj;
            //如果读通道处于空闲状态，说明没有接收到心跳命令
            if (IdleState.READER_IDLE.equals(event.state())) {
                logger.info("当前:" + ctx.channel().remoteAddress() + " 通道处于空闲状态次数:{}", idleCount);
                if (idleCount > maxIdleCount) {
                    logger.info("通道已关闭");
                    ctx.channel().close();
                    //TODO 上报离线消息
                }
                idleCount++;
            }
        } else {
            super.userEventTriggered(ctx, obj);
        }
    }

    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("检测到异常：" + cause.getMessage());
        logger.info("通道关闭");
        ctx.close();
    }

    /**
     * 业务逻辑处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("第" + count + "次" + ",服务端接受的消息:" + msg);
        String message = (String) msg;
        //如果是心跳命令，则发送给客户端;否则什么都不做
        if ("hb_request".equals(message)) {
            ctx.write("服务端成功收到心跳信息");
            ctx.flush();
        }
        count++;
    }

    /**
     * 建立连接时，返回消息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Session session = Session.createSession(ctx.channel());
        logger.info("客户端" + ctx.channel().remoteAddress() + "成功与服务端建立连接");
        ctx.writeAndFlush("成功链接服务器！");
        super.channelActive(ctx);
    }
}