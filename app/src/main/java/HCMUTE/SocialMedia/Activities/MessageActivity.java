package HCMUTE.SocialMedia.Activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import HCMUTE.SocialMedia.Adapters.MessageAdapter;
import HCMUTE.SocialMedia.Enums.TypeMessageEnum;
import HCMUTE.SocialMedia.Models.MessageModel;
import HCMUTE.SocialMedia.R;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MessageActivity extends AppCompatActivity {
    private final String TAG = "MessageActivity";
    private final String SERVER_PATH = "ws://192.168.1.22:1234";
    private Socket socketClient;
    private ImageButton ibBack;
    private EditText etTypeMessage;
    private ImageButton btSendMedia;
    private ImageButton btSendMessage;
    private List<MessageModel> messageCards;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        connectToSocketServer();

        ibBack = findViewById(R.id.ibBack);
        etTypeMessage = findViewById(R.id.etTypeMessage);
        btSendMedia = findViewById(R.id.btSendMedia);
        btSendMessage = findViewById(R.id.btSendMessage);

        messageCards = new ArrayList<>();
        recyclerView = findViewById(R.id.rvMessageArea);

        linearLayoutManager = new LinearLayoutManager(this);
        messageCardAdapter = new MessageAdapter(getApplicationContext(), messageCards);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messageCardAdapter);

        ibBack.setOnClickListener(v -> finish());
        etTypeMessage.requestFocus();
        btSendMedia.setOnClickListener(v -> onClickSendMedia());
        btSendMessage.setOnClickListener(v -> onClickSendMessage());
    }

    @Override
    protected void onPause() {
        super.onPause();
        socketClient.close();
    }

    private void connectToSocketServer() {
        try {
            socketClient = IO.socket(SERVER_PATH);
            socketClient.connect();

            List<String> usernames = new ArrayList<>();
            usernames.add("John");
            usernames.add("Marry");
            usernames.add("Caty");
            Random random = new Random();

            JSONObject newClient = new JSONObject();
            newClient.put("username", usernames.get(random.nextInt(usernames.size())));

            socketClient.emit("new", newClient);
            socketClient.on("receive", onReceiveMessage);
        } catch (Exception e) {
            Log.d(TAG, "Failed on connectToSocketServer: " + e.getMessage());
        }
    }

    private void onClickSendMedia() {
        if(!checkSocketAlready()) return;

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startForResult.launch(Intent.createChooser(intent, "Pick Media"));
    }

    private void onClickSendMessage() {
        try {
            if(!checkSocketAlready()) return;

            String message = String.valueOf(etTypeMessage.getText());
            String messageSendingAt = Calendar.getInstance().getTime().toString();
            updateMessage(TypeMessageEnum.SENDER_MESSAGE, "", messageSendingAt, message, null);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("typeSender", "MESSAGE");
            jsonObject.put("conversationId", "1");
            jsonObject.put("username", "John");
            jsonObject.put("fullname", "John");
            jsonObject.put("messageSendingAt", messageSendingAt);
            jsonObject.put("message", message);
            jsonObject.put("media", "");
            socketClient.emit("message", jsonObject);

        } catch (Exception e) {
            Log.d(TAG, "Failed on onClickSendMessage: " + e.getMessage());
        }
    }

    private boolean checkSocketAlready(){
        if(!socketClient.connected()){
            Toast.makeText(getApplicationContext(), "Try later", Toast.LENGTH_LONG).show();
            socketClient.connect();
        }

        return socketClient.connected();
    }

    private void updateMessage(TypeMessageEnum viewType, String fullname, String messageSendingAt, String text, Bitmap media) {
        recyclerView.post(() -> {
            messageCards.add(new MessageModel(viewType, fullname, messageSendingAt, text, media));
            messageCardAdapter.notifyItemInserted(messageCardAdapter.getItemCount() - 1);
            recyclerView.smoothScrollToPosition(messageCardAdapter.getItemCount() - 1);
        });

    }

    private final ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o != null && o.getResultCode() == RESULT_OK && o.getData() != null && o.getData().getData() != null) {
                try {
                    Uri selectedFileUri = o.getData().getData();
                    ContentResolver cR = getContentResolver();

                    InputStream is = cR.openInputStream(selectedFileUri);
                    Bitmap image = BitmapFactory.decodeStream(is);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    String mediaBase64String = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

                    String messageSendingAt = Calendar.getInstance().getTime().toString();
                    updateMessage(TypeMessageEnum.SENDER_MEDIA, "", messageSendingAt, "", image);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("typeSender", "MEDIA");
                    jsonObject.put("conversationId", "1");
                    jsonObject.put("username", "John");
                    jsonObject.put("fullname", "John");
                    jsonObject.put("messageSendingAt", messageSendingAt);
                    jsonObject.put("message", "");
                    jsonObject.put("media", mediaBase64String);
                    socketClient.emit("message", jsonObject);
                } catch (Exception e) {
                    Log.d(TAG, "Failed in startForResult" + e.getMessage());
                }
            }
        }
    });

    private final Emitter.Listener onReceiveMessage = args -> {
        try {
            JSONObject jsonReceive = (JSONObject) args[0];
            String typeReceiveString = jsonReceive.getString("typeSender");
            TypeMessageEnum typeReceive = TypeMessageEnum.fromString(typeReceiveString);
            if(typeReceive == null) return;

            String fullname = jsonReceive.getString("fullname");
            String messageSendingAt = jsonReceive.getString("messageSendingAt");
            String message = jsonReceive.getString("message");

            String mediaBase64String = jsonReceive.getString("media");
            byte[] bytes = Base64.decode(mediaBase64String, Base64.DEFAULT);
            Bitmap media = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

            updateMessage(typeReceive, fullname, messageSendingAt, message, media);
        } catch (JSONException e) {
            Log.d(TAG, "Failed on onReceiveMessage: " + e.getMessage());
        }
    };

}