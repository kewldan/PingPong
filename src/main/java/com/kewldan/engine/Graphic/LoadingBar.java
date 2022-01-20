package com.kewldan.engine.Graphic;

import com.kewldan.engine.Graphic.primitives.Quad;
import com.kewldan.engine.Graphic.text.Font;
import com.kewldan.engine.Window;

public class LoadingBar {
    int x, y, w, h;
    float progress;
    Quad back, bar;

    public LoadingBar(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        back = new Quad(x, y, w, h);
        bar = new Quad(x + 2, y + 2, w - 4, h - 4);
    }

    public void draw(ShapeBatch shapes) {
        shapes.setColor(255, 255, 255);
        shapes.draw(back);
        shapes.setColor(50, 50, 50);
        shapes.draw(bar);
    }

    public void draw(Font font){
        font.drawCentered(Math.round(progress * 100f) + "%", x + w / 2, y - h - 30);
    }

    public void setProgress(float prog) {
        this.progress = Math.min(prog, 1);
        bar.view.pos.x = x + 2f + progress * (w - 4f);
        bar.view.size.x = w - 4f - progress * (w - 4f);
    }
}
