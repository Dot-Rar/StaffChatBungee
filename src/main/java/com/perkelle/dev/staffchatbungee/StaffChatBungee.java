package com.perkelle.dev.staffchatbungee;

import com.perkelle.dev.staffchatbungee.messagequeue.IMessageQueue;
import com.perkelle.dev.staffchatbungee.messagequeue.LocalMessageQueue;
import com.perkelle.dev.staffchatbungee.messagequeue.MessageQueue;
import com.perkelle.dev.staffchatbungee.messagequeue.RedisMessageQueue;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;

public class StaffChatBungee extends Plugin  {

    private static StaffChatBungee instance;

    public static StaffChatBungee getInstance() {
        return instance;
    }

    private static void setInstance(StaffChatBungee instance) {
        StaffChatBungee.instance = instance;
    }

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        setInstance(this);

        // Load the config
        configManager = new ConfigManager(this);

        try {
            configManager.load();
        } catch(IOException ex) {
            getLogger().severe("Error while loading StaffChatBungee config, disabling");
            ex.printStackTrace();
            return;
        }

        // Set message queue and call initialisation code
        IMessageQueue messageQueue;
        if(ConfigManager.getConfig().getBoolean("redis.enable", false)) {
            messageQueue = new RedisMessageQueue();
        } else {
            messageQueue = new LocalMessageQueue();
        }

        MessageQueue.INSTANCE.setMessageQueue(messageQueue);
        messageQueue.connect();

        // Register commands
    }
}
