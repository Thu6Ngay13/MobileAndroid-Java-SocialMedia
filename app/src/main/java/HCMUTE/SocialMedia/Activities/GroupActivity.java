package HCMUTE.SocialMedia.Activities;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.PostAdapter;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.R;

public class GroupActivity extends AppCompatActivity {

    int x = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        List<PostCardModel> postCardModels = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            postCardModels.add(new PostCardModel(
                    "avaterurl",
                    "username",
                    "Jonhny Deep",
                    "23:59 25-02-2024",
                    R.mipmap.ic_global_72_dark,
                    "Hôm nay trời đẹp quá",
                    "postMedia",
                    false
            ));
        }

        RecyclerView recyclerView = findViewById(R.id.rvGroupArea);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PostAdapter(getApplicationContext(), postCardModels));
    }

}