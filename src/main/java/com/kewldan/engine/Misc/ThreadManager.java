package com.kewldan.engine.Misc;

import java.util.HashMap;

public class ThreadManager {
    static long b;
    static HashMap<String, Thread> threads;

    public static void init(){
        threads = new HashMap<>();
        b = System.currentTimeMillis();
    }

    public static Thread addNStart(String name, Runnable runnable){
        if(threads.containsKey(name)){
            new IllegalArgumentException("Thread with name already started");
        }else{
            Thread thread = new Thread(runnable, name);
            thread.start();
            threads.put(name, thread);
            return thread;
        }
        return null;
    }

    public static long getTime(){
        return System.currentTimeMillis() - b;
    }
}
