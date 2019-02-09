package com.slaghoedje.acechat.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import com.slaghoedje.acechat.AceChat;
import com.slaghoedje.acechat.commands.subcommands.*;
import com.slaghoedje.acechat.util.I18n;

public class ChatCommand implements TabExecutor {
    private List<SubCommand> subCommands;

    public ChatCommand(AceChat aceChat) {
        subCommands = new ArrayList<>();

        subCommands.add(new VersionSubCommand(aceChat));
        subCommands.add(new ClearSubCommand());
        subCommands.add(new HelpSubCommand(this));
        subCommands.add(new ReloadSubCommand(aceChat));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) return onCommand(sender, command, label, new String[] { "help" });

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

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(!sender.hasPermission("acechat.admin.help")) return new ArrayList<>();

        if(args.length == 1)
            return subCommands.stream().filter(subCommand -> subCommand.getPermission().isEmpty() || sender.hasPermission(subCommand.getPermission()))
                    .flatMap(subCommand -> Arrays.stream(subCommand.getAliases()))
                    .filter(subCommandAlias -> subCommandAlias.startsWith(args[0])).collect(Collectors.toList());

        return new ArrayList<>();
    }

    public List<SubCommand> getSubCommands() {
        return subCommands;
    }
}
