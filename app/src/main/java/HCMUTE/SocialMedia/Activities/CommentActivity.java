package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.CommentCardAdapter;
import HCMUTE.SocialMedia.Models.CommentCardModel;
import HCMUTE.SocialMedia.R;

public class CommentActivity extends AppCompatActivity {

    int x = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        List<CommentCardModel> commentCardModels = new ArrayList<>();

        for (int i = 0; i < x; i++) {
            commentCardModels.add(new CommentCardModel(
                    R.mipmap.ic_user_72_dark,
                    "Jonhny Deep",
                    "Hôm nay trời đẹp quá",
                    R.mipmap.ic_global_72_dark,
                    "23:59 25-02-2024"
            ));
        }

        RecyclerView recyclerView = findViewById(R.id.rvCommentArea);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommentCardAdapter(getApplicationContext(), commentCardModels));
    }
}