package com.slaghoedje.acechat.commands.subcommands;

import org.bukkit.command.CommandSender;

import com.slaghoedje.acechat.AceChat;
import com.slaghoedje.acechat.util.I18n;

public class VersionSubCommand extends SubCommand {
    private final AceChat aceChat;

    public VersionSubCommand(AceChat aceChat) {
        super("acechat.admin.version", "help.command-descriptions.version", "version", "ver", "v");
        this.aceChat = aceChat;
    }

    @Override
    public void onCommand(CommandSender sender, String mainLabel, String subLabel, String[] args) {
        sender.sendMessage(I18n.get("version").replaceAll("%version%", aceChat.getDescription().getVersion()));
    }
}
