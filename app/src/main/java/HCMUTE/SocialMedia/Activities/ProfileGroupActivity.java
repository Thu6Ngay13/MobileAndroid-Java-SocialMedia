package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.MyPersonalPageAdapter;
import HCMUTE.SocialMedia.Adapters.PostAdapter;
import HCMUTE.SocialMedia.Adapters.ProfileGroupAdapter;
import HCMUTE.SocialMedia.Models.AccountCardModel;
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

public class ProfileGroupActivity extends AppCompatActivity {
    private SwipeRefreshLayout slProfileGroup;
    private ImageButton ibBack;
    private LinearLayout llSearch;
    private RecyclerView recyclerView;
    private APIService apiService;
    private ProfileGroupAdapter adapter;
    GroupModel groupModel = new GroupModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_group);
        ibBack = findViewById(R.id.ibBack);
        llSearch = findViewById(R.id.llSearch);

        slProfileGroup = findViewById(R.id.slProfileGroup);
        slProfileGroup.setOnRefreshListener(() -> {
            slProfileGroup.setRefreshing(true);
            reloadActivity();
        });
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileGroupActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            groupModel.setGroupId(bundle.getLong("GROUP_GROUPID"));
            groupModel.setGroupName(bundle.getString("GROUP_GROUPNAME"));
            groupModel.setAvatarURL(bundle.getString("GROUP_AVATARURL"));
            groupModel.setDescription(bundle.getString("GROUP_DESCRIPSION"));
            groupModel.setModeId(bundle.getLong("GROUP_MODEID"));
            groupModel.setCreationTimeAt(bundle.getString("GROUP_TIME"));
            groupModel.setHolderUsername(bundle.getString("GROUP_USERNAME"));
            groupModel.setHolderFullName(bundle.getString("GROUP_FULLNAME"));
            Log.d("", groupModel.toString());
        }
        getProfile(groupModel.getGroupId());
    }

    private void reloadActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void getProfile(Long groupId) {
        Log.d("", groupId.toString());
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        final List<PostCardModel> postCardModels = new ArrayList<>();
        apiService.getPostsInGroup(PrefManager.getUsername(),  groupId).enqueue(new Callback<ResponseModel<PostCardModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<PostCardModel>> call, Response<ResponseModel<PostCardModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<PostCardModel> responseModel = response.body();
                    List<PostCardModel> responseModelResult = new ArrayList<>();
                    if (responseModel != null && responseModel.isSuccess()) {
                        responseModelResult = responseModel.getResult();
                        postCardModels.addAll(responseModelResult);
                        if (!postCardModels.isEmpty()) {
                            Log.d("First Element", postCardModels.get(0).toString());
                        } else {
                            Log.d("List Check", "postCardModels is empty");
                        }


                    }
                } else {
                    int statusCode = response.code();
                    Log.d("", "loi");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<PostCardModel>> call, Throwable t) {
                Log.d("", "loiloi");
            }
        });

        final List<YourFriendModel> accountCardModels = new ArrayList<>();
        apiService.getMembersInGroup( groupId).enqueue(new Callback<ResponseModel<YourFriendModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<YourFriendModel>> call, Response<ResponseModel<YourFriendModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<YourFriendModel> responseModel = response.body();
                    List<YourFriendModel> responseModelResult = new ArrayList<>();
                    if (responseModel != null && responseModel.isSuccess()) {
                        responseModelResult = responseModel.getResult();
                        accountCardModels.addAll(responseModelResult);
                        if (!accountCardModels.isEmpty()) {
                            Log.d("First Element", accountCardModels.get(0).toString());
                        } else {
                            Log.d("List Check", "postCardModels is empty");
                        }
                        recyclerView = findViewById(R.id.rvProfileGroupArea);
                        adapter = new ProfileGroupAdapter(ProfileGroupActivity.this, groupModel, accountCardModels, postCardModels);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(ProfileGroupActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<YourFriendModel>> call, Throwable t) {
            }
        });

    }
}
