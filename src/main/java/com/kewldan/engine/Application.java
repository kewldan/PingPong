package com.kewldan.engine;

import com.kewldan.engine.Graphic.LoadingBar;
import com.kewldan.engine.Graphic.SplashScreen;
import com.kewldan.engine.Input.Clipboard;
import com.kewldan.engine.Misc.Disposable;

public abstract class Application implements Disposable {
    public Window window;
    public Graphics graphics;
    public Clipboard clipboard;
    public LoadingBar bar;
    public SplashScreen screen;

    public void create() {

    }

    public void load(){

    }

    public void render(){

    }
}
