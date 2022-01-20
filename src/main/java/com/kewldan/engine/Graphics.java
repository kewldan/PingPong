package com.kewldan.engine;

import org.lwjgl.opengl.GL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

public class Graphics implements ActionListener {
    long id;
    int fps, r;
    Timer timer;

    public Graphics(long id) {
        timer = new Timer(500, this);
        timer.start();

        this.id = id;
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render() {
        glfwSwapBuffers(id); // swap the color buffers
        glfwPollEvents();
        r++;
    }

    public void enableTexturing() {
        glEnable(GL_TEXTURE_2D);
    }

    public void disableTexturing() {
        glDisable(GL_TEXTURE_2D);
    }

    public void setClearColor(float r, float g, float b) {
        glClearColor(r / 255f, g / 255f, b / 255f, 1);
    }

    public void enableBlending() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void disableBlending() {
        glDisable(GL_BLEND);
    }

    public void create() {
        GL.createCapabilities();
        enableBlending();
        enableTexturing();
        glOrtho(0f, Window.getInstance().width, Window.getInstance().height, 0f, -1f, 1f);
    }

    public int getFps() {
        return fps;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fps = r * 2;
        r = 0;
    }
}
