package com.kewldan.socket;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Connection extends Thread {
    Socket socket;
    InputStream reader;
    OutputStream writer;
    int id;
    ServerListener listener;

    public Connection(Socket socket, int id, ServerListener listener) throws IOException {
        this.socket = socket;
        this.id = id;
        reader = socket.getInputStream();
        writer = socket.getOutputStream();
        this.listener = listener;
        start();
    }

    /**
     * Get connection ID
     * @return ID
     */
    public int getConnectionID() {
        return id;
    }

    @Override
    public void run() {
        try {
            while (!interrupted() && socket.isConnected()) {
                listener.onPacketReceive(Server.waitBytes(reader), this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the connection
     * @throws IOException Socket close
     */
    public void close() throws IOException {
        interrupt();
        socket.close();
    }

    /**
     * Send packet to client
     * @param packet packet to send
     * @throws IOException OutputStream write
     */
    public void send(BasePacket packet) throws IOException {
        if(socket.isConnected()){
            ByteBuffer buffer = ByteBuffer.allocate(4 + 1 + packet.getLength()); //Length(4) + Type(1) + Payload(n)
            buffer.putInt(packet.getLength() + 1); //Length without length
            buffer.put(packet.type);
            packet.getBytes(buffer);
            writer.write(buffer.array());
            writer.flush();
        }else{
            System.out.println("[CONNECTION] Failed to send message");
        }
    }

    @Override
    public String toString() {
        return "Connection{" +
                "socket=" + socket +
                ", reader=" + reader +
                ", writer=" + writer +
                ", id=" + id +
                ", listener=" + listener +
                '}';
    }
}
