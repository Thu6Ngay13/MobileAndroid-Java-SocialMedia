package HCMUTE.SocialMedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        List<PostModel> posts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            posts.add(new PostModel(
                    R.mipmap.ic_user_90_dark,
                    "Jonhny Deep",
                    "23:59 25-02-2024",
                    R.mipmap.ic_global_72_dark,
                    "Hôm nay trời đẹp quá",
                    R.drawable.post_image,
                    R.mipmap.ic_menu_90_dark,
                    R.mipmap.ic_like_72_line,
                    R.mipmap.ic_comment_72_dark,
                    R.mipmap.ic_share_72_dark
            ));
        }

        RecyclerView recyclerView = findViewById(R.id.rvPostArea);
        recyclerView.setAdapter(new PostAdapter(getApplicationContext(), posts));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}