package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.MyPersonalPageAdapter;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.R;

public class MyPersonalPageActivity extends AppCompatActivity {

    int x = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_personal_page);
        loadData();
    }

    private void loadData(){
        List<YourFriendModel> yourFriendModels = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            yourFriendModels.add(new YourFriendModel(
                    R.mipmap.ic_user_72_dark,
                    "Cao Thị Thu Thủy"
            ));
        }

        List<PostCardModel> postCardModels = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            postCardModels.add(new PostCardModel(
                    1,
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

        RecyclerView recyclerView = findViewById(R.id.rvMyPersonalPageArea);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MyPersonalPageAdapter(getApplicationContext(), yourFriendModels, postCardModels));
    }
}