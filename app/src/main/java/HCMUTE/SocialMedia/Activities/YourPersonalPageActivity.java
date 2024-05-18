package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.MyPersonalPageAdapter;
import HCMUTE.SocialMedia.Adapters.YourPersonalPageAdapter;
import HCMUTE.SocialMedia.Consts.Const;
import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Responses.SimpleResponse;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourPersonalPageActivity extends AppCompatActivity {
    private ImageButton ibBack;
    private LinearLayout llSearch;
    private RecyclerView recyclerView;
    private APIService apiService;
    private YourPersonalPageAdapter adapter;
    private String friend_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_personal_page);
        Intent intent = getIntent();
        if (intent != null){
            friend_username = intent.getStringExtra("YOUR_FRIEND_USERNAME");
        }
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
                Intent intent = new Intent(YourPersonalPageActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        getProfile(Const.USERNAME, friend_username);
    }
    private void getProfile(String username, String friendUsername) {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getFriendAccountByUsername(username, friendUsername).enqueue(new Callback<SimpleResponse<AccountCardModel>>() {
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
                            if (!yourFriend.getUsername().equals(Const.USERNAME)){
                                yourFriendModels.add(yourFriend);
                            }
                        }
                        List<PostCardModel> postCardModels = new ArrayList<>();
                        for (PostCardModel p: model.getPosts()) {
                            PostCardModel post = new PostCardModel();
                            post.setPostId(p.getPostId());
                            post.setAvatar(p.getAvatar());
                            post.setUsername(p.getUsername());
                            post.setFullName(p.getFullName());
                            post.setPostMedia(p.getPostMedia());
                            post.setPostingTimeAt(p.getPostingTimeAt());
                            post.setMode(R.mipmap.ic_global_72_dark);
                            post.setPostText(p.getPostText());
                            post.setLiked(p.isLiked());
                            postCardModels.add(post);
                        }
                        recyclerView = findViewById(R.id.rvYourPersonalPageArea);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(YourPersonalPageActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(new YourPersonalPageAdapter(YourPersonalPageActivity.this, model, yourFriendModels, postCardModels));
                    }
                }
                else{
                    Toast.makeText(YourPersonalPageActivity.this, "An error occurred please try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse<AccountCardModel>> call, Throwable t) {
                Toast.makeText(YourPersonalPageActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}