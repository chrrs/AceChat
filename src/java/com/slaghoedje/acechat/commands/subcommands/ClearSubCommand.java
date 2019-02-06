package com.slaghoedje.acechat.commands.subcommands;

import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.slaghoedje.acechat.util.Config;
import com.slaghoedje.acechat.util.I18n;

public class ClearSubCommand extends SubCommand {
    public ClearSubCommand() {
        super("acechat.admin.clear", "help.command-descriptions.clear", "clear", "cl", "c");
    }

    @Override
    public void onCommand(CommandSender sender, String mainLabel, String subLabel, String[] args) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            int clearLines = Config.getConfig().getInt("clear-length");
            if(player.hasPermission("acechat.admin.clear.bypass")) clearLines = 1;

            player.sendMessage(String.join("\n", Collections.nCopies(clearLines, "\u00A7r ")));
            player.sendMessage(I18n.get("chatclear").replaceAll("%player%", sender.getName()));
        }
    }
}
