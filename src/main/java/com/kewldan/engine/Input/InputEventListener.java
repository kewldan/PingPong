package com.kewldan.engine.Input;

public interface InputEventListener {
    /**
     * When key pressed on first time
     * @param code Key code
     */
    void keyPressedFirst(int code);
    /**
     * When key pressed
     * @param code Key code
     */
    void keyPressed(int code);
    /**
     * When key released
     * @param code Key code
     */
    void keyReleased(int code);

    /**
     * When mouse move
     * @param x X
     * @param y Y
     */
    void mouseMove(int x, int y);

    /**
     * When mouse button pressed
     * @param button Button code (0 - 3 (+Additional buttons))
     */
    void mousePressed(int button);

    /**
     * When mouse button released
     * @param button Button code (0 - 3 (+Additional buttons))
     */
    void mouseReleased(int button);
}
