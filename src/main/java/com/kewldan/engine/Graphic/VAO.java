package com.kewldan.engine.Graphic;

import com.kewldan.engine.Misc.Disposable;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class VAO implements Disposable {
    int id, vertices, method = GL_TRIANGLES;
    List<VBO> vbos;

    public VAO(int vertices) {
        this.vertices = vertices;
        id = glGenVertexArrays();
        vbos = new ArrayList<>();
    }

    /**
     * Bind VAO
     */
    public void bind() {
        glBindVertexArray(id);
    }

    /**
     * Bind VAO with VBO
     *
     * @param data      VBO data
     * @param attribute Attribute location in shader
     * @param size      Data element size
     * @return this
     */
    public VAO addVBO(float[] data, int attribute, int size) {
        VBO nw = new VBO(data);
        bind();
        nw.bind();

        glVertexAttribPointer(attribute, size, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(attribute);

        VBO.unbind();
        unbind();
        vbos.add(nw);
        return this;
    }

    /**
     * Draw VAO
     */
    public void draw() {
        bind();
        glDrawArrays(method, 0, vertices);
        unbind();
    }

    /**
     * Get draw method
     *
     * @return Draw method
     */
    public int getMethod() {
        return method;
    }

    /**
     * Set draw method (Default is GL_TRIANGLES)
     *
     * @param method new method
     * @return this
     */
    public VAO setMethod(int method) {
        this.method = method;
        return this;
    }

    @Override
    public String toString() {
        return "VAO{" +
                "id=" + id +
                ", vertices=" + vertices +
                ", method=" + method +
                ", vbos=" + vbos +
                '}';
    }

    /**
     * Delete VAO and VBOs
     */
    @Override
    public void dispose() {
        glDeleteVertexArrays(id);
        for (VBO vbo : vbos) {
            vbo.dispose();
        }
    }

    /**
     * Unbind VAO
     */
    public static void unbind() {
        glBindVertexArray(0);
    }
}
