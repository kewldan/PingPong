package com.kewldan.engine.Misc;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class Files {
    static ClassLoader loader;

    static {
        loader = Files.class.getClassLoader();
    }


    /**
     * Get file in resource String content
     *
     * @param path path to file
     * @return string in file
     * @throws IOException if file not exists
     */
    public static String getString(String path) {
        return new String(getByteBuffer(path).array());
    }

    /**
     * Get file in resource bytes
     *
     * @param path path to file
     * @return bytes in file
     * @throws IOException if file not exists
     */
    public static ByteBuffer getByteBuffer(String path) {
        InputStream is = loader.getResourceAsStream(path);
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(is.available());
            while (is.available() > 0) {
                byteBuffer.put((byte) is.read());
            }
            return byteBuffer;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get image bitmap
     *
     * @param image image
     * @return image bytes
     */
    public static ByteBuffer getImageBytes(BufferedImage image) {
        boolean hasAlpha = image.getColorModel().hasAlpha();
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * (hasAlpha ? 4 : 3)); //4 for RGBA, 3 for RGB

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                if (hasAlpha)
                    buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        buffer.flip();
        return buffer;
    }

    public static InputStreamReader getFileReader(String path){
        return new InputStreamReader(loader.getResourceAsStream(path));
    }

    /**
     * Get image in resource
     *
     * @param path path to image
     * @return image in resources
     * @throws IOException if image not exists
     */
    public static BufferedImage getImage(String path) {
        InputStream is = loader.getResourceAsStream(path);
        try {
            return ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Human readable byte count format
     *
     * @param bytes See description
     * @return String LOL
     */
    public static String humanReadableByteCountBin(long bytes) {
        long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        if (absB < 1024) {
            return bytes + " B";
        }
        long value = absB;
        CharacterIterator ci = new StringCharacterIterator("KMGTPE");
        for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        value *= Long.signum(bytes);
        return String.format("%.1f %cB", value / 1024.0, ci.current());
    }
}
