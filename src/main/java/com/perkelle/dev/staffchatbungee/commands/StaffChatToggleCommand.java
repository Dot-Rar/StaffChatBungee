package com.perkelle.dev.staffchatbungee.commands;

import com.perkelle.dev.staffchatbungee.ChatMode;
import com.perkelle.dev.staffchatbungee.utils.MessageFormatter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatToggleCommand extends Command {

    public StaffChatToggleCommand() {
        super("sctoggle", "", "staffchattoggle");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(TextComponent.fromLegacyText("Only players can use this command"));
            return;
        }

        ProxiedPlayer p = (ProxiedPlayer) sender;

        if(!p.hasPermission("staffchat.use")) {
            sender.sendMessage(TextComponent.fromLegacyText(MessageFormatter.formatCommandResponse("lang.no-permission")));
            return;
        }

        boolean isInStaffChat = ChatMode.INSTANCE.isInStaffChat(p);
        ChatMode.INSTANCE.setInStaffChat(p, !isInStaffChat);

        if(isInStaffChat) {
            sender.sendMessage(TextComponent.fromLegacyText(MessageFormatter.formatCommandResponse("lang.channel-off")));
        } else {
            sender.sendMessage(TextComponent.fromLegacyText(MessageFormatter.formatCommandResponse("lang.channel-on")));
        }
    }
}
