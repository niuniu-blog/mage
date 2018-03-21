package com.qiang.netty.session;


import io.netty.channel.Channel;

/**
 * 标识链接通道的类
 *
 * @author zhangxinqiang
 * @date 21/03/2018
 */
public class Session {
    private Session() {
    }

    /**
     * Session的唯一标识
     */
    private String id;
    /**
     * 通道
     */
    private Channel channel;

    private long lastCommunicateTimeStamp = 0L;

    public static Session createSession(Channel channel) {
        Session session = new Session();
        session.setId(channel.id().asLongText());
        session.setChannel(channel);
        session.setLastCommunicateTimeStamp(System.currentTimeMillis());
        return session;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public Channel getChannel() {
        return channel;
    }

    private void setChannel(Channel channel) {
        this.channel = channel;
    }

    public long getLastCommunicateTimeStamp() {
        return lastCommunicateTimeStamp;
    }

    private void setLastCommunicateTimeStamp(long lastCommunicateTimeStamp) {
        this.lastCommunicateTimeStamp = lastCommunicateTimeStamp;
    }
}
