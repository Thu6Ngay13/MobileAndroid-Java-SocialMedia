package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.PostAdapter;
import HCMUTE.SocialMedia.Enums.Select;
import HCMUTE.SocialMedia.Models.PostModel;
import HCMUTE.SocialMedia.Models.SelectModel;
import HCMUTE.SocialMedia.R;

public class MainActivity extends AppCompatActivity {
    private List<SelectModel> selectModels;
    private Select chosen;
    private ImageButton ibHome;
    private ImageButton ibFriend;
    private ImageButton ibNotify;
    private ImageButton ibSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectModels = new ArrayList<>();
        ibHome = (ImageButton)findViewById(R.id.ibHome);
        ibFriend = (ImageButton)findViewById(R.id.ibFriend);
        ibNotify = (ImageButton)findViewById(R.id.ibNotify);
        ibSetting = (ImageButton)findViewById(R.id.ibSetting);

        selectModels.add(new SelectModel(Select.Home, ibHome, R.mipmap.ic_home_72_line));
        selectModels.add(new SelectModel(Select.Friend, ibFriend, R.mipmap.ic_friend_72_line));
        selectModels.add(new SelectModel(Select.Notify, ibNotify, R.mipmap.ic_bell_72_line));
        selectModels.add(new SelectModel(Select.Setting, ibSetting, R.mipmap.ic_setting_72_light));

        onClickHome();
        chosen = Select.Home;
        ibHome.setImageResource(R.mipmap.ic_home_72_full);

        ibHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectModel oldSelectModel = selectModels.get(chosen.ordinal());
                if(oldSelectModel.getId() == Select.Home)
                    return;


                chosen = Select.Home;
                ibHome.setImageResource(R.mipmap.ic_home_72_full);
                oldSelectModel.getIb().setImageResource(oldSelectModel.getImg());

                onClickHome();
            }
        });

        ibFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectModel oldSelectModel = selectModels.get(chosen.ordinal());
                if(oldSelectModel.getId() == Select.Friend)
                    return;

                chosen = Select.Friend;
                ibFriend.setImageResource(R.mipmap.ic_friend_72_full);
                oldSelectModel.getIb().setImageResource(oldSelectModel.getImg());

                Toast.makeText(MainActivity.this, "Friend", Toast.LENGTH_SHORT).show();
            }
        });

        ibNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectModel oldSelectModel = selectModels.get(chosen.ordinal());
                if(oldSelectModel.getId() == Select.Notify)
                    return;
                
                chosen = Select.Notify;
                ibNotify.setImageResource(R.mipmap.ic_bell_72_full);
                oldSelectModel.getIb().setImageResource(oldSelectModel.getImg());

                Toast.makeText(MainActivity.this, "Notify", Toast.LENGTH_SHORT).show();
            }
        });

        ibSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectModel oldSelectModel = selectModels.get(chosen.ordinal());
                if(oldSelectModel.getId() == Select.Setting)
                    return;

                chosen = Select.Setting;
                ibSetting.setImageResource(R.mipmap.ic_setting_72_dark);
                oldSelectModel.getIb().setImageResource(oldSelectModel.getImg());

                Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickHome(){
        List<PostModel> postModels = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            postModels.add(new PostModel(
                    R.mipmap.ic_user_72_dark,
                    "Jonhny Deep",
                    "23:59 25-02-2024",
                    R.mipmap.ic_global_72_dark,
                    "Hôm nay trời đẹp quá",
                    R.drawable.post_image,
                    R.mipmap.ic_more_72_dark
            ));
        }

        RecyclerView recyclerView = findViewById(R.id.rvMainArea);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new PostAdapter(getApplicationContext(), postModels));
    }
}