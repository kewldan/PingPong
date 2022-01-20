package com.kewldan.engine.Input;

public interface WindowEventListener {
    /**
     * When window resize
     * @param w Width
     * @param h Height
     */
    void resize(int w, int h);

    /**
     * When window move
     * @param x X
     * @param y Y
     */
    void move(int x, int y);
}
