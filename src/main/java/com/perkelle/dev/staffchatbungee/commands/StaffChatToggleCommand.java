package com.perkelle.dev.staffchatbungee.commands;

import com.perkelle.dev.staffchatbungee.ChatMode;
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

        boolean isInStaffChat = ChatMode.INSTANCE.isInStaffChat(p);
        ChatMode.INSTANCE.setInStaffChat(p, !isInStaffChat);

        if(isInStaffChat) {
            sender.sendMessage();
        }
    }
}
