package com.slaghoedje.acechat.util;

import org.bukkit.configuration.file.FileConfiguration;

import com.slaghoedje.acechat.AceChat;

public class Config {
    private static FileConfiguration config;

    public static void load(AceChat aceChat) {
        config = aceChat.loadFileConfiguration("config.yml");
    }

    public static FileConfiguration getConfig() {
        return config;
    }
}
