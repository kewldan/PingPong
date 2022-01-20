package com.kewldan.engine.Input;

import static org.lwjgl.glfw.GLFW.*;

public class Clipboard {
    long window;

    public Clipboard(long window) {
        this.window = window;
    }

    /**
     * Get string in clipboard
     * @return String in clipboard
     */
    public String getString(){
        return glfwGetClipboardString(window);
    }

    /**
     * Set string to clipboard
     * @param value string
     */
    public void setString(String value){
        glfwSetClipboardString(window, value);
    }
}
