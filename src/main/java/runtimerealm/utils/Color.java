package runtimerealm.utils;

import lombok.Getter;

@Getter
public enum Color {
    RESET("\u001B[0m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),

    // ANSI codes for additional formatting
    BOLD("\u001B[1m"),
    UNDERLINE("\u001B[4m");

    private final String code;

    Color(String code) {
        this.code = code;
    }

    public String colorize(String message) {
        return this.code + message + RESET.code;
    }
    public String bold(String message) {
        return BOLD.code + message + RESET.code;
    }
    public String underline(String message) {
        return UNDERLINE.code + message + RESET.code;
    }
    public String withBackgroundColor(String message, Color backgroundColor) {
        return backgroundColor.code + this.code + message + RESET.code;
    }
    public String combined(String message, Color bgColor, boolean isBold, boolean isUnderline) {
        String formattedMessage = "";
        if (isBold) {
            formattedMessage += BOLD.code;
        }
        if (isUnderline) {
            formattedMessage += UNDERLINE.code;
        }
        formattedMessage += this.code + bgColor.code + message + RESET.code;
        return formattedMessage;
    }
}