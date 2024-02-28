package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.FriendAdapter;
import HCMUTE.SocialMedia.Adapters.PostAdapter;
import HCMUTE.SocialMedia.Enums.MainSelection;
import HCMUTE.SocialMedia.Models.FriendModel;
import HCMUTE.SocialMedia.Models.FriendRequestModel;
import HCMUTE.SocialMedia.Models.PostModel;
import HCMUTE.SocialMedia.Models.MainSelectionModel;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.R;

public class MainActivity extends AppCompatActivity {
    private List<MainSelectionModel> mainSelectionModels;
    private MainSelection chosen;
    private ImageButton ibHome;
    private ImageButton ibFriend;
    private ImageButton ibNotify;
    private ImageButton ibSetting;

    int x = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainSelectionModels = new ArrayList<>();
        ibHome = (ImageButton)findViewById(R.id.ibHome);
        ibFriend = (ImageButton)findViewById(R.id.ibFriend);
        ibNotify = (ImageButton)findViewById(R.id.ibNotify);
        ibSetting = (ImageButton)findViewById(R.id.ibSetting);

        mainSelectionModels.add(new MainSelectionModel(MainSelection.Home, ibHome, R.mipmap.ic_home_72_line));
        mainSelectionModels.add(new MainSelectionModel(MainSelection.Friend, ibFriend, R.mipmap.ic_friend_72_line));
        mainSelectionModels.add(new MainSelectionModel(MainSelection.Notify, ibNotify, R.mipmap.ic_bell_72_line));
        mainSelectionModels.add(new MainSelectionModel(MainSelection.Setting, ibSetting, R.mipmap.ic_setting_72_light));

        onClickHome();
        chosen = MainSelection.Home;
        ibHome.setImageResource(R.mipmap.ic_home_72_full);

        ibHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainSelectionModel oldMainSelectionModel = mainSelectionModels.get(chosen.ordinal());
                if(oldMainSelectionModel.getId() == MainSelection.Home)
                    return;

                chosen = MainSelection.Home;
                ibHome.setImageResource(R.mipmap.ic_home_72_full);
                oldMainSelectionModel.getIb().setImageResource(oldMainSelectionModel.getImg());

                onClickHome();
            }
        });

        ibFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainSelectionModel oldMainSelectionModel = mainSelectionModels.get(chosen.ordinal());
                if(oldMainSelectionModel.getId() == MainSelection.Friend)
                    return;

                chosen = MainSelection.Friend;
                ibFriend.setImageResource(R.mipmap.ic_friend_72_full);
                oldMainSelectionModel.getIb().setImageResource(oldMainSelectionModel.getImg());

                onClickFriend();
            }
        });

        ibNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainSelectionModel oldMainSelectionModel = mainSelectionModels.get(chosen.ordinal());
                if(oldMainSelectionModel.getId() == MainSelection.Notify)
                    return;
                
                chosen = MainSelection.Notify;
                ibNotify.setImageResource(R.mipmap.ic_bell_72_full);
                oldMainSelectionModel.getIb().setImageResource(oldMainSelectionModel.getImg());

                Toast.makeText(MainActivity.this, "Notify", Toast.LENGTH_SHORT).show();
            }
        });

        ibSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainSelectionModel oldMainSelectionModel = mainSelectionModels.get(chosen.ordinal());
                if(oldMainSelectionModel.getId() == MainSelection.Setting)
                    return;

                chosen = MainSelection.Setting;
                ibSetting.setImageResource(R.mipmap.ic_setting_72_dark);
                oldMainSelectionModel.getIb().setImageResource(oldMainSelectionModel.getImg());

                Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickHome(){
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

        RecyclerView recyclerView = findViewById(R.id.rvMainArea);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PostAdapter(getApplicationContext(), postModels));
    }

    private void onClickFriend(){
        List<FriendModel> friendModels = new ArrayList<>();
        List<FriendRequestModel> friendRequestModels = new ArrayList<>();
        List<YourFriendModel> yourFriendModels = new ArrayList<>();

        for (int i = 0; i < x; i++) {
            friendRequestModels.add(new FriendRequestModel(
                    R.mipmap.ic_user_72_dark,
                    "Jonhny Deep",
                    "23:59 25-02-2024"
            ));
            yourFriendModels.add(new YourFriendModel(
                    R.mipmap.ic_user_72_dark,
                    "Jonhny Deep"
            ));
        }

        friendModels.add(new FriendModel(friendRequestModels, yourFriendModels));
        RecyclerView recyclerView = findViewById(R.id.rvMainArea);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FriendAdapter(getApplicationContext(), friendModels));
    }
}