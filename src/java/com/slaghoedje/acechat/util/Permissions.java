package com.slaghoedje.acechat.util;

import org.bukkit.entity.Player;

import net.milkbowl.vault.permission.Permission;

public class Permissions {
    public static Permission vaultPermission;

    public static boolean hasPermission(Player player, String permission) {
        if(vaultPermission != null) return vaultPermission.has(player, permission);
        else return player.hasPermission(permission);
    }
}
