package HCMUTE.SocialMedia.Client;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {
    public interface MessageListener {
        void onMessageReceived(String message);
    }
    private Socket clientSocket = null;
    private boolean running = false;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private MessageListener messageListener;

    public boolean startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            running = true;
            onRunning();
        } catch (Exception e) {
            Log.d("ClientXXX", "startConnection failed");
            return false;
        }
        return true;
    }

    private void onRunning() {
        new Thread(() -> {
            try {
                while (running) {
                    Thread.sleep(300);
                    String serverMessage = in.readLine();
                    if (serverMessage != null) {
                        System.out.println("Received from server: " + serverMessage);

                        // Notify the listener about the received message
                        if (messageListener != null) {
                            messageListener.onMessageReceived(serverMessage);
                        }
                    }
                }
            } catch (Exception e) {
                Log.d("ClientXXX", "onRunning failed");
            }
        }).start();
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

    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }
}