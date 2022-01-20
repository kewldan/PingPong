package com.kewldan.engine.Graphic.primitives;

import com.kewldan.engine.Graphic.Shape;
import com.kewldan.engine.Graphic.VAO;
import com.kewldan.engine.Misc.GarbageCollector;
import com.kewldan.engine.Misc.ModelTransform;

import static org.lwjgl.opengl.GL15.GL_LINES;

public class Line extends Shape {

    final static int VERTEX_SIZE = 2;

    public Line(int x0, int y0, int x1, int y1) {
        view = new ModelTransform(0, 0, 0, 0);
        view.setFreeSpace(true);
        object = new VAO(2)
                .addVBO(new float[]{
                        x0, y0,
                        x1, y1
                }, 0, VERTEX_SIZE)
                .setMethod(GL_LINES);
        GarbageCollector.add(object);
    }
}
