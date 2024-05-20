package HCMUTE.SocialMedia.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.ReportPostAdapter;
import HCMUTE.SocialMedia.Models.ReportPostModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.Utils.ProcessTime;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminViewReportPostActivity extends AppCompatActivity {

    private ImageButton ibBack;
    private ImageView ivAvatar;
    private TextView tvFullName;
    private TextView tvPostingTimeAt;
    private ImageView ivMenu;
    private ImageView ivMode;
    private TextView tvPostText;
    private ImageView ivPostImage;

    private long postId;
    private long groupId;
    private long mode;

    private String avatar;
    private String username;
    private String fullName;
    private String postingTimeAt;
    private String postText;
    private String postMedia;

    private List<ReportPostModel> reportPostModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_view_report_post);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        postId = intent.getLongExtra("postId", -1L);
        groupId = intent.getLongExtra("groupId", -1L);
        mode = intent.getLongExtra("mode", -1L);

        avatar = intent.getStringExtra("avatar");
        username = intent.getStringExtra("username");
        fullName = intent.getStringExtra("fullname");
        postingTimeAt = intent.getStringExtra("postingTimeAt");
        postText = intent.getStringExtra("postText");
        postMedia = intent.getStringExtra("postMedia");

        ibBack = findViewById(R.id.ibBack);
        ivAvatar = findViewById(R.id.ivAvatar);
        tvFullName = findViewById(R.id.tvFullName);
        tvPostingTimeAt = findViewById(R.id.tvPostingTimeAt);
        ivMenu = findViewById(R.id.ivMenu);
        ivMode = findViewById(R.id.ivMode);
        tvPostText = findViewById(R.id.tvPostText);
        ivPostImage = findViewById(R.id.ivPostImage);

        ibBack.setOnClickListener(v -> finish());
        tvFullName.setText(fullName);

        List<String> timeResult = ProcessTime.getTimeFromString(postingTimeAt);
        String timeShow = timeResult.get(0) + "-" + timeResult.get(1) + "-" + timeResult.get(2) + " " + timeResult.get(3) + ":" + timeResult.get(4);
        tvPostingTimeAt.setText(timeShow);
        tvPostText.setText(postText);

        Glide.with(AdminViewReportPostActivity.this.getApplicationContext())
                .load(avatar)
                .into(ivAvatar);
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminViewReportPostActivity.this.getApplicationContext(), YourPersonalPageActivity.class);
                intent.putExtra("YOUR_FRIEND_USERNAME", username);
                AdminViewReportPostActivity.this.getApplicationContext().startActivity(intent);
            }
        });

        Glide.with(AdminViewReportPostActivity.this.getApplicationContext())
                .load(postMedia)
                .into(ivPostImage);
        if (mode == 1) {
            ivMode.setImageResource(R.mipmap.ic_global_72_dark);
        } else if (mode == 2) {
            ivMode.setImageResource(R.mipmap.ic_friend_72_full);
        } else if (mode == 3) {
            ivMode.setImageResource(R.mipmap.ic_lock_72_dark);
        } else {
            ivMode.setImageResource(R.mipmap.ic_user_groups_72_full);
        }

        reportPostModels = new ArrayList<>();
        APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAllReportsWithPostId(postId).enqueue(new Callback<ResponseModel<ReportPostModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<ReportPostModel>> call, Response<ResponseModel<ReportPostModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<ReportPostModel> responseModel = response.body();
                    if (responseModel != null && responseModel.isSuccess()) {
                        List<ReportPostModel> responseModelResult = responseModel.getResult();
                        reportPostModels.addAll(responseModelResult);
                    }

                    RecyclerView recyclerView = (RecyclerView) AdminViewReportPostActivity.this.findViewById(R.id.rvAllReport);
                    recyclerView.setLayoutManager(new LinearLayoutManager(AdminViewReportPostActivity.this.getApplicationContext()));
                    recyclerView.setAdapter(new ReportPostAdapter(AdminViewReportPostActivity.this.getApplicationContext(), reportPostModels));

                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code

                }
            }

            @Override
            public void onFailure(Call<ResponseModel<ReportPostModel>> call, Throwable t) {

            }
        });

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(AdminViewReportPostActivity.this.getApplicationContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_admin_report_post, popupMenu.getMenu());

        MenuItem iBanAccount = popupMenu.getMenu().findItem(R.id.iBanAccount);
        MenuItem iDeletePost = popupMenu.getMenu().findItem(R.id.iDeletePost);
        MenuItem iIgnoreAllReport = popupMenu.getMenu().findItem(R.id.iIgnoreAllReport);

        iBanAccount.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {

                APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                apiService.banAccount(username).enqueue(new Callback<ResponseModel<String>>() {
                    @Override
                    public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                        if (response.isSuccessful()) {
                            ResponseModel<String> responseModel = response.body();
                            if (responseModel.isSuccess()){
                                finish();
                            }
                        } else {
                            int statusCode = response.code();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                    }
                });
                return false;
            }
        });

        iDeletePost.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {

                APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                apiService.deletePost(postId).enqueue(new Callback<ResponseModel<String>>() {
                    @Override
                    public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                        if (response.isSuccessful()) {
                            ResponseModel<String> responseModel = response.body();
                            if (responseModel.isSuccess()){
                                finish();
                            }
                        } else {
                            int statusCode = response.code();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                    }
                });
                return false;
            }
        });

        iIgnoreAllReport.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {

                APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                apiService.ignoreAllReportOfPost(postId).enqueue(new Callback<ResponseModel<String>>() {
                    @Override
                    public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                        if (response.isSuccessful()) {
                            ResponseModel<String> responseModel = response.body();
                            if (responseModel.isSuccess()){
                                finish();
                            }
                        } else {
                            int statusCode = response.code();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                    }
                });
                return false;
            }
        });

        popupMenu.show();
    }
}