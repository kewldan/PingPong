package com.kewldan.engine.Misc;

import java.util.ArrayList;
import java.util.List;

/**
 * Collect all disposable object, and later dispose all object
 */
public class GarbageCollector {
    public static List<Disposable> objects;

    static {
        objects = new ArrayList<>();
    }

    /**
     * Add disposable object
     *
     * @param obj object
     */
    public static void add(Disposable obj) {
        objects.add(obj);
    }

    /**
     * Dispose ALL OBJECTS
     */
    public static void clean() {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).dispose();
        }
        objects.removeAll(objects);
    }
}
