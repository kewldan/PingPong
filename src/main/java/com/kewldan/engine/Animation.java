package com.kewldan.engine;

import com.kewldan.engine.Misc.AnimationAction;

public class Animation {
    float progress; //Length in seconds
    boolean repeat;
    long b, l;
    AnimationAction action;

    public Animation(long l, AnimationAction action) {
        this.action = action;
        b = System.currentTimeMillis();
        this.l = l;
    }

    public Animation setLength(long ln) {
        l = ln;
        return this;
    }

    public Animation setRepeat(boolean repeat) {
        this.repeat = repeat;
        return this;
    }

    public void play() {
        progress = -((b - System.currentTimeMillis()) / (float) l);
        if (progress > 1) {
            if (repeat) {
                b = System.currentTimeMillis();
            } else {
                return;
            }
        }
        action.play(progress);
    }
}
