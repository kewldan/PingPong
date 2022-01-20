package com.kewldan.engine.Input;

import com.kewldan.engine.Graphic.ImageBatch;
import com.kewldan.engine.Graphic.ShapeBatch;
import com.kewldan.engine.Graphic.primitives.Texture;
import com.kewldan.engine.Graphic.text.Font;
import org.joml.Vector3f;

public class Button {
    public int x, y, w, h;
    public String text;
    IHasAction action;
    Texture texture;

    public Button(int x, int y, String text, Texture texture, IHasAction a) {
        this(x, y, text, texture.getWidth(), texture.getHeight(), texture, a);
    }

    public Button(int x, int y, String text, int w, int h, Texture texture, IHasAction a) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.text = text;
        this.action = a;
        this.texture = texture;
    }

    private boolean on(int x1, int y1) {
        return x1 > x && x1 < x + w && y1 > y && y1 < y + h;
    }

    public void collide(int x1, int y1) {
        if (on(x1, y1))
            action.callback(x1, y1);
    }

    public void drawBackground(ImageBatch batch) {
        batch.draw(texture, x, y, w, h, 1);
    }

    public void drawBackground(ShapeBatch batch, Vector3f color) {
        drawBackground(batch, (int) color.x * 255, (int) color.y * 255, (int) color.z * 255);
    }

    public void drawBackground(ShapeBatch batch, int r, int g, int b) {
        batch.setColor(r, g, b);
        batch.draw();
    }

    public void drawForeground(Font font) {
        int fontX = x + (w - font.getLength(text)) / 2;
        int fontY = y + (h + font.size) / 2;
        font.draw(text, fontX, fontY);
    }
}
