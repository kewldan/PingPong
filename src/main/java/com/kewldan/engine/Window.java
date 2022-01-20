package com.kewldan.engine;

import com.kewldan.engine.Input.Clipboard;
import com.kewldan.engine.Input.InputEventListener;
import com.kewldan.engine.Input.WindowEventListener;
import com.kewldan.engine.Misc.Files;
import com.kewldan.engine.Misc.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL46;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Window {
    public static Window instance;

    public static Window getInstance(int w, int h) {
        if (instance == null) instance = new Window(w, h);
        return instance;
    }

    public static Window getInstance() {
        return instance;
    }

    public int width, height;
    long window;
    String title;
    Graphics g;
    Clipboard clipboard;
    boolean sync = true;
    List<InputEventListener> inputListeners;
    List<WindowEventListener> eventListeners;

    public Window(int wight, int height) {
        this.width = wight;
        this.height = height;

        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, 0);
        glfwWindowHint(GLFW_RESIZABLE, 1);

        window = glfwCreateWindow(wight, height, "Gametack application", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(sync ? 1 : 0);
        glfwShowWindow(window);
        g = new Graphics(window);
        clipboard = new Clipboard(window);
        eventListeners = new ArrayList<>();
        inputListeners = new ArrayList<>();
        glfwSetWindowSizeCallback(window, (window1, w, h) -> {
            updateWindowSize(w, h);
            for (WindowEventListener e : eventListeners) {
                e.resize(w, h);
            }
        });
        glfwSetWindowPosCallback(window, (window1, xpos, ypos) -> {
            for (WindowEventListener e : eventListeners) {
                e.move(xpos, ypos);
            }
        });


        glfwSetMouseButtonCallback(window, (window1, button, action, mods) -> {
            for (InputEventListener e : inputListeners) {
                if (action == 1) {
                    e.mousePressed(button);
                } else {
                    e.mouseReleased(button);
                }

            }
        });
        glfwSetCursorPosCallback(window, (window1, xpos, ypos) -> {
            for (InputEventListener e : inputListeners) {
                e.mouseMove((int) xpos, (int) ypos);
            }
        });
        glfwSetKeyCallback(window, (window1, key, scancode, action, mods) -> {
            for (InputEventListener e : inputListeners) {
                if (action > 0) {
                    e.keyPressed(key);
                    if (action == 1) {
                        e.keyPressedFirst(key);
                    }
                } else {
                    e.keyReleased(key);
                }
            }
        });
        glfwSetWindowMaximizeCallback(window, (window1, maximized) -> {
            updateWindowSize();
        });
    }

    public Version getVersion() {
        return new Version(GL11.glGetString(GL_VERSION), glfwGetVersionString());
    }

    void updateWindowSize() {
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            updateWindowSize(pWidth.get(0), pHeight.get(0));
        }
    }

    /**
     * Store w and h to Window
     *
     * @param w Width
     * @param h Height
     */
    void updateWindowSize(int w, int h) {
        this.width = w;
        this.height = h;
        glViewport(0, 0, w, h);
    }

    /**
     * Add your keyboard/mouse listener
     *
     * @param e your listener
     */
    public void addInputListener(InputEventListener e) {
        inputListeners.add(e);
    }

    /**
     * @param e your listener
     */
    public void addWindowListener(WindowEventListener e) {
        eventListeners.add(e);
    }

    public Graphics getGraphics() {
        return g;
    }

    public Clipboard getClipboard() {
        return clipboard;
    }

    public boolean isOpen() {
        return !glfwWindowShouldClose(window);
    }

    /**
     * Set window title
     *
     * @param title new title
     */
    public void setTitle(String title) {
        glfwSetWindowTitle(window, title);
        this.title = title;
    }

    /**
     * Set window size
     *
     * @param w width
     * @param h height
     */
    public void setSize(int w, int h) {
        this.width = w;
        this.height = h;
        glfwSetWindowSize(window, w, h);
        glViewport(0, 0, w, h);
    }

    /**
     * Set VSync (1 Frame)
     *
     * @param sync VSync state
     */
    public void setVSync(boolean sync) {
        this.sync = sync;
        glfwSwapInterval(sync ? 1 : 0);
    }

    /**
     * Set window position
     *
     * @param x X
     * @param y Y
     */
    public void setPosition(int x, int y) {
        glfwSetWindowPos(window, x, y);
    }

    /**
     * Set window resizable
     *
     * @param resize Resizable state
     */
    public void setResizable(boolean resize) {
        glfwWindowHint(GLFW_RESIZABLE, resize ? GLFW_TRUE : GLFW_FALSE);
    }

    /**
     * Set window visible
     *
     * @param visible Visible state
     */
    public void setVisible(boolean visible) {
        glfwWindowHint(GLFW_VISIBLE, visible ? 1 : 0);
    }

    /**
     * Set cursor visible
     *
     * @param visible Visible state
     */
    public void setCursorVisible(boolean visible) {
        glfwWindowHint(GLFW_CURSOR_HIDDEN, visible ? 0 : 1);
    }

    /**
     * Invoke when window close
     */
    public void dispose() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
    }

    /**
     * Set window title (FPS, memory)
     */
    public void setDebugTitle() {
        long usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
        glfwSetWindowTitle(window, title + " " + g.getFps() + " FPS, RAM: " + Files.humanReadableByteCountBin(usedMemory));
    }

    @Override
    public String toString() {
        return "Window{" +
                "wight=" + width +
                ", height=" + height +
                ", window=" + window +
                ", title='" + title + '\'' +
                '}';
    }
}
