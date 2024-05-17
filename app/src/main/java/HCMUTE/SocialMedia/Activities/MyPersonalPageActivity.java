package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
    private ImageButton ibBack;
    private LinearLayout llSearch;
    private RecyclerView recyclerView;
    private APIService apiService;
    private MyPersonalPageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_personal_page);
        ibBack = findViewById(R.id.ibBack);
        llSearch = findViewById(R.id.llSearch);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPersonalPageActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        getProfile("abc");
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
                            PostCardModel post = new PostCardModel();
                            post.setPostId(p.getPostId());
                            post.setAvatar(p.getAvatar());
                            post.setUsername(p.getUsername());
                            post.setFullName(p.getFullName());
                            post.setPostingTimeAt(p.getPostingTimeAt());
                            post.setMode(R.mipmap.ic_global_72_dark);
                            post.setPostText(p.getPostText());
                            post.setLiked(p.isLiked());
                            postCardModels.add(post);
                            if (postCardModels.size() > 6){
                                break;
                            }
                        }
                        recyclerView = findViewById(R.id.rvMyPersonalPageArea);
                        adapter = new MyPersonalPageAdapter(getApplicationContext(), model, yourFriendModels, postCardModels);
                        adapter.setOnEditProfileClickListener(new MyPersonalPageAdapter.OnEditProfileClickListener() {
                            @Override
                            public void onEditProfileClick() {
                                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                                startActivity(intent);
                            }
                        });
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
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
}