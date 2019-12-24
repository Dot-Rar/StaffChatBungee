package com.perkelle.dev.staffchatbungee.messagequeue;

public enum MessageQueue {
    INSTANCE;

    private IMessageQueue messageQueue;

    public void setMessageQueue(IMessageQueue messageQueue) {
        INSTANCE.messageQueue = messageQueue;
    }

    public IMessageQueue getMessageQueue() {
        return messageQueue;
    }
}
