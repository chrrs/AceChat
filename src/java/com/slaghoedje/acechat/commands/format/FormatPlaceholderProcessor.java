package com.slaghoedje.acechat.commands.format;

public interface FormatPlaceholderProcessor {
    String process(ChatFormatPart chatFormatPart, String toProcess);
}
