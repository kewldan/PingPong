package com.kewldan.engine.Misc;

import org.joml.Vector2f;

public class ModelTransform {
    public Vector2f pos, size;
    public boolean freeSpace = false;
    public float rounded;

    public void setPosition(float x, float y) {
        pos.set(x, y);
    }

    public void setSize(int w, int h) {
        size.set(w, h);
    }

    public ModelTransform setFreeSpace(boolean freeSpace) {
        this.freeSpace = freeSpace;
        return this;
    }

    public ModelTransform setRounded(float rounded){
        this.rounded = rounded;
        return this;
    }

    public ModelTransform(int x, int y, int w, int h) {
        pos = new Vector2f(x, y);
        size = new Vector2f(w, h);
    }

    @Override
    public String toString() {
        return "ModelTransform{" +
                "pos=" + pos +
                ", size=" + size +
                ", freeSpace=" + freeSpace +
                '}';
    }
}
