package com.slaghoedje.acechat.commands.format;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public class PlaceholderApiPlaceholderProcessor implements FormatPlaceholderProcessor {
    private Player sender;
    private Player receiver;

    public PlaceholderApiPlaceholderProcessor setPlayers(Player sender, Player receiver) {
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

        return PlaceholderAPI.setPlaceholders(target, toProcess);
    }
}
