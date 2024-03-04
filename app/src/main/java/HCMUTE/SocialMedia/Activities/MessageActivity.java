package HCMUTE.SocialMedia.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import HCMUTE.SocialMedia.Adapters.MessageAdapter;
import HCMUTE.SocialMedia.Client.SocketClient;
import HCMUTE.SocialMedia.Enums.TypeReceiveMessageEnum;
import HCMUTE.SocialMedia.Models.MessageModel;
import HCMUTE.SocialMedia.R;

public class MessageActivity extends AppCompatActivity implements SocketClient.MessageListener {
    public static final String KEY_NAME = "NAME";
    private final String serverName = "192.168.1.22";
    private final int serverPort = 1234;
    private String username = "John";
    private SocketClient socketClient = null;
    private ImageButton ibBack;
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
        ibBack = findViewById(R.id.ibBack);

        messageCards = new ArrayList<>();
        recyclerView = findViewById(R.id.rvMessageArea);

        linearLayoutManager = new LinearLayoutManager(this);
        messageCardAdapter = new MessageAdapter(getApplicationContext(), messageCards);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messageCardAdapter);

        btSendMessage.setOnClickListener(v -> onClickSendMessage());
        ibBack.setOnClickListener(v -> finish());
        connectSocket();
    }

    private void connectSocket(){
        try {
            socketClient = new SocketClient();
            socketClient.startConnection(username, serverName, serverPort);
            startMessageReceiver();
        } catch (Exception e) {
            Log.d("MessageActivity", "connectSocket failed");
        }
    }

    private void startMessageReceiver() {
        socketClient.setMessageListener(this);
    }

    private void onClickSendMessage() {
        if (socketClient.isConnectedFailed()) {
            Toast.makeText(getApplicationContext(), "Try later", Toast.LENGTH_LONG).show();
            connectSocket();
            return;
        }

        String message = String.valueOf(etTypeMessage.getText());
//      String messageInfos = "send-to-global," + message;
//      String messageInfos = "send-to-group," + message + "," + "Mary-Suzi";
        String messageInfos = "send-to-person," + message + "," + "Mary";
        socketClient.sendMessage(messageInfos);

        Random random = new Random();
        List<Integer> images = new ArrayList<>();
        images.add(0);
        images.add(R.drawable.post_image);

        updateMesage(
                TypeReceiveMessageEnum.YOU,
                R.mipmap.ic_user_72_dark,
                null,
                "23:59 25-02-2024",
                message,
                images.get(random.nextInt(images.size())),
                null
        );
    }

    @Override
    public void onMessageReceived(TypeReceiveMessageEnum typeReceive, String message) {
        runOnUiThread(() -> {
            updateMesage(
                    typeReceive,
                    R.mipmap.ic_user_72_dark,
                    "Johny Deep",
                    "23:59 25-02-2024",
                    message,
                    0,
                    null
            );
        });
    }

    private void updateMesage(TypeReceiveMessageEnum viewType, int avatar, String fullname, String messageSendingAt, String text, int media, Boolean seen) {
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