package com.slaghoedje.acechat.commands.subcommands;

import org.bukkit.command.CommandSender;

import com.slaghoedje.acechat.commands.ChatCommand;
import com.slaghoedje.acechat.util.I18n;

public class HelpSubCommand extends SubCommand {
    private final ChatCommand chatCommand;

    public HelpSubCommand(ChatCommand chatCommand) {
        super("acechat.admin.help", "help.command-descriptions.help", "help", "?", "h");
        this.chatCommand = chatCommand;
    }

    @Override
    public void onCommand(CommandSender sender, String mainLabel, String subLabel, String[] args) {
        sender.sendMessage(I18n.get("help.header"));

        for(SubCommand subCommand : chatCommand.getSubCommands()) {
            if(!subCommand.getPermission().isEmpty() && !sender.hasPermission(subCommand.getPermission())) continue;

            sender.sendMessage(I18n.get("help.item")
                    .replaceAll("%command%", String.format("/%s %s", mainLabel, subCommand.getAliases()[0]))
                    .replaceAll("%description%", I18n.get(subCommand.getDescription())));
        }
    }
}
