package HCMUTE.SocialMedia.Client;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class SocketClient {
    public interface MessageListener {
        void onMessageReceived(String message);
    }
    private Socket clientSocket;
    private boolean running;
    private PrintWriter out;
    private BufferedReader in;
    private MessageListener messageListener;

    public SocketClient() {
        clientSocket = new Socket();
        running = false;
        out = null;
        in = null;
    }

    public void startConnection(String ip, int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!running){
                    try {
                        Thread.sleep(3000);
                        clientSocket.connect(new InetSocketAddress(ip, port), 3000);
                        out = new PrintWriter(clientSocket.getOutputStream(), true);
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                        running = isConnected();
                        onRunning();
                    } catch (SocketTimeoutException e){
                        running = false;
                        clientSocket = new Socket();
                    }
                    catch (Exception e) {
                        running = false;
                        Log.d("SocketClient", "startConnection failed " + e.getMessage());
                    }

                }
            }
        }).start();
    }

    private void onRunning() {
        new Thread(() -> {
            try {
                while (running) {
                    Thread.sleep(300);
                    String serverMessage = in.readLine();
                    if (serverMessage != null && messageListener != null) {
                        messageListener.onMessageReceived(serverMessage);
                    }
                }
            } catch (Exception e) {
                Log.d("SocketClient", "onRunning failed " + e.getMessage());
            }
        }).start();
    }

    public void sendMessage(String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    out.println(msg);
                } catch (Exception e) {
                    Log.d("SocketClient", "sendMessage failed");
                }
            }
        }).start();
    }

    public void stopConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (running){
                    try {
                        running = false;

                        in.close();
                        out.close();

                        clientSocket.close();
                        running = isClosed();
                    } catch (Exception e) {
                        running = true;
                        Log.d("SocketClient", "stopConnection failed " + e.getMessage());
                    }
                }
            }
        }).start();
    }

    public boolean isConnected(){
        return clientSocket.isConnected();
    }

    public boolean isClosed(){
        return clientSocket.isClosed();
    }

    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }
}