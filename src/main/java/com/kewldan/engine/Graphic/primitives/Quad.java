package com.kewldan.engine.Graphic.primitives;

import com.kewldan.engine.Graphic.Shape;
import com.kewldan.engine.Graphic.VAO;
import com.kewldan.engine.Misc.GarbageCollector;
import com.kewldan.engine.Misc.ModelTransform;
import com.kewldan.engine.Window;

import static org.lwjgl.opengl.GL11.GL_QUADS;

public class Quad extends Shape {
    public final static float[] vertices = new float[]{
            0, 0,
            0, 1,
            1, 1,
            1, 0
    };
    final static int VERTEX_SIZE = 2;
    final static int VERTICES = 4;

    public Quad(int x, int y, int w, int h) {
        view = new ModelTransform(x, y, w, h);
        object = new VAO(VERTICES)
                .setMethod(GL_QUADS)
                .addVBO(vertices, 0, VERTEX_SIZE); //Vertex pos
        GarbageCollector.add(object);
    }

    public void center(){
        view.setPosition((Window.getInstance().width - view.size.x) / 2, (Window.getInstance().height - view.size.y) / 2);
    }
}
