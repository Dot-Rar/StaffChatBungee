package com.perkelle.dev.staffchatbungee.commands;

import com.google.common.base.Strings;
import com.perkelle.dev.staffchatbungee.ChatMode;
import com.perkelle.dev.staffchatbungee.messagequeue.MessageQueue;
import com.perkelle.dev.staffchatbungee.messagequeue.StaffChatMessage;
import com.perkelle.dev.staffchatbungee.utils.MessageFormatter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatCommand extends Command {

    public StaffChatCommand() {
        super("staffchat", "", "sc");
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

        if(args.length == 0) {
            sender.sendMessage(TextComponent.fromLegacyText(MessageFormatter.formatCommandResponse("lang.missing-message")));
            return;
        }

        StaffChatMessage message = new StaffChatMessage(p.getName(), p.getServer().getInfo().getName(), String.join(" ", args));
        MessageQueue.INSTANCE.getMessageQueue().publishMessage(message);
    }
}
