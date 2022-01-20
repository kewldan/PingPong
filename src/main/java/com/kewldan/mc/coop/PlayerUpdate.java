package com.kewldan.mc.coop;

import com.kewldan.socket.BasePacket;

import java.nio.ByteBuffer;

public class PlayerUpdate extends BasePacket {
    public float y;

    public PlayerUpdate(float y){
        this.y = y;
    }

    public PlayerUpdate(ByteBuffer src){
        y = src.getFloat(1);
    }

    @Override
    protected void getBytes(ByteBuffer src) {
        src.putFloat(y);
    }

    @Override
    protected int getLength() {
        return 4;
    }
}
