package HCMUTE.SocialMedia.RealTime;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import HCMUTE.SocialMedia.Consts.Const;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketIO {
    private static final String TAG = "ServerRealTime";
    private static Socket socketClient;

    public static void connectToServer() {
        try {

            IO.Options options = new IO.Options();
            options.reconnection = true;
            options.reconnectionAttempts = Integer.MAX_VALUE;
            options.reconnectionDelay = 3000L;

            Socket socket = IO.socket(Const.SERVER_SOCKETIO, options);
            socketClient = socket;

            socket.connect();
            socketClient.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Connected to server");
                    try {
                        JSONObject newClient = new JSONObject();
                        newClient.put("username", Const.USERNAME);

                        SocketIO.socketClient.emit("new", newClient);
                        socketClient.on("receivePushNotify", new Emitter.Listener() {
                            @Override
                            public void call(Object... args) {
                                try {

                                } catch (Exception e) {
                                    Log.d(TAG, "Failed on onReceiveMessage: " + e.getMessage());
                                }
                            }
                        });
                    } catch (Exception e) {
                        Log.d(SocketIO.TAG, "Failed on connectToServer when Socket.EVENT_CONNECT: " + e.getMessage());
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