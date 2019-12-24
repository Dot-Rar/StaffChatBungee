package com.perkelle.dev.staffchatbungee.utils;

import com.perkelle.dev.staffchatbungee.ConfigManager;
import net.md_5.bungee.api.ChatColor;

import java.util.Map;

public class MessageFormatter {

    public static String translateColour(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static String formatStaffChatMessage(Map<String, String> placeholders) {
        String message = ConfigManager.getConfig().getString("lang.message-format", "&8[&4StaffChat: %server%&8] &f%player%: %message%");
        message = translateColour(message);

        for(Map.Entry<String, String > placeholder : placeholders.entrySet()) {
            message = message.replace(placeholder.getKey(), placeholder.getValue());
        }

        return  message;
    }

    public static String formatCommandResponse(String configKey) {
        String prefix = ConfigManager.getConfig().getString("lang.prefix", "&8[&4StaffChat&8] &f");
        String message = ConfigManager.getConfig().getString(configKey);

        return translateColour(prefix + message);
    }
}
