package com.kewldan.engine.Graphic;

import com.kewldan.engine.Graphic.primitives.Line;

import java.util.ArrayList;

public class DebugDrawer {
    static int id = 0;
    static ArrayList<Line> lines;

    static {
        lines = new ArrayList<>();
    }

    public static void registerVert(Shape[] verts){
        for(int i = 0; i < verts.length; i+=2){

        }
        id++;
    }


}
