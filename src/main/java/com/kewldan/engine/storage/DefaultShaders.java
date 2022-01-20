package com.kewldan.engine.storage;

import com.kewldan.engine.Shader;

public class DefaultShaders {
    public static Shader texture, standart, font;

    static {
        texture = new Shader("texture");
        texture.compile();
        standart = new Shader("default");
        standart.compile();
        font = new Shader("font");
        font.compile();
    }
}
