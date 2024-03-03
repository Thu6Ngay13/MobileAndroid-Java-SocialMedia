package HCMUTE.SocialMedia.Activities;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.ConversationCardAdapter;
import HCMUTE.SocialMedia.Adapters.SettingAdapter;
import HCMUTE.SocialMedia.Models.ConversationCardModel;
import HCMUTE.SocialMedia.Models.SettingCardModel;
import HCMUTE.SocialMedia.Models.SettingModel;
import HCMUTE.SocialMedia.R;

public class ConversationActivity extends AppCompatActivity {

    private ImageButton ibBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        ibBack = findViewById(R.id.ibBack);
        ibBack.setOnClickListener(v -> finish());

        List<ConversationCardModel> conversationCards = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            conversationCards.add(new ConversationCardModel(R.mipmap.ic_user_72_dark, "Johnny Deep"));
        }

        RecyclerView recyclerView = findViewById(R.id.rvConversationArea);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ConversationCardAdapter(getApplicationContext(), conversationCards));
    }
}