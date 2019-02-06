package com.slaghoedje.acechat.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.slaghoedje.acechat.AceChat;
import com.slaghoedje.acechat.commands.subcommands.SubCommand;
import com.slaghoedje.acechat.commands.subcommands.VersionSubCommand;
import com.slaghoedje.acechat.util.I18n;

public class ChatCommand implements CommandExecutor {
    private List<SubCommand> subCommands;

    public ChatCommand(AceChat aceChat) {
        subCommands = new ArrayList<>();

        subCommands.add(new VersionSubCommand(aceChat));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) return onCommand(sender, command, label, new String[] { "version" });

        for (SubCommand subCommand : subCommands) {
            for (String alias : subCommand.getAliases()) {
                if (args[0].equalsIgnoreCase(alias)) {
                    if (!subCommand.getPermission().isEmpty() && !sender.hasPermission(subCommand.getPermission())) {
                        sender.sendMessage(I18n.get("error.nopermission")
                                .replaceAll("%permission%", subCommand.getPermission()));
                        return true;
                    }

                    String[] subArgs = Arrays.copyOfRange(args, 1, args.length);

                    subCommand.onCommand(sender, label, args[0], subArgs);
                    return true;
                }
            }
        }

        sender.sendMessage(I18n.get("error.invalidsubcommand")
                .replaceAll("%maincommandlabel%", label)
                .replaceAll("%subcommandlabel%", args[0]));
        return true;
    }
}
