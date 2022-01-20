package com.kewldan.engine.Graphic;

import com.kewldan.engine.Misc.ModelTransform;

public abstract class Shape {
    public ModelTransform view;
    public VAO object;

    @Override
    public String toString() {
        return "Shape{" +
                "view=" + view +
                ", object=" + object +
                '}';
    }

    public void draw(){
        object.draw();
    }
}
