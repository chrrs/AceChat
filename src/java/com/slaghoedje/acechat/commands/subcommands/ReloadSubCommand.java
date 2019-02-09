package com.slaghoedje.acechat.commands.subcommands;

import org.bukkit.command.CommandSender;

import com.slaghoedje.acechat.AceChat;
import com.slaghoedje.acechat.util.I18n;

public class ReloadSubCommand extends SubCommand {
    private final AceChat aceChat;

    public ReloadSubCommand(AceChat aceChat) {
        super("acechat.admin.reload", "help.command-descriptions.reload", "reload", "rl", "r");

        this.aceChat = aceChat;
    }

    @Override
    public void onCommand(CommandSender sender, String mainLabel, String subLabel, String[] args) {
        sender.sendMessage(I18n.get("reload.before"));
        aceChat.reload();
        sender.sendMessage(I18n.get("reload.after"));
    }
}
