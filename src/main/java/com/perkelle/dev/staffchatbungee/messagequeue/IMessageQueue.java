package com.perkelle.dev.staffchatbungee.messagequeue;

import java.util.function.Consumer;

public interface IMessageQueue {
    void connect();
    void publishMessage(StaffChatMessage message);
    Consumer<StaffChatMessage> getMessageConsumer();
}
