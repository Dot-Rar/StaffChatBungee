package com.perkelle.dev.staffchatbungee.listeners;

import com.perkelle.dev.staffchatbungee.ChatMode;
import com.perkelle.dev.staffchatbungee.messagequeue.MessageQueue;
import com.perkelle.dev.staffchatbungee.messagequeue.StaffChatMessage;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(ChatEvent e) {
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();

        if(e.isCommand()) {
            return;
        }

        if(ChatMode.INSTANCE.isInStaffChat(p)) {
            e.setCancelled(true);

            StaffChatMessage message = new StaffChatMessage(p.getName(), p.getServer().getInfo().getName(), e.getMessage());
            MessageQueue.INSTANCE.getMessageQueue().publishMessage(message);
        }
    }
}
