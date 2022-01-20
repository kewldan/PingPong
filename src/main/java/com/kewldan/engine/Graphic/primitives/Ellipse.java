package com.kewldan.engine.Graphic.primitives;

import com.kewldan.engine.Graphic.Shape;
import com.kewldan.engine.Graphic.VAO;
import com.kewldan.engine.Misc.GarbageCollector;
import com.kewldan.engine.Misc.ModelTransform;

import static org.lwjgl.opengl.GL15.GL_TRIANGLE_FAN;

public class Ellipse extends Shape {
    static float[] vertices;

    final static int VERTEX_SIZE = 2;
    final static int VERTICES = 128;

    static {
        vertices = new float[VERTEX_SIZE * (VERTICES + 2)];
        vertices[0] = 0;
        vertices[1] = 0;
        float a = 0;
        for (int i = 1; i <= VERTICES; i++) {
            vertices[i * VERTEX_SIZE] = (float) Math.cos(a);
            vertices[i * VERTEX_SIZE + 1] = (float) Math.sin(a);
            a += Math.PI / VERTICES * 2f;
        }
        vertices[vertices.length - 2] = 1;
        vertices[vertices.length - 1] = 0;
    }

    public Ellipse(int x, int y, int w, int h) {
        view = new ModelTransform(x, y, w, h);
        object = new VAO(VERTICES + 2)
                .addVBO(vertices, 0, VERTEX_SIZE)
                .setMethod(GL_TRIANGLE_FAN);
        GarbageCollector.add(object);
    }

    @Override
    public String toString() {
        return "Ellipse{" +
                "view=" + view +
                ", object=" + object +
                '}';
    }
}
