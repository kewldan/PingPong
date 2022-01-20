package com.kewldan.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Random;

public class Server extends Thread {
    ServerSocket server;
    ServerListener listener;
    HashMap<Integer, Connection> connections;
    Random random;

    /**
     * Create socket server
     * @param port Port
     * @param listener1 Packet listener
     * @throws IOException if port already used
     */
    public Server(int port, ServerListener listener1) throws IOException {
        server = new ServerSocket(port);
        connections = new HashMap<>();
        listener = listener1;
        random = new Random();
        start();
    }

    @Override
    public void run() {
        try {
            try {
                while (!Thread.interrupted()) {
                    Socket socket = server.accept();
                    try {
                        int id;
                        do{
                            id = random.nextInt();
                        }while(connections.containsKey(id));
                        Connection n = new Connection(socket, id, listener);
                        connections.put(id, n);
                        listener.onConnection(n);
                    } catch (IOException e) {
                        socket.close();
                    }
                }
            } finally {
                server.close();
            }
        } catch (Exception e) {

        }
    }

    /**
     * Close all connections
     * @throws IOException
     */
    public void close() throws IOException {
        interrupt();
        for(Connection c : connections.values()){
            c.close();
        }
        server.close();
        connections.clear();
    }

    /**
     * Send packet to client
     * @param id Connection ID
     * @param packet Packet to send
     * @throws IOException
     */
    public void send(int id, BasePacket packet) throws IOException {
        Connection c = connections.get(id);
        if(c != null){
            c.send(packet);
        }
    }

    /**
     * Method for waiting InputStream bytes
     * @param is InputStream
     * @return Bytes (WITHOUT LENGTH)
     * @throws IOException
     */
    static ByteBuffer waitBytes(InputStream is) throws IOException {
        try {
            int len = (is.read() & 0xFF) << 24 | (is.read() & 0xFF) << 16 | (is.read() & 0xFF) << 8 | (is.read() & 0xFF); //Payload length (With type)
            ByteBuffer buffer = ByteBuffer.allocate(len);
            for (int i = 0; i < len; i++) {
                buffer.put((byte) is.read());
            }

            return buffer;
        }catch (SocketException e){

        }

        return null;
    }

    @Override
    public String toString() {
        return "Server{" +
                "server=" + server +
                ", listener=" + listener +
                ", connections=" + connections +
                ", random=" + random +
                '}';
    }
}
