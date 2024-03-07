package ru.blatfan.blatlibs.util.colors;

public enum ANSIColors {
    BLACK ("\u001B[30m"),
    RED ("\u001B[31m"),
    GREEN ("\u001B[32m"),
    YELLOW ("\u001B[33m"),
    BLUE ("\u001B[34m"),
    PURPLE ("\u001B[35m"),
    CYAN ("\u001B[36m"),
    WHITE ("\u001B[37m"),
    BLACK_BRIGHT ("\u001B[90m"),
    RED_BRIGHT ("\u001B[91m"),
    GREEN_BRIGHT ("\u001B[92m"),
    YELLOW_BRIGHT ("\u001B[93m"),
    BLUE_BRIGHT ("\u001B[94m"),
    PURPLE_BRIGHT ("\u001B[95m"),
    CYAN_BRIGHT ("\u001B[96m"),
    WHITE_BRIGHT ("\u001B[97m"),
    RESET ("\u001B[0m");

    private ANSIColors(String color) {

    }
}
