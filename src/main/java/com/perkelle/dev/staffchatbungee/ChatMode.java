package com.perkelle.dev.staffchatbungee;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public enum ChatMode {
    INSTANCE;

    private List<UUID> inStaffChat = new ArrayList<>();

    public boolean isInStaffChat(ProxiedPlayer p) {
        return inStaffChat.contains(p.getUniqueId());
    }

    public void setInStaffChat(ProxiedPlayer p, boolean newState) {
        if(newState) {
            inStaffChat.add(p.getUniqueId());
        } else {
            inStaffChat.remove(p.getUniqueId());
        }
    }
}
