package com.kewldan.engine.Input;

import com.kewldan.engine.Graphic.primitives.Quad;

public class Hitbox {
    float x, y, w, h;
    Quad parent;

    public Hitbox(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Hitbox(Quad quad) {
        this.parent = quad;
    }

    void check() {
        if (parent != null) {
            x = parent.view.pos.x;
            y = parent.view.pos.y;
            w = parent.view.size.x;
            h = parent.view.size.y;
        }
    }

    public boolean point(int x0, int y0) {
        check();
        return x0 > x && x0 < x + w && y0 > y && y0 < y + h;
    }

    public boolean circle(float cx, float cy, float r) {
        check();
        float testX = Math.abs(cx - x);
        float testY = Math.abs(cy - y);
        if (testX > (w / 2 + r) || testY > (h / 2 + r))
            return false;

        if (testX <= (w / 2) || testY <= (h / 2))
            return true;


        return ((testX - w / 2) * (testX - w / 2) + (testY - h / 2) * (testY - h / 2) <= (r * r));
    }

    @Override
    public String toString() {
        return "Hitbox{" +
                "x=" + x +
                ", y=" + y +
                ", w=" + w +
                ", h=" + h +
                ", parent=" + parent +
                '}';
    }
}
