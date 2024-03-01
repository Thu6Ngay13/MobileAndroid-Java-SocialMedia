package HCMUTE.SocialMedia.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import HCMUTE.SocialMedia.Adapters.FriendAdapter;
import HCMUTE.SocialMedia.Adapters.HomeAdapter;
import HCMUTE.SocialMedia.Adapters.NotifyAdapter;
import HCMUTE.SocialMedia.Adapters.SettingAdapter;
import HCMUTE.SocialMedia.Enums.MainSelectionEnum;
import HCMUTE.SocialMedia.Models.FriendModel;
import HCMUTE.SocialMedia.Models.FriendRequestModel;
import HCMUTE.SocialMedia.Models.HomeModel;
import HCMUTE.SocialMedia.Models.NotifyCardModel;
import HCMUTE.SocialMedia.Models.NotifyModel;
import HCMUTE.SocialMedia.Models.PostModel;
import HCMUTE.SocialMedia.Models.MainSelectionModel;
import HCMUTE.SocialMedia.Models.SettingCardModel;
import HCMUTE.SocialMedia.Models.SettingModel;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.R;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 999;
    private ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if(o != null && o.getResultCode() == RESULT_OK){
                if (o.getData() != null && o.getData().getStringExtra(MessageActivity.KEY_NAME) != null){
                    Log.d("Receipt Data", "onActivityResult: " + o.getData().getStringExtra(MessageActivity.KEY_NAME));
                }
            }
        }
    });

    private List<MainSelectionModel> mainSelectionModels;
    private MainSelectionEnum chosen;
    private ImageButton ibHome;
    private ImageButton ibFriend;
    private ImageButton ibNotify;
    private ImageButton ibSetting;
    private ImageButton ibMessage;

    int x = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainSelectionModels = new ArrayList<>();
        ibHome = (ImageButton)findViewById(R.id.ibHome);
        ibFriend = (ImageButton)findViewById(R.id.ibFriend);
        ibNotify = (ImageButton)findViewById(R.id.ibNotify);
        ibSetting = (ImageButton)findViewById(R.id.ibSetting);
        ibMessage = (ImageButton)findViewById(R.id.ibMessage);

        mainSelectionModels.add(new MainSelectionModel(MainSelectionEnum.Home, ibHome, R.mipmap.ic_home_72_line));
        mainSelectionModels.add(new MainSelectionModel(MainSelectionEnum.Friend, ibFriend, R.mipmap.ic_friend_72_line));
        mainSelectionModels.add(new MainSelectionModel(MainSelectionEnum.Notify, ibNotify, R.mipmap.ic_bell_72_line));
        mainSelectionModels.add(new MainSelectionModel(MainSelectionEnum.Setting, ibSetting, R.mipmap.ic_setting_72_light));

        onClickHome();
        chosen = MainSelectionEnum.Home;
        ibHome.setImageResource(R.mipmap.ic_home_72_full);

        ibHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainSelectionModel oldMainSelectionModel = mainSelectionModels.get(chosen.ordinal());
                if(oldMainSelectionModel.getId() == MainSelectionEnum.Home)
                    return;

                chosen = MainSelectionEnum.Home;
                ibHome.setImageResource(R.mipmap.ic_home_72_full);
                oldMainSelectionModel.getIb().setImageResource(oldMainSelectionModel.getImg());

                onClickHome();
            }
        });

        ibFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainSelectionModel oldMainSelectionModel = mainSelectionModels.get(chosen.ordinal());
                if(oldMainSelectionModel.getId() == MainSelectionEnum.Friend)
                    return;

                chosen = MainSelectionEnum.Friend;
                ibFriend.setImageResource(R.mipmap.ic_friend_72_full);
                oldMainSelectionModel.getIb().setImageResource(oldMainSelectionModel.getImg());

                onClickFriend();
            }
        });

        ibNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainSelectionModel oldMainSelectionModel = mainSelectionModels.get(chosen.ordinal());
                if(oldMainSelectionModel.getId() == MainSelectionEnum.Notify)
                    return;
                
                chosen = MainSelectionEnum.Notify;
                ibNotify.setImageResource(R.mipmap.ic_bell_72_full);
                oldMainSelectionModel.getIb().setImageResource(oldMainSelectionModel.getImg());

                onClickNotify();
            }
        });

        ibSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainSelectionModel oldMainSelectionModel = mainSelectionModels.get(chosen.ordinal());
                if(oldMainSelectionModel.getId() == MainSelectionEnum.Setting)
                    return;

                chosen = MainSelectionEnum.Setting;
                ibSetting.setImageResource(R.mipmap.ic_setting_72_dark);
                oldMainSelectionModel.getIb().setImageResource(oldMainSelectionModel.getImg());

                onClickSetting();
            }
        });


        ibMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMessage();
            }
        });
    }

    private void onClickHome(){
        List<HomeModel> homeModels = new ArrayList<>();
        List<PostModel> postModels = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            if(i % 2 == 0){
                postModels.add(new PostModel(
                        R.mipmap.ic_user_72_dark,
                        "Jonhny Deep",
                        "23:59 25-02-2024",
                        R.mipmap.ic_global_72_dark,
                        "Hôm nay trời đẹp quá",
                        0,
                        false
                ));
            }
            else {
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
        }

        homeModels.add(new HomeModel(postModels));
        RecyclerView recyclerView = findViewById(R.id.rvMainArea);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new HomeAdapter(getApplicationContext(), homeModels));
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

    private void onClickNotify(){
        List<NotifyModel> notifyModels = new ArrayList<>();
        List<NotifyCardModel> notifyCardModelTodays = new ArrayList<>();
        List<NotifyCardModel> notifyCardModel3DaysAgos = new ArrayList<>();
        List<String> contents = new ArrayList<>();
        contents.add("đã thích bài viết của bạn");
        contents.add("đã chia sẽ bài viết của bạn");
        contents.add("đã mời bạn tham gia nhóm Hai Con Mèo Màu Xanh Lá Cây");
        contents.add("đã gửi yêu cầu kết bạn đến bạn");
        contents.add("đã bình luận vào bào viết của bạn");

        for (int i = 0; i < x; i++) {
            Random random = new Random();
            notifyCardModelTodays.add(new NotifyCardModel(
                    R.mipmap.ic_user_72_dark,
                    "Jonhny Deep",
                    contents.get(random.nextInt(contents.size())),
                    "23:59 28-02-2024"
            ));
        }

        for (int i = 0; i < x; i++) {
            Random random = new Random();
            notifyCardModel3DaysAgos.add(new NotifyCardModel(
                    R.mipmap.ic_user_72_dark,
                    "Jonhny Deep",
                    contents.get(random.nextInt(contents.size())),
                    "23:59 25-02-2024"
            ));
        }

        notifyModels.add(new NotifyModel("Today", notifyCardModelTodays));
        notifyModels.add(new NotifyModel("3 days ago", notifyCardModel3DaysAgos));

        RecyclerView recyclerView = findViewById(R.id.rvMainArea);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NotifyAdapter(getApplicationContext(), notifyModels));
    }

    private void onClickSetting(){
        List<SettingModel> settingModels = new ArrayList<>();
        List<SettingCardModel> settingCardModels = new ArrayList<>();

        settingCardModels.add(new SettingCardModel(R.mipmap.ic_admin_72_dark, "Admin"));
        settingModels.add(new SettingModel(R.mipmap.ic_user_72_dark, "Jonhny Deep", settingCardModels));

        RecyclerView recyclerView = findViewById(R.id.rvMainArea);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SettingAdapter(getApplicationContext(), settingModels));
    }

    private void onClickMessage(){
        Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("string", "idx1010");
        intent.putExtras(bundle);

        startForResult.launch(intent);
    }
}