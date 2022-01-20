package com.kewldan.socket;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Client extends Thread {
    public Socket socket;
    OutputStream writer;
    InputStream reader;
    ClientListener listener;

    /**
     * Create client
     * @param port Host port
     * @param address Host address
     * @param l Listener for new packets
     * @throws IOException Socket connection
     */
    public Client(int port, String address, ClientListener l) throws IOException {
        socket = new Socket(address, port);
        reader = socket.getInputStream();
        writer = socket.getOutputStream();
        listener = l;
        start();
    }

    /**
     * Listen packets
     */
    @Override
    public void run() {
        while (!interrupted() && socket.isConnected()) {
            try {
                ByteBuffer b = Server.waitBytes(reader);
                if(b != null)
                    listener.onPacketReceive(b, b.get(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Close the connection
     * @throws IOException socket close
     */
    public void close() throws IOException {
        interrupt();
        socket.close();
    }

    /**
     * Send packet to server
     * @param packet Packet to send
     * @throws IOException OutputStream writing
     */
    public void send(BasePacket packet) throws IOException {
        if (socket.isConnected()) {
            ByteBuffer buffer = ByteBuffer.allocate(4 + 1 + packet.getLength()); //Length(4) + Type(1) + Payload(n)
            buffer.putInt(buffer.capacity() - 4); //Length without length
            buffer.put(packet.type);
            packet.getBytes(buffer);
            writer.write(buffer.array());
            writer.flush();
        } else {
            System.out.println("[CLIENT] Failed to send message");
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "socket=" + socket +
                ", writer=" + writer +
                ", reader=" + reader +
                ", listener=" + listener +
                '}';
    }
}
