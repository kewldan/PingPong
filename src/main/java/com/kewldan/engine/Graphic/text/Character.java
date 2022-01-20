package com.kewldan.engine.Graphic.text;

import com.kewldan.engine.Graphic.VAO;
import com.kewldan.engine.Graphic.primitives.Quad;
import com.kewldan.engine.Graphic.primitives.Texture;
import com.kewldan.engine.Misc.GarbageCollector;
import org.lwjgl.opengl.GL11;

public class Character {
    Texture texture;
    VAO vao;
    public float w, h, oy, ox, xadvance;

    public Character(Texture texture, float w, float h, float oy, float ox, float xaddvance) {
        this.texture = texture;
        this.w = w;
        this.h = h;
        this.oy = oy;
        this.ox = ox;
        this.xadvance = xaddvance;
    }

    public void create(float[] tex) {
        vao = new VAO(4)
                .addVBO(Quad.vertices, 0, 2)
                .addVBO(tex, 1, 2)
                .setMethod(GL11.GL_QUADS);
        GarbageCollector.add(vao);
    }
}
