package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.AcceptMemberInGroupAdapter;
import HCMUTE.SocialMedia.Adapters.GroupCardAdapter;
import HCMUTE.SocialMedia.Adapters.ProfileGroupAdapter;
import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.Models.GroupModel;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptMemberInGroupActivity extends AppCompatActivity {
    private ImageButton ibBack;
    private LinearLayout llSearch;
    private RecyclerView recyclerView;
    private APIService apiService;
    private CircleImageView civAvatar;
    private TextView tvHolderFullName, tvGroupName;
    GroupModel groupModel = new GroupModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_member_in_group);
        ibBack = findViewById(R.id.ibBack);
        llSearch = findViewById(R.id.llSearch);
        civAvatar = findViewById(R.id.civAvatar);
        tvHolderFullName = findViewById(R.id.tvHolderFullName);
        tvGroupName = findViewById(R.id.tvGroupName);
        recyclerView = findViewById(R.id.rvAcceptMemberArea);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcceptMemberInGroupActivity.this, SearchActivity.class);
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
        Glide.with(getApplicationContext()).load(groupModel.getAvatarURL()).into(civAvatar);
        tvHolderFullName.setText(groupModel.getHolderFullName());
        tvGroupName.setText(groupModel.getGroupName());
        getAcceptMembers(groupModel.getGroupId());
    }
    private void getAcceptMembers(Long groupId) {
        Log.d("", groupId.toString());
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        final List<AccountCardModel> accountCardModels = new ArrayList<>();
        apiService.listAcceptMemberGroup( groupId).enqueue(new Callback<ResponseModel<AccountCardModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<AccountCardModel>> call, Response<ResponseModel<AccountCardModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<AccountCardModel> responseModel = response.body();
                    List<AccountCardModel> responseModelResult = new ArrayList<>();
                    if (responseModel != null && responseModel.isSuccess()) {
                        responseModelResult = responseModel.getResult();
                        accountCardModels.addAll(responseModelResult);

                        recyclerView.setLayoutManager(new LinearLayoutManager(AcceptMemberInGroupActivity.this.getApplicationContext()));
                        recyclerView.setAdapter(new AcceptMemberInGroupAdapter(AcceptMemberInGroupActivity.this.getApplicationContext(), accountCardModels,groupId));

                    }
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<AccountCardModel>> call, Throwable t) {
            }
        });

    }
}