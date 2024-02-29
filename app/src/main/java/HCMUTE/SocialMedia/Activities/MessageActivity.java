package HCMUTE.SocialMedia.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import HCMUTE.SocialMedia.Adapters.MessageAdapter;
import HCMUTE.SocialMedia.Client.EchoClient;
import HCMUTE.SocialMedia.Enums.MessageEnum;
import HCMUTE.SocialMedia.Models.MessageModel;
import HCMUTE.SocialMedia.R;

public class MessageActivity extends AppCompatActivity {

    private String serverName = "192.168.1.10";
    private int serverPort = 1234;
    private EchoClient client;
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

        etTypeMessage = (EditText) findViewById(R.id.etTypeMessage);
        btSendMessage = (ImageButton) findViewById(R.id.btSendMessage);

        messageCards = new ArrayList<>();
        recyclerView = findViewById(R.id.rvMessageArea);

        linearLayoutManager = new LinearLayoutManager(this);
        messageCardAdapter = new MessageAdapter(getApplicationContext(), messageCards);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messageCardAdapter);

        btSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSendMessage();
            }
        });

//        connectToSocket();
        onClickSendMessage();
    }

    private void connectToSocket(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                client = new EchoClient();
                client.startConnection(serverName, serverPort);
            }
        }).start();
    }

    private void onClickSendMessage() {
        String message = String.valueOf(etTypeMessage.getText());
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(2);
            if(x % 2 == 0){
                messageCards.add(new MessageModel(
                        MessageEnum.SEND,
                        R.mipmap.ic_user_72_dark,
                        null,
                        "23:59 25-02-2024",
                        "message",
                        R.drawable.post_image,
                        null
                ));
            }
            else {
                messageCards.add(new MessageModel(
                        MessageEnum.RECEIVE,
                        R.mipmap.ic_user_72_dark,
                        "Jonhny Deep",
                        "23:59 25-02-2024",
                        "message",
                        R.drawable.post_image,
                        false
                ));
            }

        }

        messageCardAdapter.notifyItemInserted(messageCardAdapter.getItemCount()-1);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.smoothScrollToPosition(messageCardAdapter.getItemCount() - 1);
            }
        });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                client.sendMessage(message);
//                Log.d("ServerXXX", "Send: " + message);
//            }
//        }).start();
    }
}