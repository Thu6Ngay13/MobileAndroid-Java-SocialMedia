package HCMUTE.SocialMedia.Activities;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.HomeAdapter;
import HCMUTE.SocialMedia.Adapters.PostAdapter;
import HCMUTE.SocialMedia.Models.HomeModel;
import HCMUTE.SocialMedia.Models.PostModel;
import HCMUTE.SocialMedia.R;

public class GroupActivity extends AppCompatActivity {

    int x = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        List<PostModel> postModels = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            postModels.add(new PostModel(
                    R.mipmap.ic_user_72_dark,
                    "Jonhny Deep",
                    "23:59 25-02-2024",
                    R.mipmap.ic_global_72_dark,
                    "Hôm nay trời đẹp quá",
                    R.drawable.post_image,
                    false
            ));
        }

        RecyclerView recyclerView = findViewById(R.id.rvGroupArea);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PostAdapter(getApplicationContext(), postModels));
    }

}