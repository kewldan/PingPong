package com.kewldan.engine;

import com.kewldan.engine.Graphic.LoadingBar;
import com.kewldan.engine.Misc.GarbageCollector;
import com.kewldan.engine.Misc.ThreadManager;

public class ApplicationManager {

    public static void start(Application app, boolean splash){
        ThreadManager.init();
        app.create();
        app.graphics = app.window.getGraphics();
        app.graphics.create();
        app.clipboard = app.window.getClipboard();
        app.bar = new LoadingBar(app.window.width / 2 - 150, app.window.height / 2 - 200, 300, 30);
        app.load();

        appWait(app);
    }

    static void appWait(Application app){
        while (app.window.isOpen()){
            app.render();
            app.window.g.render();
        }
        app.window.dispose();
        GarbageCollector.clean();
        app.dispose();
    }

    public static void start(Application app){
        start(app, false);
    }
}
