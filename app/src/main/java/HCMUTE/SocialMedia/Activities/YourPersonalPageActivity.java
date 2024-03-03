package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.MyPersonalPageAdapter;
import HCMUTE.SocialMedia.Adapters.YourPersonalPageAdapter;
import HCMUTE.SocialMedia.Models.PostModel;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.R;

public class YourPersonalPageActivity extends AppCompatActivity {
    int x = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_personal_page);
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

        List<PostModel> postModels = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            postModels.add(new PostModel(
                    R.mipmap.ic_user_72_dark,
                    "Cao Thị Thu Thủy",
                    "23:59 25-02-2024",
                    R.mipmap.ic_global_72_dark,
                    "Hôm nay trời đẹp quá",
                    R.drawable.post_image,
                    false
            ));
        }
        RecyclerView recyclerView = findViewById(R.id.rvYourPersonalPageArea);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new YourPersonalPageAdapter(getApplicationContext(), yourFriendModels, postModels));
    }
}