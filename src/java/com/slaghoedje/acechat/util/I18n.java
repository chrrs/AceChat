package com.slaghoedje.acechat.util;

import org.bukkit.configuration.file.FileConfiguration;

import com.slaghoedje.acechat.AceChat;

public class I18n {
    private static FileConfiguration messagesConfig;

    public static void load(AceChat aceChat) {
        messagesConfig = aceChat.loadFileConfiguration("messages.yml");
    }

    public static String format(String key, Object... format) {
        String value = messagesConfig.getString(key);
        return String.format(value == null ? key : value, format);
    }
}
