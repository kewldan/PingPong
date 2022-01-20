package com.kewldan.engine.Graphic;

import com.kewldan.engine.Graphic.primitives.Texture;
import com.kewldan.engine.Misc.ThreadManager;
import com.kewldan.engine.Window;

public class SplashScreen {
    Texture texture;
    float length, black;

    public SplashScreen(String path, float length, float black) {
        this.texture = new Texture(path);
        this.length = length;
        this.black = black;
    }

    public void draw(ImageBatch batch){
        if(ThreadManager.getTime() < length) {
            batch.begin();
            batch.draw(texture, 0, 0, Window.getInstance().width, Window.getInstance().height, ThreadManager.getTime() < length - black ? 1 : 1 - (ThreadManager.getTime() - (length - black)) / black);
            batch.end();
        }
    }
}
