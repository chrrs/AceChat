package com.slaghoedje.acechat.util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import com.slaghoedje.acechat.AceChat;

public class I18n {
    private static FileConfiguration messagesConfig;

    public static void load(AceChat aceChat) {
        messagesConfig = aceChat.loadFileConfiguration("messages.yml");
    }

    public static String get(String key) {
        String value = messagesConfig.getString(key);
        return ChatColor.translateAlternateColorCodes('&', value == null ? key : value);
    }
}
