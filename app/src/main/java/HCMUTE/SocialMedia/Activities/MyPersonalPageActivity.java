package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.MyPersonalPageAdapter;
import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Responses.AuthResponse;
import HCMUTE.SocialMedia.Responses.SimpleResponse;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPersonalPageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private APIService apiService;

    int x = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_personal_page);
        getProfile(PrefManager.getInstance(getApplicationContext()).getUsername());
        loadData();
    }

    private AccountCardModel getProfile(String username) {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAccountByUsername(username).enqueue(new Callback<SimpleResponse<AccountCardModel>>() {
            @Override
            public void onResponse(Call<SimpleResponse<AccountCardModel>> call, Response<SimpleResponse<AccountCardModel>> response) {
                if (response.isSuccessful()){
                    SimpleResponse<AccountCardModel> simpleResponse = response.body();
                    if (simpleResponse.getResult() != null){
                        AccountCardModel model = simpleResponse.getResult();
                        List<YourFriendModel> yourFriendModels = new ArrayList<>();
                        for (AccountCardModel a: model.getFriends()) {
                            YourFriendModel yourFriend = new YourFriendModel();
                            yourFriend.setAvatar(a.getAvatarURL());
                            yourFriend.setUsername(a.getUsername());
                            yourFriendModels.add(yourFriend);
                        }
                        List<PostCardModel> postCardModels = new ArrayList<>();
                        for (PostCardModel p: model.getPosts()) {

                        }
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
                        recyclerView = findViewById(R.id.rvMyPersonalPageArea);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(new MyPersonalPageAdapter(getApplicationContext(), model, yourFriendModels, postCardModels));
                    }
                }
                else{
                    Toast.makeText(MyPersonalPageActivity.this, "An error occurred please try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse<AccountCardModel>> call, Throwable t) {
                Toast.makeText(MyPersonalPageActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    private void loadData(){

    }
}