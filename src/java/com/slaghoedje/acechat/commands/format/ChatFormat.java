package com.slaghoedje.acechat.commands.format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ChatFormat {
    public List<ChatFormatPart> parts;

    public ChatFormat(FileConfiguration toRead) throws IllegalArgumentException {
        parts = new ArrayList<>();

        ConfigurationSection partsConfig = toRead.getConfigurationSection("parts");
        if(partsConfig == null) throw new IllegalArgumentException("Error while parsing root element 'parts'");

        for(String partName : partsConfig.getKeys(false)) {
            ConfigurationSection partConfig = partsConfig.getConfigurationSection(partName);
            if(partConfig == null) throw new IllegalArgumentException(String.format("Error while parsing part '%s'", partName));

            ChatFormatPart part = new ChatFormatPart();
            part.text = partConfig.getString("text", "");
            part.hoverLines = partConfig.isString("hover") ?
                    Collections.singletonList(partConfig.getString("hover", "")) :
                    partConfig.getStringList("hover");

            try {
                part.clickAction = ClickEvent.Action.valueOf(partConfig.getString("click.type"));
                part.clickText = partConfig.getString("click.text");
            } catch(Exception ignored) { }

            try {
                part.placeholderTarget = ChatFormatPart.PlaceholderTarget.valueOf(partConfig.getString("placeholder-target"));
            } catch(Exception ignored) { }

            parts.add(part);
        }
    }

    public TextComponent constructTextComponent(FormatPlaceholderProcessor... placeholderProcessors) {
        TextComponent base = new TextComponent("");

        for(ChatFormatPart chatFormatPart : parts) {
            TextComponent part = new TextComponent("");

            String text = chatFormatPart.text;
            if(text == null) text = "";

            for(FormatPlaceholderProcessor placeholderProcessor : placeholderProcessors)
                text = placeholderProcessor.process(chatFormatPart, text);

            for(BaseComponent baseComponent : TextComponent.fromLegacyText(text))
                part.addExtra(baseComponent);

            List<String> hoverLines = chatFormatPart.hoverLines;
            if(hoverLines != null && hoverLines.size() != 0 && !(hoverLines.size() == 1 && hoverLines.get(0).isEmpty())) {
                hoverLines = hoverLines.stream().map(hoverLine -> {
                    for(FormatPlaceholderProcessor placeholderProcessor : placeholderProcessors)
                        hoverLine = placeholderProcessor.process(chatFormatPart, hoverLine);

                    return hoverLine;
                }).collect(Collectors.toList());

                part.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(String.join("\n", hoverLines))));
            }

            if(chatFormatPart.clickAction != null && chatFormatPart.clickText != null) {
                String formattedClickText = chatFormatPart.clickText;

                for(FormatPlaceholderProcessor placeholderProcessor : placeholderProcessors)
                    formattedClickText = placeholderProcessor.process(chatFormatPart, formattedClickText);

                part.setClickEvent(new ClickEvent(chatFormatPart.clickAction, formattedClickText));
            }

            base.addExtra(part);
        }

        return base;
    }
}
