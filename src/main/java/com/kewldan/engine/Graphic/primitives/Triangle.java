package com.kewldan.engine.Graphic.primitives;

import com.kewldan.engine.Graphic.Shape;
import com.kewldan.engine.Graphic.VAO;
import com.kewldan.engine.Misc.GarbageCollector;
import com.kewldan.engine.Misc.ModelTransform;

public class Triangle extends Shape {
    final static int VERTEX_SIZE = 2;
    final static int VERTICES = 3;

    public Triangle(int x0, int y0, float x1, float y1, float x2, float y2) {
        view = new ModelTransform(x0, y0, 0, 0);
        view.setFreeSpace(true);
        object = new VAO(VERTICES)
                .addVBO(new float[]{
                        x0, y0,
                        x1, y1,
                        x2, y2
                }, 0, VERTEX_SIZE);
        GarbageCollector.add(object);
    }
}
