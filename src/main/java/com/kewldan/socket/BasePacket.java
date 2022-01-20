package com.kewldan.socket;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public abstract class BasePacket {
    public byte type;

    /**
     * Charset for encoding strings
     */
    public static final Charset charset = StandardCharsets.UTF_8;

    /**
     * Get packet bytes to Source for sending
     * @param src Source
     */
    protected abstract void getBytes(ByteBuffer src);

    /**
     * Get packet length
     * @return packet length (in bytes)
     */
    protected abstract int getLength();
}
