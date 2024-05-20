package HCMUTE.SocialMedia.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.CommentCardAdapter;
import HCMUTE.SocialMedia.Adapters.GroupCardAdapter;
import HCMUTE.SocialMedia.Adapters.MyPersonalPageAdapter;
import HCMUTE.SocialMedia.Adapters.PostAdapter;
import HCMUTE.SocialMedia.Adapters.PostGroupAdapter;
import HCMUTE.SocialMedia.Consts.Const;
import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.Models.CommentCardModel;
import HCMUTE.SocialMedia.Models.GroupModel;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Responses.SimpleResponse;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupActivity extends AppCompatActivity {

    private ImageButton ibBack;
    private ImageButton ibCreateGroup;
    private RecyclerView recyclerView;
    private APIService apiService;
    private Button btPostGroup, btMyGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        ibBack = findViewById(R.id.ibBack);
        ibCreateGroup = findViewById(R.id.ibCreateGroup);
        btPostGroup = findViewById(R.id.btPostGroup);
        btMyGroup = findViewById(R.id.btMyGroup);

        recyclerView = findViewById(R.id.rvGroupArea);
        loadPosts(PrefManager.getUsername());
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ibCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupActivity.this, CreateGroupActivity.class);
                startActivity(intent);
            }
        });

        btPostGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData(PrefManager.getUsername());
            }
        });

        btMyGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMyGroups(PrefManager.getUsername());
            }
        });
        loadData(PrefManager.getUsername());
    }
    private void loadData(String username) {
        final List<PostCardModel> postCardModels = new ArrayList<>();
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getPostInGroupsByUsername(username).enqueue(new Callback<ResponseModel<PostCardModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<PostCardModel>> call, Response<ResponseModel<PostCardModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<PostCardModel> responseModel = response.body();
                    List<PostCardModel> responseModelResult = new ArrayList<>();
                    if (responseModel != null && responseModel.isSuccess()) {
                        responseModelResult = responseModel.getResult();
                        postCardModels.addAll(responseModelResult);
                        recyclerView.setLayoutManager(new LinearLayoutManager(GroupActivity.this.getApplicationContext()));
                        recyclerView.setAdapter(new PostGroupAdapter(GroupActivity.this.getApplicationContext(), postCardModels));
                    }
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

    private void loadPosts(String username) {
        final List<PostCardModel> postCardModels = new ArrayList<>();
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getPostInGroupsByUsername(username).enqueue(new Callback<ResponseModel<PostCardModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<PostCardModel>> call, Response<ResponseModel<PostCardModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<PostCardModel> responseModel = response.body();
                    List<PostCardModel> responseModelResult = new ArrayList<>();
                    if (responseModel != null && responseModel.isSuccess()) {
                        responseModelResult = responseModel.getResult();
                        postCardModels.addAll(responseModelResult);
                        recyclerView.setLayoutManager(new LinearLayoutManager(GroupActivity.this.getApplicationContext()));
                        recyclerView.setAdapter(new PostGroupAdapter(GroupActivity.this.getApplicationContext(), postCardModels));
                    }
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

    private void loadMyGroups(String username) {
        final List<GroupModel> groupCardModels = new ArrayList<>();
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getGroupsByUsername(username).enqueue(new Callback<ResponseModel<GroupModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<GroupModel>> call, Response<ResponseModel<GroupModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<GroupModel> responseModel = response.body();
                    List<GroupModel> responseModelResult = new ArrayList<>();
                    if (responseModel != null && responseModel.isSuccess()) {
                        responseModelResult = responseModel.getResult();
                        groupCardModels.addAll(responseModelResult);
                        recyclerView.setLayoutManager(new LinearLayoutManager(GroupActivity.this.getApplicationContext()));
                        recyclerView.setAdapter(new GroupCardAdapter(GroupActivity.this.getApplicationContext(), groupCardModels));
                    }
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<GroupModel>> call, Throwable t) {
            }
        });
    }


}