package com.kewldan.engine;

import org.lwjgl.system.CallbackI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

public class Preferences {
    File file;
    HashMap<String, HashMap<String, String>> values; //Type - (Name - Value)

    public Preferences(String path) {
        file = new File(path);
        values = new HashMap<>();
    }

    public boolean exists() {
        return file.exists();
    }

    public void load() throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());

        for (String line : lines) {
            String[] args = line.split(" ", 3);
            if (args.length >= 3) {
                HashMap<String, String> typeValues = values.get(args[0]);
                if(typeValues == null) {
                    typeValues = new HashMap<>();
                    values.put(args[0], typeValues);
                }
                typeValues.put(args[1], String.join(" ", args[2]));
            }
        }
    }

    public void save() throws IOException {
        StringJoiner joiner = new StringJoiner("\n");
        values.forEach((type, typeValues) -> {
            typeValues.forEach((name, value) -> {
                joiner.add(type + " " + name + " " + value);
            });
        });
        BufferedWriter writer = new BufferedWriter(new FileWriter(file.getPath()));
        writer.write(joiner.toString());
        writer.close();
    }

    public Preferences addValue(String type, String name, String def) {
        HashMap<String, String> typeValues = values.get(type);
        if(typeValues == null){
            typeValues = new HashMap<>();
            values.put(type, typeValues);
        }
        typeValues.put(name, def);
        return this;
    }

    public Preferences addBoolean(String name, boolean value){
        addValue("bool", name, String.valueOf(value));
        return this;
    }

    public Preferences addInt(String name, int value){
        addValue("int", name, String.valueOf(value));
        return this;
    }

    public Preferences addString(String name, String value){
        addValue("string", name, String.valueOf(value));
        return this;
    }

    public int getInt(String name, int def){
        HashMap<String, String> typeValues = values.get("int");
        if(typeValues != null){
            if(typeValues.containsKey(name))
                return Integer.parseInt(typeValues.get(name));
            return def;
        }
        return def;
    }

    public int getInt(String name){
        return getInt(name, -1);
    }

    public String getString(String name, String def){
        HashMap<String, String> typeValues = values.get("string");
        if(typeValues != null){
            if(typeValues.containsKey(name))
                return typeValues.get(name);
            return def;
        }
        return def;
    }

    public String getString(String name){
        return getString(name, "");
    }

    public boolean getBoolean(String name, boolean def){
        HashMap<String, String> typeValues = values.get("bool");
        if(typeValues != null){
            if(typeValues.containsKey(name))
                return Boolean.parseBoolean(typeValues.get(name));
            return def;
        }
        return def;
    }

    public boolean getBoolean(String name){
        return getBoolean(name, false);
    }

    @Override
    public String toString() {
        return "Preferences{" +
                "file=" + file +
                ", values=" + values +
                '}';
    }
}
