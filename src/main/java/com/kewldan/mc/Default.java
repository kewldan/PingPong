package com.kewldan.mc;

import com.kewldan.engine.Graphic.primitives.Texture;
import com.kewldan.engine.Graphic.text.Font;

public class Default {
    public static Font font;
    public static Texture close;

    public static void load(){
        font = new Font("Fonts/font.fnt");
        close = new Texture("close.png");
    }
}
