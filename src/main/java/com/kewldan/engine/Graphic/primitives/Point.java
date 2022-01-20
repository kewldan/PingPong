package com.kewldan.engine.Graphic.primitives;

import com.kewldan.engine.Graphic.Shape;
import com.kewldan.engine.Graphic.VAO;
import com.kewldan.engine.Misc.GarbageCollector;
import com.kewldan.engine.Misc.ModelTransform;

import static org.lwjgl.opengl.GL11.*;

public class Point extends Shape {
    final static int VERTEX_SIZE = 2;

    public Point(int x, int y) {
        view = new ModelTransform(0, 0, 0, 0)
                .setFreeSpace(true);
        object = new VAO(2)
                .addVBO(new float[]{
                        x, y
                }, 0, VERTEX_SIZE)
                .setMethod(GL_POINTS);
        GarbageCollector.add(object);
    }
}
