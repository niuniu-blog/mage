package com.qiang.netty.server;

import com.qiang.netty.filter.NettyServerFilter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务类
 *
 * @author zhangxinqiang
 * @date 21/03/2018
 */
public class NettyServer {
    private Logger logger = LoggerFactory.getLogger(getClass());
    //设置服务端端口
    private int port;
    //服务名
    private String serverName;
    // 通过nio方式来接收连接和处理连接
    private static EventLoopGroup group = new NioEventLoopGroup();
    private static ServerBootstrap serverBootstrap = new ServerBootstrap();

    public void start() {
        try {
            serverBootstrap.group(group);
            serverBootstrap.channel(NioServerSocketChannel.class);
            //设置过滤器
            serverBootstrap.childHandler(new NettyServerFilter());
            // 服务器绑定端口监听
            ChannelFuture f = serverBootstrap.bind(port).sync();
            logger.info("[" + serverName + "]启动成功,端口是:" + port);
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //关闭EventLoopGroup，释放掉所有资源包括创建的线程
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.setPort(1111);
        nettyServer.setServerName("123123");
        nettyServer.start();
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
