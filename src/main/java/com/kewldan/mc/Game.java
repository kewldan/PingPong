package com.kewldan.mc;

import com.kewldan.engine.*;
import com.kewldan.engine.Graphic.*;
import com.kewldan.engine.Graphic.SplashScreen;
import com.kewldan.engine.Graphic.primitives.Texture;
import com.kewldan.engine.Graphic.text.Font;
import com.kewldan.engine.Misc.Scene;
import com.kewldan.engine.Window;
import com.kewldan.engine.api.Script;

public class Game extends Application {
    InputManager inputManager;
    Scene playing;

    @Override
    public void create() {
        window = Window.getInstance(1280, 720);
        window.setTitle("Ping pong");
        inputManager = new InputManager();
        window.addInputListener(inputManager);
        playing = new Playing();
    }

    @Override
    public void load() {
        playing.load();
        playing.enable();
        Default.load();
    }

    @Override
    public void render() {
        playing.draw();
        window.setDebugTitle();
    }

    @Override
    public void dispose() {
        playing.disable();
    }
}
