package com.kewldan.engine.Graphic;

import com.kewldan.engine.Graphic.primitives.Quad;
import com.kewldan.engine.Graphic.primitives.Texture;
import com.kewldan.engine.Shader;
import com.kewldan.engine.Window;
import com.kewldan.engine.storage.DefaultShaders;
import org.joml.Vector2f;

import static org.lwjgl.opengl.GL30.*;

/**
 * Using for drawing textures
 */
public class ImageBatch {
    VAO vao; //One VAO for objects
    Shader shader;

    public ImageBatch() {
        shader = DefaultShaders.texture;
        create();
    }

    public ImageBatch(Shader shader) {
        this.shader = shader;
        create();
    }

    /**
     * Create VAO
     */
    public void create() {
        vao = new VAO(4)
                .setMethod(GL_QUADS)
                .addVBO(Quad.vertices, 0, 2);
    }

    /**
     * Use shader and upload textures
     */
    public void begin() {
        shader.use();
        shader.uploadTexture("uTexture", 0);
        shader.uploadVec2f("r", Window.getInstance().width, Window.getInstance().height);
    }

    /**
     * Custom draw with vectors
     *
     * @param i    Texture
     * @param pos  Position
     * @param size Size
     */
    public void draw(Texture i, Vector2f pos, Vector2f size) {
        glActiveTexture(GL_TEXTURE0);
        i.bind();
        shader.uploadVec2f("s", size);
        shader.uploadVec2f("p", pos);
        vao.draw();
    }

    /**
     * Custom draw
     *
     * @param i Texture
     * @param x X
     * @param y Y
     * @param w Width
     * @param h Height
     */
    public void draw(Texture i, float x, float y, float w, float h, float a) {
        glActiveTexture(GL_TEXTURE0);
        i.bind();
        shader.uploadVec2f("s", w, h);
        shader.uploadVec2f("p", x, y);
        shader.uploadFloat("a", a);
        vao.draw();
    }

    /**
     * Detach shader and draw
     */
    public void end() {
        shader.detach();
    }

    /**
     * Draw texture with her Width and Height
     *
     * @param i texture
     * @param x X
     * @param y Y
     */
    public void draw(Texture i, int x, int y) {
        draw(i, x, y, i.getWidth(), i.getHeight(), 1);
    }


    public void draw(Texture i, int x, int y, float scale) {
        draw(i, x, y, i.getWidth() * scale, i.getHeight() * scale, 1);
    }

    @Override
    public String toString() {
        return "ImageBatch{" +
                "vao=" + vao +
                ", shader=" + shader +
                '}';
    }
}
