package com.kewldan.mc;

import com.kewldan.engine.Graphic.ShapeBatch;
import com.kewldan.engine.Graphic.primitives.Ellipse;
import com.kewldan.engine.Graphic.primitives.Quad;
import com.kewldan.engine.Input.Hitbox;
import com.kewldan.engine.Misc.Scene;
import com.kewldan.engine.Preferences;
import com.kewldan.mc.coop.PlayerUpdate;
import com.kewldan.mc.coop.ServerUpdate;
import com.kewldan.socket.Client;
import com.kewldan.socket.Connection;
import com.kewldan.socket.Server;
import com.kewldan.socket.ServerListener;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

public class Playing extends Scene {
    final int rad = 15;

    Preferences prefs;
    Ellipse ball;
    Quad serverQuad, playerQuad;
    Server server;
    Client client;
    float vx, vy;
    int serverScore, playerScore;
    ShapeBatch shapes;

    //Only server:
    Hitbox serverHitbox, playerHitbox; //Players

    @Override
    public void load() {
        super.load();
        try {
            prefs = new Preferences("settings");
            if (prefs.exists()) {
                prefs.load();
            } else {
                prefs.addBoolean("isHost", true)
                        .addString("hostIP", "127.0.0.1")
                        .addInt("difficulty", 3)
                        .addInt("port", 6006)
                        .save();
            }
            for (String s : Main.args) {
                if (s.equals("-client")) {
                    prefs.addBoolean("isHost", false);
                    System.out.println("Override as client");
                } else if (s.equals("-server")) {
                    prefs.addBoolean("isHost", true);
                    System.out.println("Override as server");
                }
            }
            if (prefs.getBoolean("isHost", false)) {
                server = new Server(prefs.getInt("port"), new ServerListener() {
                    @Override
                    public void onPacketReceive(ByteBuffer bytes, Connection connection) {
                        PlayerUpdate update = new PlayerUpdate(bytes);
                        playerQuad.view.pos.y = update.y;

                        try {
                            connection.send(new ServerUpdate(serverQuad.view.pos.y, ball.view.pos.x, ball.view.pos.y, serverScore, playerScore));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onConnection(Connection connection) {
                        playerScore = 0;
                        serverScore = 0;
                    }
                });
            } else {
                client = new Client(prefs.getInt("port"), prefs.getString("hostIP"), (packet, type) -> {
                    ServerUpdate update = new ServerUpdate(packet);
                    serverScore = update.serverScore;
                    playerScore = update.playerScore;
                    ball.view.pos.x = update.bx;
                    ball.view.pos.y = update.by;
                    serverQuad.view.pos.y = update.y;
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        window.setResizable(false);

        shapes = new ShapeBatch();

        serverQuad = new Quad(10, 10, 20, 100);
        playerQuad = new Quad(window.width - 30, 10, 20, 100);
        serverQuad.view.setRounded(5);
        playerQuad.view.setRounded(5);
        ball = new Ellipse(window.width / 2, window.height / 2, rad, rad);

        serverHitbox = new Hitbox(serverQuad);
        playerHitbox = new Hitbox(playerQuad);
        updateBall();
    }

    void updateBall() {
        ball.view.pos.x = window.width / 2;
        ball.view.pos.y = window.height / 2;
        vx = ((float) Math.random() * 2 - 1) * (prefs.getInt("difficulty") + playerScore + serverScore);
        vy = ((float) Math.random() * 2 - 1) * (prefs.getInt("difficulty") + playerScore + serverScore);
    }

    @Override
    public void draw() {
        window.getGraphics().clear();

        Default.font.begin();
        Default.font.drawCentered("Server: " + serverScore, 150, 50);
        Default.font.drawCentered("Player: " + playerScore, window.width - 150, 50);
        Default.font.end();

        shapes.begin();
        shapes.draw(serverQuad, playerQuad, ball);
        shapes.end();

        try {
            if (prefs.getBoolean("isHost")) {
                serverQuad.view.pos.y = InputManager.mouseY;
                //Server tick

                if (ball.view.pos.x < rad) { //Horizontal walls collision
                    updateBall();
                    playerScore++;
                } else if (ball.view.pos.x > window.width - rad) {
                    updateBall();
                    serverScore++;
                }

                if (ball.view.pos.y < rad || ball.view.pos.y > window.height - rad) { //Vertical walls collision
                    vy = -vy;
                }

                if (serverHitbox.circle(ball.view.pos.x, ball.view.pos.y, rad) || playerHitbox.circle(ball.view.pos.x, ball.view.pos.y, rad)) { //Players collision
                    vx = -vx;
                }

                ball.view.pos.x += vx;
                ball.view.pos.y += vy;
            } else {
                playerQuad.view.pos.y = InputManager.mouseY;
                client.send(new PlayerUpdate(playerQuad.view.pos.y));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}
