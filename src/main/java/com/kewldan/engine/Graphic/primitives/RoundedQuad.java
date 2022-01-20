package com.kewldan.engine.Graphic.primitives;

public class RoundedQuad extends Quad {
    public RoundedQuad(int x, int y, int w, int h, int r) {
        super(x, y, w, h);
        view.setRounded(r);
    }
}
