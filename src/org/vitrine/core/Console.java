package org.vitrine.core;

import lombok.Getter;
import org.fusesource.jansi.AnsiConsole;

public class Console {
    public enum Color {
        //FONT
        BOLD(1),
        UNDERLINE(4),
        BLINK(5),
        HIDDEN(8),
        BLACK(30),
        RED(31),
        GREEN(32),
        YELLOW(33),
        BLUE(34),
        MAGENTA(35),
        CYAN(36),
        WHITE(37),
        //BG
        BG_BLACK(40),
        BG_RED(41),
        BG_GREEN(42),
        BG_YELLOW(43),
        BG_BLUE(44),
        BG_MAGENTA(45),
        BG_CYAN(46),
        BG_WHITE(47),
        //SPECIAL
        BLACK_AND_BG_WHITE(7),
        RESET(0);

        @Getter
        private final int color;

        Color(int color) {
            this.color = color;
        }
    }

    public static void println(Object msg, Color color) {
        AnsiConsole.out.println("\033[" + color.getColor() + "m" + msg + "\033[" + Color.RESET.getColor() + "m");
    }

    public static void clear() {
        AnsiConsole.out.println("\033c");
    }
}
