package HCMUTE.SocialMedia.Client;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {
    private Socket clientSocket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private boolean running = false;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            new Thread(() -> {
                try {
                    while (running) {
                        String serverMessage = in.readLine();
                        if (serverMessage != null) {
                            System.out.println("Received from server: " + serverMessage);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            running = true;
        } catch (Exception e) {
            Log.d("ServerXXX", "startConnection failed");
        }
    }

    public void sendMessage(String msg) {
        try {
            out.println(msg);
        } catch (Exception e) {
            Log.d("ServerXXX", "sendMessage failed");
        }
    }

    public void stopConnection() {
        try {
            running = false;

            in.close();
            out.close();

            clientSocket.close();
        } catch (Exception e) {
            Log.d("ServerXXX", "stopConnection failed");
        }
    }
}