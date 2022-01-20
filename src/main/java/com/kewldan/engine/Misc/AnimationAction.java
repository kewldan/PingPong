package com.kewldan.engine.Misc;

public interface AnimationAction {
    /**
     * Invoke while animation
     * @param progress Animation cycle progress 0 - 1
     */
    void play(float progress);
}
