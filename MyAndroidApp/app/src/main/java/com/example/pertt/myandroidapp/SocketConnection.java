package com.example.pertt.myandroidapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by pertt on 2017-12-12.
 */

public class SocketConnection {
    private static Socket socket;
    private static final String HOST = "10.0.2.2";
    private static final int TIMEOUT_SERVER_SOCKET = 30000;
    private static final int TIMEOUT_CLIENT_SOCKET = 180000;
    private static final int PORT = 8000;
    private static int attempts = 0;
    private static boolean connected = false;
    private static BufferedReader inStream;
    private static PrintWriter outStream;

    private SocketConnection() {
    }
    public static int getConnectionAttempts() {
        return attempts;
    }
    public static synchronized boolean connect() {
        try {
            if (connected) {
                return true;
            }
            attempts++;
            connected = true;
            socket = new Socket();
            socket.connect(new InetSocketAddress(HOST, PORT), TIMEOUT_SERVER_SOCKET);
            socket.setSoTimeout(TIMEOUT_CLIENT_SOCKET);
            /*
            outStream = new PrintWriter(socket.getOutputStream(), true);
            inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));*/
            return true;
        } catch (IOException e) {
            socket = null;
            connected = false;
            outStream = null;
            inStream = null;
            e.printStackTrace();
            return false;
        }
    }
    public static synchronized void sendGuess(String guess) {
        if (!connected) {
            return;
        }
        outStream.write(guess);
    }
}
