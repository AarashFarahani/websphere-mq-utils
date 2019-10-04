package com.core.mqutil;

public class MqConfig {
    private String hostName;
    private int port;
    private String channel;
    private String userId;
    private String password;
    private int csid;
    private String queueManager;
    private String accessQueue;

    public String getHostName() {
        return this.hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCsid() {
        return this.csid;
    }

    public void setCsid(int csid) {
        this.csid = csid;
    }

    public String getQueueManager() {
        return this.queueManager;
    }

    public void setQueueManager(String queueManager) {
        this.queueManager = queueManager;
    }

    public String getAccessQueue() {
        return this.accessQueue;
    }

    public void setAccessQueue(String accessQueue) {
        this.accessQueue = accessQueue;
    }
}
