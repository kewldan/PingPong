package com.kewldan.engine.Graphic;

import com.kewldan.engine.Shader;
import com.kewldan.engine.Window;
import com.kewldan.engine.storage.DefaultShaders;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * Use for shape drawing
 */
public class ShapeBatch {
    Shader shader;
    Vector3f color = new Vector3f(1, 1, 1);
    float alpha = 1;
    boolean using;

    public ShapeBatch() {
        shader = DefaultShaders.standart;
    }

    /**
     * Draw shapes with your shader
     *
     * @param shader Your shader
     */
    public ShapeBatch(Shader shader) {
        this.shader = shader;
    }

    /**
     * Use shader and upload color
     */
    public void begin() {
        if (!using) {
            shader.use();
            shader.uploadVec3f("color", color);
            shader.uploadFloat("alpha", alpha);
            shader.uploadVec2f("r", new Vector2f(Window.getInstance().width, Window.getInstance().height)); //Resolution
            shader.uploadFloat("h", Window.getInstance().height);
            using = true;
        }
    }

    /**
     * Set draw color
     *
     * @param r Red 0 - 255
     * @param g Green 0 - 255
     * @param b Blue 0 - 255
     */
    public void setColor(int r, int g, int b) {
        this.color.set(r / 255f, g / 255f, b / 255f);
        if (using) {
            shader.uploadVec3f("color", color);
        }
    }

    /**
     * Set alpha (If blend enabled)
     *
     * @param alpha 0 - 1
     */
    public void setAlpha(float alpha) {
        this.alpha = alpha;
        if (using) {
            shader.uploadFloat("alpha", alpha);
        }
    }

    /**
     * Draw shapes
     *
     * @param objects objects
     */
    public void draw(Shape... objects) {
        for (Shape obj : objects) {
            shader.uploadVec2f("s", obj.view.size);
            shader.uploadVec2f("p", obj.view.pos);
            shader.uploadInt("f", obj.view.freeSpace ? 1 : 0);
            shader.uploadFloat("radius", obj.view.rounded);
            obj.draw();
        }
    }

    /**
     * Detach shader and draw
     */
    public void end() {
        if (using) {
            shader.detach();
            using = false;
        }
    }
}
