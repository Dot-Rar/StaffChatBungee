package com.perkelle.dev.staffchatbungee.messagequeue;

import com.google.common.collect.ImmutableMap;
import com.perkelle.dev.staffchatbungee.utils.MessageFormatter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Map;
import java.util.function.Consumer;

public class LocalMessageQueue implements IMessageQueue {

    @Override
    public void connect() {
    }

    @Override
    public void publishMessage(StaffChatMessage message) {
        getMessageConsumer().accept(message);
    }

    @Override
    public Consumer<StaffChatMessage> getMessageConsumer() {
        return result -> {
            Map<String, String> placeholders = ImmutableMap.<String, String> builder()
                    .put("%server%", result.getServerName())
                    .put("%player%", result.getFrom())
                    .put("%message%", result.getMessage())
                    .build();

            String message = MessageFormatter.formatStaffChatMessage(placeholders);

            ProxyServer.getInstance().getPlayers().stream()
                    .filter(p -> p.hasPermission("staffchat.receive") || p.hasPermission("staffchat.*"))
                    .forEach(p -> p.sendMessage(TextComponent.fromLegacyText(message)));
        };
    }
}
