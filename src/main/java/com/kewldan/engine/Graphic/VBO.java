package com.kewldan.engine.Graphic;

import com.kewldan.engine.Misc.Disposable;

import static org.lwjgl.opengl.GL15.*;

public class VBO implements Disposable {
    int id;

    public VBO(float[] data) {
        id = glGenBuffers();
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        unbind();
    }

    /**
     * Bind VBO
     */
    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id);
    }

    /**
     * Buffer ID in OpenGL
     *
     * @return ID
     */
    public int getBufferId() {
        return id;
    }

    /**
     * Unbind VBO
     */
    public static void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    /**
     * Delete VBO in GPU
     */
    @Override
    public void dispose() {
        glDeleteBuffers(id);
    }

    @Override
    public String toString() {
        return "VBO{" +
                "id=" + id +
                '}';
    }
}
