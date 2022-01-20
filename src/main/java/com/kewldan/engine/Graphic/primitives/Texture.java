package com.kewldan.engine.Graphic.primitives;

import com.kewldan.engine.Misc.Disposable;
import com.kewldan.engine.Misc.Files;
import com.kewldan.engine.Misc.GarbageCollector;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL40.*;

public class Texture implements Disposable {
    String path;
    int id;
    int w, h;
    ByteBuffer data;
    BufferedImage image;

    public Texture(String path) {
        this.path = path;
        GarbageCollector.add(this);

        image = Files.getImage(path);
        data = Files.getImageBytes(image);
        w = image.getWidth();
        h = image.getHeight();
        load();
    }

    public void load(){
        id = glGenTextures();

        bind();

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        if (!image.getColorModel().hasAlpha()) {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, w, h,
                    0, GL_RGB, GL_UNSIGNED_BYTE, data);
        } else {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, w, h,
                    0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        }
    }

    public void setLinearFilter() {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    }

    public void setNearestFilter() {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    @Override
    public void dispose() {
        glDeleteTextures(id);
    }

    @Override
    public String toString() {
        return "Texture{" +
                "path='" + path + '\'' +
                ", id=" + id +
                ", w=" + w +
                ", h=" + h +
                '}';
    }
}
