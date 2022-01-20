package com.kewldan.engine;

import com.kewldan.engine.Misc.Files;
import org.joml.*;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private int shaderProgramID;
    private boolean beingUsed = false;
    int vertexID, fragmentID;

    private String vertexSource;
    private String fragmentSource;
    private String filepath;
    HashMap<String, Integer> uniforms, attributes;

    /**
     * @param filepath Path to shader in resources
     */
    public Shader(String filepath) {
        this.filepath = filepath;
        uniforms = new HashMap<>();
        attributes = new HashMap<>();
        vertexSource = Files.getString("Shaders/" + filepath + ".vert");
        fragmentSource = Files.getString("Shaders/" + filepath + ".frag");
    }

    /**
     * Compile shader (Always compile shader before using)
     */
    public void compile() {
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexID, vertexSource);
        glCompileShader(vertexID);

        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + filepath + "'\n\tVertex shader compilation failed.");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }

        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentID, fragmentSource);
        glCompileShader(fragmentID);

        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + filepath + "'\n\tFragment shader compilation failed.");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }

        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexID);
        glAttachShader(shaderProgramID, fragmentID);
        glLinkProgram(shaderProgramID);

        success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int len = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + filepath + "'\n\tLinking of shaders failed.");
            System.out.println(glGetProgramInfoLog(shaderProgramID, len));
            assert false : "";
        }
    }

    /**
     * Bind shader to OpenGL
     */
    public void use() {
        if (!beingUsed) {
            glUseProgram(shaderProgramID);
            beingUsed = true;
        }
    }

    /**
     * Detach your shader from OpenGL
     */
    public void detach() {
        glUseProgram(0);
        beingUsed = false;
    }

    /**
     * @return ShaderID in OpenGL
     */
    public int getShaderProgramID() {
        return shaderProgramID;
    }

    /**
     * @return Shader log
     */
    public String getLog() {
        return glGetProgramInfoLog(shaderProgramID);
    }

    public void uploadMat4f(String varName, Matrix4f mat4) {
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matBuffer);
        glUniformMatrix4fv(getUniform(varName), false, matBuffer);
    }

    public void uploadMat3f(String varName, Matrix3f mat3) {
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(9);
        mat3.get(matBuffer);
        glUniformMatrix3fv(getUniform(varName), false, matBuffer);
    }

    public void uploadVec4f(String varName, Vector4f vec) {
        glUniform4f(getUniform(varName), vec.x, vec.y, vec.z, vec.w);
    }

    public void uploadVec3f(String varName, Vector3f vec) {
        glUniform3f(getUniform(varName), vec.x, vec.y, vec.z);
    }

    public void uploadVec2f(String varName, Vector2f vec) {
        glUniform2f(getUniform(varName), vec.x, vec.y);
    }

    public void uploadVec2f(String varName, float x, float y) {
        glUniform2f(getUniform(varName), x, y);
    }


    public void uploadFloat(String varName, float val) {
        glUniform1f(getUniform(varName), val);
    }

    public void uploadInt(String varName, int val) {
        glUniform1i(getUniform(varName), val);
    }

    public void uploadTexture(String varName, int slot) {
        glUniform1i(getUniform(varName), slot);
    }

    public void uploadIntArray(String varName, int[] array) {
        glUniform1iv(getUniform(varName), array);
    }

    public void uploadFloatArray(String varName, float[] array) {
        glUniform1fv(getUniform(varName), array);
    }

    int getUniform(String var) {
        int l;
        if (uniforms.containsKey(var)) {
            l = uniforms.get(var);
        } else {
            l = glGetUniformLocation(shaderProgramID, var);
            uniforms.put(var, l);
        }
        return l;
    }

    /**
     * @param var Attribute name
     * @return attribute location
     */
    public int getAttribute(String var) {
        int l;
        if (attributes.containsKey(var)) {
            l = attributes.get(var);
        } else {
            l = glGetAttribLocation(shaderProgramID, var);
            attributes.put(var, l);
        }
        return l;
    }

    @Override
    public String toString() {
        return "Shader{" +
                "shaderProgramID=" + shaderProgramID +
                ", beingUsed=" + beingUsed +
                ", vertexID=" + vertexID +
                ", fragmentID=" + fragmentID +
                ", filepath='" + filepath + '\'' +
                '}';
    }
}
