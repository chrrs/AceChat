package com.slaghoedje.acechat.commands.format;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DefaultPlaceholderProcessor implements FormatPlaceholderProcessor {
    private Player sender;
    private Player receiver;

    public DefaultPlaceholderProcessor setPlayers(Player sender, Player receiver) {
        this.sender = sender;
        this.receiver = receiver;
        return this;
    }

    @Override
    public String process(ChatFormatPart chatFormatPart, String toProcess) {
        Player target = null;
        if(chatFormatPart.placeholderTarget == ChatFormatPart.PlaceholderTarget.SENDER)
            target = sender;
        else if(chatFormatPart.placeholderTarget == ChatFormatPart.PlaceholderTarget.RECEIVER)
            target = receiver;

        assert target != null;
        return ChatColor.translateAlternateColorCodes('&', toProcess)
                .replaceAll("%player_name%", target.getName());
    }
}
