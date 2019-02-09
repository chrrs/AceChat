package com.slaghoedje.acechat.commands.format;

import java.util.List;

import net.md_5.bungee.api.chat.ClickEvent;

public class ChatFormatPart {
    public ChatFormatPart() {
        text = "";
        hoverLines = null;
        clickText = "";
        placeholderTarget = PlaceholderTarget.SENDER;
    }

    public String text;
    public List<String> hoverLines;
    public ClickEvent.Action clickAction;
    public String clickText;
    public PlaceholderTarget placeholderTarget;

    public enum PlaceholderTarget {
        SENDER, RECEIVER
    }
}
