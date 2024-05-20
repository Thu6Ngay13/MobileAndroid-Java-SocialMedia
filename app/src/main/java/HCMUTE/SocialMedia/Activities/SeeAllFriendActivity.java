package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.SeeAllFriendAdapter;
import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Responses.SimpleResponse;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAllFriendActivity extends AppCompatActivity {
    private ImageView ivBack;
    private RecyclerView rvFriend;
    private APIService apiService;
    private SeeAllFriendAdapter adapter;
    private List<YourFriendModel> friends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_friend);
        String username = getIntent().getStringExtra("SEE_FRIEND");
        ivBack = findViewById(R.id.ivBack);
        rvFriend = findViewById(R.id.rvFriend);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getData(username);
    }

    private void getData(String username) {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAccountByUsername(username).enqueue(new Callback<SimpleResponse<AccountCardModel>>() {
            @Override
            public void onResponse(Call<SimpleResponse<AccountCardModel>> call, Response<SimpleResponse<AccountCardModel>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
                        SimpleResponse<AccountCardModel> simpleResponse = response.body();
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(SeeAllFriendActivity.this, 3);
                        rvFriend.setLayoutManager(gridLayoutManager);
                        AccountCardModel model = simpleResponse.getResult();
                        List<YourFriendModel> yourFriendModels = new ArrayList<>();
                        for (AccountCardModel a: model.getFriends()) {
                            YourFriendModel yourFriend = new YourFriendModel();
                            yourFriend.setAvatar(a.getAvatarURL());
                            yourFriend.setUsername(a.getUsername());
                            if (!a.getUsername().equals(PrefManager.getUsername())){
                                yourFriendModels.add(yourFriend);
                            }
                        }
                        adapter = new SeeAllFriendAdapter(SeeAllFriendActivity.this, yourFriendModels);
                        rvFriend.setAdapter(adapter);
                    }
                }
                else {
                    Toast.makeText(SeeAllFriendActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse<AccountCardModel>> call, Throwable t) {

            }
        });
    }
}