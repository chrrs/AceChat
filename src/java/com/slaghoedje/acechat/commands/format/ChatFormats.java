package com.slaghoedje.acechat.commands.format;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import com.slaghoedje.acechat.AceChat;
import com.slaghoedje.acechat.util.Config;

public class ChatFormats {
    private static Map<String, ChatFormat> chatFormats;

    public static void load(AceChat aceChat) {
        chatFormats = new HashMap<>();
        List<String> formatFileNames = new ArrayList<>();

        File formatsFolder = new File(aceChat.getDataFolder(), "formats");

        if(formatsFolder.exists() && formatsFolder.isDirectory()) {
            formatFileNames = Arrays.stream(Objects.requireNonNull(formatsFolder.list()))
                    .filter(formatFileName -> formatFileName.endsWith(".yml")).collect(Collectors.toList());
        }

        if(formatFileNames.isEmpty()) {
            formatFileNames.addAll(Collections.singletonList("chat.yml"));
        }

        for(String formatFileName : formatFileNames) {
            FileConfiguration formatConfig = aceChat.loadFileConfiguration("formats/" + formatFileName);
            ChatFormat chatFormat = new ChatFormat(formatConfig);
            chatFormats.put(formatFileName.substring(0, formatFileName.lastIndexOf('.')), chatFormat);
        }
    }

    public static ChatFormat getFormatFromConfig(Player player, String formatName) {
        if(Config.getConfig().isString("formats." + formatName)) {
            return chatFormats.get(Config.getConfig().getString("formats." + formatName));
        } else if(Config.getConfig().isConfigurationSection("formats." + formatName)) {
            ConfigurationSection formatSection = Config.getConfig().getConfigurationSection("formats." + formatName);

            String permissionPrefix = "acechat.format.";
            List<String> formatPermissions = new ArrayList<>();

            for(PermissionAttachmentInfo attachmentInfo : player.getEffectivePermissions()) {
                String permission = attachmentInfo.getPermission();
                if(permission.startsWith(permissionPrefix))
                    formatPermissions.add(permission.substring(permissionPrefix.length()));
            }

            for(String key : formatSection.getKeys(false)) {
                if(formatPermissions.contains(key))
                    return chatFormats.get(formatSection.getString(key));
            }

            return null;
        } else return null;
    }
}
