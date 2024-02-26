package HCMUTE.SocialMedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
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

        List<PostModel> postModels = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            postModels.add(new PostModel(
                    R.mipmap.ic_user_90_dark,
                    "Jonhny Deep",
                    "23:59 25-02-2024",
                    R.mipmap.ic_global_72_dark,
                    "Hôm nay trời đẹp quá",
                    R.drawable.post_image,
                    R.mipmap.ic_menu_90_dark
            ));
        }

        RecyclerView recyclerView = findViewById(R.id.rvPostArea);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new PostAdapter(getApplicationContext(), postModels));

    }
}