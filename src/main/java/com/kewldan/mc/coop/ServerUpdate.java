package com.kewldan.mc.coop;

import com.kewldan.socket.BasePacket;

import java.nio.ByteBuffer;

public class ServerUpdate extends BasePacket {
    public float y, bx, by;
    public int serverScore, playerScore;

    public ServerUpdate(float y, float bx, float by, int serverScore, int playerScore) {
        this.y = y;
        this.bx = bx;
        this.by = by;
        this.serverScore = serverScore;
        this.playerScore = playerScore;
    }

    public ServerUpdate(ByteBuffer src){
        y = src.getFloat(1);
        bx = src.getFloat(5);
        by = src.getFloat(9);
        serverScore = src.getInt(13);
        playerScore = src.getInt(17);
    }

    @Override
    protected void getBytes(ByteBuffer src) {
        src.putFloat(y);
        src.putFloat(bx);
        src.putFloat(by);
        src.putInt(serverScore);
        src.putInt(playerScore);
    }

    @Override
    protected int getLength() {
        return 20;
    }
}
