package com.kewldan.socket;

import java.nio.ByteBuffer;

public interface ServerListener {
    void onPacketReceive(ByteBuffer bytes, Connection connection);
    void onConnection(Connection connection);
}
