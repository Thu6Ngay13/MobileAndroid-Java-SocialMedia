package HCMUTE.SocialMedia.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.MyPersonalPageAdapter;
import HCMUTE.SocialMedia.Adapters.PostAdapter;
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

public class GroupActivity extends AppCompatActivity {

    private ImageButton ibBack;
    private ImageButton ibCreateGroup;
    private RecyclerView recyclerView;
    private APIService apiService;
    private MyPersonalPageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        ibBack = findViewById(R.id.ibBack);
        ibCreateGroup = findViewById(R.id.ibCreateGroup);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ibCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        loadData(Const.USERNAME);
    }

    private AccountCardModel loadData(String username) {
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
                            if (yourFriendModels.size() > 6){
                                break;
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
                        recyclerView = findViewById(R.id.rvMyPersonalPageArea);
                        adapter = new MyPersonalPageAdapter(GroupActivity.this, model, yourFriendModels, postCardModels);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(GroupActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    }
                }
                else{
                    Toast.makeText(GroupActivity.this, "An error occurred please try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse<AccountCardModel>> call, Throwable t) {
                Toast.makeText(GroupActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

}