package com.kewldan.engine.api;

import com.kewldan.engine.Misc.Files;

import javax.script.*;

public class Script {
    Bindings bindings;
    String content;
    ScriptEngine engine;
    Invocable invocable;

    public static ScriptEngineManager manager;

    public static ScriptEngine getEngine(){
        if(manager == null)
            manager = new ScriptEngineManager();
        return manager.getEngineByName("nashorn");
    }

    public Script() {
        engine = getEngine();
        bindings = engine.createBindings();
    }

    public void loadScript(String path){
        this.content = Files.getString(path);
        try {
            engine.eval(Files.getFileReader(path));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        invocable = (Invocable) engine;
    }

    public void load(String name, Object js){
        bindings.put("R." + name, js);
    }

    public void start(Object ... objs){
        try {
            invocable.invokeFunction("setup", objs);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void loop(Object ... objs){
        try {
            invocable.invokeFunction("loop", objs);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
