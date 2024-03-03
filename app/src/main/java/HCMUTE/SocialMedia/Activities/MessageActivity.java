package HCMUTE.SocialMedia.Activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import HCMUTE.SocialMedia.Adapters.MessageAdapter;
import HCMUTE.SocialMedia.Client.SocketClient;
import HCMUTE.SocialMedia.Enums.MessageEnum;
import HCMUTE.SocialMedia.Models.MessageModel;
import HCMUTE.SocialMedia.R;

public class MessageActivity extends AppCompatActivity implements SocketClient.MessageListener {

    public static final String KEY_NAME = "NAME";
    private final String serverName = "192.168.1.22";
    private final int serverPort = 1234;
    private boolean socketIsAlready = false;
    private SocketClient socketClient = null;
    private EditText etTypeMessage;
    private ImageButton btSendMessage;
    private List<MessageModel> messageCards;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        etTypeMessage = findViewById(R.id.etTypeMessage);
        btSendMessage = findViewById(R.id.btSendMessage);

        messageCards = new ArrayList<>();
        recyclerView = findViewById(R.id.rvMessageArea);

        linearLayoutManager = new LinearLayoutManager(this);
        messageCardAdapter = new MessageAdapter(getApplicationContext(), messageCards);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messageCardAdapter);

        btSendMessage.setOnClickListener(v -> onClickSendMessage());
        connectSocket();
    }

    private void connectSocket(){
        try {
            socketClient = new SocketClient();
            socketClient.startConnection(serverName, serverPort);

            socketIsAlready = true;
            startMessageReceiver();
        } catch (Exception e) {
            socketIsAlready = false;
            Log.d("MessageActivity", "connectSocket failed");
        }
    }

    private void startMessageReceiver() {
        socketClient.setMessageListener(this);
    }

    private void onClickSendMessage() {
        if (!socketIsAlready) {
            Toast.makeText(getApplicationContext(), "Try later", Toast.LENGTH_LONG).show();
            return;
        }

        Random random = new Random();
        List<Integer> images = new ArrayList<>();
        images.add(0);
        images.add(R.drawable.post_image);

        String message = String.valueOf(etTypeMessage.getText());
        socketClient.sendMessage(message);

        updateMesage(
                MessageEnum.SEND,
                R.mipmap.ic_user_72_dark,
                null,
                "23:59 25-02-2024",
                message,
                images.get(random.nextInt(images.size())),
                null
        );
    }

    @Override
    public void onMessageReceived(String message) {
        runOnUiThread(() -> {
            updateMesage(
                    MessageEnum.RECEIVE,
                    R.mipmap.ic_user_72_dark,
                    "Johny Deep",
                    "23:59 25-02-2024",
                    message,
                    0,
                    null
            );
        });
    }

    private void updateMesage(MessageEnum viewType, int avatar, String fullname, String messageSendingAt, String text, int media, Boolean seen) {
        messageCards.add(new MessageModel(
                viewType,
                avatar,
                fullname,
                messageSendingAt,
                text,
                media,
                seen
        ));

        messageCardAdapter.notifyItemInserted(messageCardAdapter.getItemCount() - 1);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.smoothScrollToPosition(messageCardAdapter.getItemCount() - 1);
            }
        });

    }
}