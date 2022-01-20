package com.kewldan.socket;

import java.nio.ByteBuffer;

public interface ClientListener {
    void onPacketReceive(ByteBuffer packet, byte type);
}
