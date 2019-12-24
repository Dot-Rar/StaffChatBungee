package com.perkelle.dev.staffchatbungee;

import com.perkelle.dev.staffchatbungee.messagequeue.MessageQueue;
import com.perkelle.dev.staffchatbungee.messagequeue.RedisMessageQueue;
import com.perkelle.dev.staffchatbungee.messagequeue.StaffChatMessage;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/*
    Expose methods for easy access
 */
public class StaffChatBungeeAPI {

    public boolean isRedis() {
        return MessageQueue.INSTANCE.getMessageQueue() instanceof RedisMessageQueue;
    }

    public void publishMessage(StaffChatMessage message) {
        MessageQueue.INSTANCE.getMessageQueue().publishMessage(message);
    }

    public void publishMessage(ProxiedPlayer from, String message) {
        StaffChatMessage scm = new StaffChatMessage(from.getName(), from.getServer().getInfo().getName(), message);
        publishMessage(scm);
    }

    public void publishMessage(String from, String serverName, String message) {
        StaffChatMessage scm = new StaffChatMessage(from, serverName, message);
        publishMessage(scm);
    }
}
