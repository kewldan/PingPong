package com.kewldan.engine.Input;

public class ClickHitbox extends Hitbox {
    IHasAction a;

    public ClickHitbox(int x, int y, int w, int h, IHasAction a) {
        super(x, y, w, h);
        this.a = a;
    }

    public void invoke(int x, int y){
        a.callback(x, y);
    }
}
