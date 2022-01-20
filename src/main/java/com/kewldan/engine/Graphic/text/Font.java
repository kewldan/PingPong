package com.kewldan.engine.Graphic.text;

import com.kewldan.engine.Graphic.primitives.Texture;
import com.kewldan.engine.Misc.Files;
import com.kewldan.engine.Shader;
import com.kewldan.engine.Window;
import com.kewldan.engine.storage.DefaultShaders;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

/**
 * Signed Distance Field font
 */
public class Font {
    Shader shader;
    public String name;
    public int size;
    float mul = 1, lineHeight, smooth = 1f / 16f;
    Texture texture;
    HashMap<Integer, Character> characters;
    boolean using;
    Vector4f color;

    public Font(String path) {
        String file = Files.getString(path);
        shader = DefaultShaders.font;
        String[] lines = file.split("\n");
        characters = new HashMap<>();
        color = new Vector4f(1, 1, 1, 1);

        for (String line : lines) {
            if (line.startsWith("info")) {
                name = getArgument("face", line);
                size = Integer.parseInt(getArgument("size", line));
            } else if (line.startsWith("page")) {
                texture = new Texture("Fonts/" + getArgument("file", line).trim());
                texture.setLinearFilter();
            } else if (line.startsWith("chars")) {

            } else if (line.startsWith("common")) {
                lineHeight = Integer.parseInt(getArgument("lineHeight", line));
            } else if (line.startsWith("char")) {
                float x = Integer.parseInt(getArgument("x", line)) / (float) texture.getWidth();
                float y = Integer.parseInt(getArgument("y", line)) / (float) texture.getHeight();
                float w = Integer.parseInt(getArgument("width", line));
                float h = Integer.parseInt(getArgument("height", line));
                float offsetY = Integer.parseInt(getArgument("yoffset", line));
                float offsetX = Integer.parseInt(getArgument("xoffset", line));
                float xaddvance = Integer.parseInt(getArgument("xadvance", line));
                int id = Integer.parseInt(getArgument("id", line));
                Character nw = new Character(texture, w, h, offsetY, offsetX, xaddvance);
                w /= texture.getWidth();
                h /= texture.getHeight();
                nw.create(new float[]{ //Tex coords
                        x, y,
                        x, y + h,
                        x + w, y + h,
                        x + w, y
                });
                characters.put(id, nw);
            }
        }
    }

    /**
     * Begin drawing
     */
    public void begin() {
        if (!using) {
            shader.use();
            shader.uploadVec2f("r", Window.getInstance().width, Window.getInstance().height);
            shader.uploadVec4f("u_color", color);
            shader.uploadTexture("uTexture", 0);
            shader.uploadFloat("smoothing", smooth);
            glActiveTexture(GL_TEXTURE0);
            texture.bind();
            using = true;
        }
    }

    /**
     * Set smoothing
     *
     * @param smooth <1 Default is 1f / 16f
     */
    public void setSmooth(float smooth) {
        this.smooth = smooth;
        if (using) {
            shader.uploadFloat("smoothing", smooth);
        }
    }

    /**
     * Set font color
     *
     * @param r Red 0 - 255
     * @param g Green 0 - 255
     * @param b Blue 0 - 255
     * @param a Alpha 0 - 255
     */
    public final void setColor(int r, int g, int b, int a) {
        color.set(r / 255f, g / 255f, b / 255f, a / 255f);
        if (using) {
            shader.uploadVec4f("u_color", color);
        }
    }

    /**
     * Set font scale
     *
     * @param size Scale (Default 1)
     */
    public final void setScale(float size) {
        this.mul = size;
    }

    /**
     * End drawing
     */
    public final void end() {
        if (using) {
            shader.detach();
            texture.unbind();
            using = false;
        }
    }

    /**
     * Draw String
     *
     * @param text Text to draw
     * @param x    X
     * @param y    Y
     */
    public final void draw(CharSequence text, int x, int y) {
        int offsetX = 0;
        int offsetY = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n') {
                offsetY += lineHeight;
                offsetX = 0;
            } else {
                Character character = characters.get((int) c);
                if (character != null) {
                    shader.uploadVec2f("s", character.w * mul, character.h * mul);
                    shader.uploadVec2f("p", x + offsetX + character.ox * mul, y + offsetY + character.oy * mul);
                    if (c == ' ') {
                        offsetX += size / 2f * mul;
                    } else {
                        offsetX += character.xadvance * mul;
                    }
                    character.vao.draw();
                }
            }
        }
    }

    public final void drawCentered(String text, int x, int y) {
        draw(text, x - getLength(text) / 2, y);
    }

    public final int getLength(String text) {
        char[] chars = text.toCharArray();
        int len = 0;
        for (char c : chars) {
            Character character = characters.get((int) c);
            if (character != null) {
                if (c == ' ') {
                    len += character.w - size / 2f;
                } else {
                    len += character.w - character.xadvance;
                }
                len += character.w;
            }
        }
        return (int) (len * mul);
    }

    /**
     * Get argument from line
     *
     * @param name Argument name
     * @param line Line
     * @return Argument value or null
     */
    private static String getArgument(String name, final String line) {
        String[] args = line.split(" ");
        for (String a : args) {
            String[] g = a.split("=");
            if (g.length == 2) {
                if (g[0].equals(name)) {
                    return g[1].replace("\"", "");
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Font{" +
                "shader=" + shader +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", mul=" + mul +
                ", texture=" + texture +
                ", using=" + using +
                ", color=" + color +
                '}';
    }
}
