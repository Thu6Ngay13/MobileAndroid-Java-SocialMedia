package HCMUTE.SocialMedia.RealTime;

import android.util.Log;

import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketIOServerRealTime {
    private static final String SERVER_PATH = "ws://192.168.1.10:1234";
    private static final String TAG = "ServerRealTime";
    private static final String USERNAME = "abc";
    private static Socket socketClient;

    public static void connectToServer() {
        try {
            IO.Options options = new IO.Options();
            options.reconnection = true;
            options.reconnectionAttempts = Integer.MAX_VALUE;
            options.reconnectionDelay = 3000L;

            Socket socket = IO.socket(SERVER_PATH, options);
            socketClient = socket;

            socket.connect();
            socketClient.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Connected to server");
                    try {
                        JSONObject newClient = new JSONObject();
                        newClient.put("username", SocketIOServerRealTime.USERNAME);
                        SocketIOServerRealTime.socketClient.emit("new", newClient);
                    } catch (Exception e) {
                        Log.d(SocketIOServerRealTime.TAG, "Failed on connectToServer when Socket.EVENT_CONNECT: " + e.getMessage());
                    }
                }
            });
            socketClient.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Disconnected from server");
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "Failed on connectToSocketServer: " + e.getMessage());
        }
    }

    public static void onMessage(Emitter.Listener onReceiveMessage) {
        try {
            socketClient.on("receiveMessage", onReceiveMessage);
        } catch (Exception e) {
            Log.d(TAG, "Error in onMessage: " + e.getMessage());
        }
    }

    public static void offMessage(Emitter.Listener onReceiveMessage) {
        try {
            socketClient.off("receiveNotify", onReceiveMessage);
        } catch (Exception e) {
            Log.d(TAG, "Error in offMessage: " + e.getMessage());
        }
    }

    public static void onNotify(Emitter.Listener onReceiveNotify) {
        try {
            socketClient.on("receiveNotify", onReceiveNotify);
        } catch (Exception e) {
            Log.d(TAG, "Error in onNotify: " + e.getMessage());
        }
    }

    public static void offNotify(Emitter.Listener onReceiveMessage) {
        try {
            socketClient.off("receiveNotify", onReceiveMessage);
        } catch (Exception e) {
            Log.d(TAG, "Error in offNotify: " + e.getMessage());
        }
    }
}