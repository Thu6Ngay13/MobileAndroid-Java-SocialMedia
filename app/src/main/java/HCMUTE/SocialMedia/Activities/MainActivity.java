package HCMUTE.SocialMedia.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.FriendAdapter;
import HCMUTE.SocialMedia.Adapters.HomeAdapter;
import HCMUTE.SocialMedia.Adapters.NotifyAdapter;
import HCMUTE.SocialMedia.Adapters.SettingAdapter;
import HCMUTE.SocialMedia.Enums.MainSelectionEnum;
import HCMUTE.SocialMedia.Models.FriendModel;
import HCMUTE.SocialMedia.Models.FriendRequestModel;
import HCMUTE.SocialMedia.Models.HomeModel;
import HCMUTE.SocialMedia.Models.MainSelectionModel;
import HCMUTE.SocialMedia.Models.NotifyCardModel;
import HCMUTE.SocialMedia.Models.NotifyModel;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.Models.SettingCardModel;
import HCMUTE.SocialMedia.Models.SettingModel;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 999;
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
        ibHome = findViewById(R.id.ibHome);
        ibFriend = findViewById(R.id.ibFriend);
        ibNotify = findViewById(R.id.ibNotify);
        ibSetting = findViewById(R.id.ibSetting);
        ibMessage = findViewById(R.id.ibMessage);

        mainSelectionModels.add(new MainSelectionModel(MainSelectionEnum.Home, ibHome, R.mipmap.ic_home_72_line));
        mainSelectionModels.add(new MainSelectionModel(MainSelectionEnum.Friend, ibFriend, R.mipmap.ic_friend_72_line));
        mainSelectionModels.add(new MainSelectionModel(MainSelectionEnum.Notify, ibNotify, R.mipmap.ic_bell_72_line));
        mainSelectionModels.add(new MainSelectionModel(MainSelectionEnum.Setting, ibSetting, R.mipmap.ic_setting_72_light));

        onClickHome();
        chosen = MainSelectionEnum.Home;
        ibHome.setImageResource(R.mipmap.ic_home_72_full);

        ibHome.setOnClickListener(v -> {
            MainSelectionModel oldMainSelectionModel = mainSelectionModels.get(chosen.ordinal());
            if(oldMainSelectionModel.getId() == MainSelectionEnum.Home)
                return;

            chosen = MainSelectionEnum.Home;
            ibHome.setImageResource(R.mipmap.ic_home_72_full);
            oldMainSelectionModel.getIb().setImageResource(oldMainSelectionModel.getImg());

            onClickHome();
        });

        ibFriend.setOnClickListener(v -> {

            MainSelectionModel oldMainSelectionModel = mainSelectionModels.get(chosen.ordinal());
            if(oldMainSelectionModel.getId() == MainSelectionEnum.Friend)
                return;

            chosen = MainSelectionEnum.Friend;
            ibFriend.setImageResource(R.mipmap.ic_friend_72_full);
            oldMainSelectionModel.getIb().setImageResource(oldMainSelectionModel.getImg());

            onClickFriend();
        });

        ibNotify.setOnClickListener(v -> {
            MainSelectionModel oldMainSelectionModel = mainSelectionModels.get(chosen.ordinal());
            if(oldMainSelectionModel.getId() == MainSelectionEnum.Notify)
                return;

            chosen = MainSelectionEnum.Notify;
            ibNotify.setImageResource(R.mipmap.ic_bell_72_full);
            oldMainSelectionModel.getIb().setImageResource(oldMainSelectionModel.getImg());

            onClickNotify();
        });

        ibSetting.setOnClickListener(v -> {
            MainSelectionModel oldMainSelectionModel = mainSelectionModels.get(chosen.ordinal());
            if(oldMainSelectionModel.getId() == MainSelectionEnum.Setting)
                return;

            chosen = MainSelectionEnum.Setting;
            ibSetting.setImageResource(R.mipmap.ic_setting_72_dark);
            oldMainSelectionModel.getIb().setImageResource(oldMainSelectionModel.getImg());

            onClickSetting();
        });


        ibMessage.setOnClickListener(v -> onClickMessage());
    }

    private void onClickHome(){
        List<HomeModel> homeModels = new ArrayList<>();
        List<PostCardModel> postCardModels = new ArrayList<>();

        //G0i Interface trong APIService
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getPostsWithUsername("binhbinh").enqueue(new Callback<ResponseModel<PostCardModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<PostCardModel>> call, Response<ResponseModel<PostCardModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<PostCardModel> responseModel = response.body();
                    if (responseModel != null && responseModel.isSuccess()){
                        List<PostCardModel> responseModelResult = responseModel.getResult();
                        postCardModels.addAll(responseModelResult);
                    }

                    homeModels.add(new HomeModel(postCardModels));
                    RecyclerView recyclerView = findViewById(R.id.rvMainArea);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(new HomeAdapter(getApplicationContext(), homeModels));
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<PostCardModel>> call, Throwable t) {

            }
        });


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

        //G0i Interface trong APIService
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getNotificationReceiptsWithUsername("binhbinh").enqueue(new Callback<ResponseModel<NotifyCardModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<NotifyCardModel>> call, Response<ResponseModel<NotifyCardModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<NotifyCardModel> responseModel = response.body();
                    if (responseModel != null && responseModel.isSuccess()){
                        List<NotifyCardModel> notifyCardModels = responseModel.getResult();
                        notifyCardModelTodays.addAll(notifyCardModels);
                    }

                    notifyModels.add(new NotifyModel("Today", notifyCardModelTodays));
                    notifyModels.add(new NotifyModel("3 days ago", notifyCardModel3DaysAgos));

                    RecyclerView recyclerView = findViewById(R.id.rvMainArea);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(new NotifyAdapter(getApplicationContext(), notifyModels));
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<NotifyCardModel>> call, Throwable t) {

            }
        });
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
        Intent intent = new Intent(getApplicationContext(), ConversationActivity.class);
        startActivity(intent);
    }
}