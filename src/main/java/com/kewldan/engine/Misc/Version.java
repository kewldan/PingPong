package com.kewldan.engine.Misc;

public class Version {
    String glVersion, vendor, driver, glfw;
    public Version(String gl, String glfw){
        String[] gls = gl.split(" ");
        glVersion = gls[0];
        vendor = gls[1];
        driver = gls[2];
        this.glfw = glfw;
    }

    @Override
    public String toString() {
        return "Version{" +
                "glVersion='" + glVersion + '\'' +
                ", vendor='" + vendor + '\'' +
                ", driver='" + driver + '\'' +
                ", glfw='" + glfw + '\'' +
                '}';
    }
}
