package com.kewldan.mc;

import com.kewldan.engine.ApplicationManager;

import java.util.Arrays;
import java.util.List;

public class Main {
    static Game game;
    public static List<String> args;

    public static void main(String[] args) {
        Main.args = Arrays.asList(args);
        ApplicationManager.start(new Game());
        System.exit(0);
    }
}
