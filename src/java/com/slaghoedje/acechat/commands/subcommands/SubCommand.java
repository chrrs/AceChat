package com.slaghoedje.acechat.commands.subcommands;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {
    private final String permission;
    private final String description;
    private final String[] aliases;

    public SubCommand(String permission, String description, String... aliases) {
        this.permission = permission;
        this.description = description;
        this.aliases = aliases;
    }

    public abstract void onCommand(CommandSender sender, String mainLabel, String subLabel, String[] args);

    public String getPermission() {
        return permission;
    }

    public String getDescription() {
        return description;
    }

    public String[] getAliases() {
        return aliases;
    }
}
