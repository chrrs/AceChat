package com.slaghoedje.acechat;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.slaghoedje.acechat.util.Permissions;

import net.milkbowl.vault.permission.Permission;

public class AceChat extends JavaPlugin {
    public boolean placeholderApiPresent = false;

    @Override
    public void onEnable() {
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            placeholderApiPresent = true;
            getLogger().info("Hooked into PlaceholderAPI");
        }

        if(Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
            Permission permission = permissionProvider.getProvider();
            if(permission != null) {
                Permissions.vaultPermission = permission;
                getLogger().info("Hooked into Vault Permissions with provider " + permission.getName());
            } else getLogger().warning("Tried to hook into Vault permissions, but no permission plugin found!");
        }

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
}
