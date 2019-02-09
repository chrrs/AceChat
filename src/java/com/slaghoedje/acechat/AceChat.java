package com.slaghoedje.acechat;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.slaghoedje.acechat.commands.ChatCommand;
import com.slaghoedje.acechat.commands.format.ChatFormats;
import com.slaghoedje.acechat.util.Config;
import com.slaghoedje.acechat.util.I18n;

public class AceChat extends JavaPlugin {
    public boolean placeholderApiPresent = false;

    @Override
    public void onEnable() {
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            placeholderApiPresent = true;
            getLogger().info("Hooked into PlaceholderAPI");
        }

        I18n.load(this);
        Config.load(this);
        ChatFormats.load(this);

        Bukkit.getPluginManager().registerEvents(new ChatEventListener(this), this);

        TabExecutor chatCommand = new ChatCommand(this);
        getCommand("chat").setTabCompleter(chatCommand);
        getCommand("chat").setExecutor(chatCommand);

        getLogger().info("Enabled AceChat v" + getDescription().getVersion());
    }

    public FileConfiguration loadFileConfiguration(String name) {
        try {
            File file = new File(getDataFolder(), name);

            if(!file.exists()) {
                file.getParentFile().mkdirs();
                saveResource(name, false);
            }

            YamlConfiguration yamlConfiguration = new YamlConfiguration();
            yamlConfiguration.load(file);
            return yamlConfiguration;
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void reload() {
        I18n.load(this);
        Config.load(this);
        ChatFormats.load(this);
    }
}
