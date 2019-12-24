package com.perkelle.dev.staffchatbungee.messagequeue;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class StaffChatMessage {

    private final String from;
    private final String serverName;
    private final String message;

    public StaffChatMessage(String from, String serverName, String message) {
        this.from = from;
        this.serverName = serverName;
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public String getServerName() {
        return serverName;
    }

    public String getMessage() {
        return message;
    }
}
