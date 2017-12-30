package com.bigpaws;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Created by Jerry Shea on 29/12/17.
 */
public class Square {
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_BLACK = "\u001B[30m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_YELLOW = "\u001B[93m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_PURPLE = "\u001B[35m";
    static final String ANSI_CYAN = "\u001B[36m";
    static final String ANSI_WHITE = "\u001B[37m";
    static final String ANSI_ORANGE = "\u001B[33m";
    static final String ANSI_MAGENTA = "\u001B[95m";
    static final String ANSI_GREY = "\u001B[99m";

    final String name;
    final String colour;
    int histo;

    Square(String name) {
        this(name, ANSI_BLACK);
    }

    Square(String name, String colour) {
        this.name = name;
        this.colour = colour;
        this.histo = 0;
    }

    String colouredBar(int scale) {
        return colour + bar(histo / scale) + ANSI_RESET;
    }

    String bar(int count) {
        return Collections.nCopies(count, "█").stream().collect(Collectors.joining());
    }
}
