package com.slaghoedje.acechat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.slaghoedje.acechat.commands.format.ChatFormat;
import com.slaghoedje.acechat.commands.format.ChatFormats;
import com.slaghoedje.acechat.commands.format.DefaultPlaceholderProcessor;
import com.slaghoedje.acechat.commands.format.PlaceholderApiPlaceholderProcessor;
import com.slaghoedje.acechat.util.I18n;

import net.md_5.bungee.api.chat.BaseComponent;

public class ChatEventListener implements Listener {
    private final PlaceholderApiPlaceholderProcessor placeholderApiProcessor;
    private final DefaultPlaceholderProcessor defaultPlaceholderProcessor;
    private final AceChat aceChat;

    public ChatEventListener(AceChat aceChat) {
        this.aceChat = aceChat;

        placeholderApiProcessor = new PlaceholderApiPlaceholderProcessor();
        defaultPlaceholderProcessor = new DefaultPlaceholderProcessor();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (!player.hasPermission("acechat.user.chat")) {
            player.sendMessage(I18n.get("error.nopermission")
                    .replaceAll("%permission%", "acechat.user.chat"));
            return;
        }

        ChatFormat chatFormat = ChatFormats.getFormatFromConfig(player, "chat");
        if(chatFormat == null) {
            aceChat.getLogger().warning("Invalid global chat format! Check config!");
            return;
        }

        BaseComponent broadcastMessage = chatFormat.constructTextComponent(defaultPlaceholderProcessor.setPlayers(player, player),
                placeholderApiProcessor.setPlayers(player, player),
                ((chatFormatPart, toProcess) -> {
                    String returnValue;

                    if(player.hasPermission("acechat.user.color"))
                        returnValue = toProcess.replaceAll("%message%", ChatColor.translateAlternateColorCodes('&', message));
                    else
                        returnValue = toProcess.replaceAll("%message%", message);

                    return returnValue;
                }));
        Bukkit.spigot().broadcast(broadcastMessage);
        aceChat.getLogger().info(player.getName() + ": " + message);
    }
}
