package com.kewldan.mc;

import com.kewldan.engine.Input.ClickHitbox;
import com.kewldan.engine.Input.Hitbox;
import com.kewldan.engine.Input.InputEventListener;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.List;

public class InputManager implements InputEventListener {
    public static int mouseX, mouseY;
    public static List<ClickHitbox> hits;

    public InputManager() {
        hits = new ArrayList<>();
    }

    @Override
    public void keyPressedFirst(int code) {

    }

    @Override
    public void keyPressed(int code) {

    }

    @Override
    public void keyReleased(int code) {

    }

    @Override
    public void mouseMove(int x, int y) {
        mouseX = x;
        mouseY = y;
    }

    @Override
    public void mousePressed(int button) {
        for (ClickHitbox h : hits) {
            if (h.point(mouseX, mouseY))
                h.invoke(mouseX, mouseY);
        }
    }

    @Override
    public void mouseReleased(int button) {

    }
}
