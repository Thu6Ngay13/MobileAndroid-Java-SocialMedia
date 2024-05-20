package HCMUTE.SocialMedia.Activities;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.BanAccountAdapter;
import HCMUTE.SocialMedia.Adapters.ConversationCardAdapter;
import HCMUTE.SocialMedia.Models.BanAccountModel;
import HCMUTE.SocialMedia.Models.ConversationCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminBanAccountActivity extends AppCompatActivity {

    private List<BanAccountModel> banAccountModels;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_ban_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ivBack = findViewById(R.id.ibBack);
        ivBack.setOnClickListener(v -> finish());

        banAccountModels = new ArrayList<>();
        APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAllAccount().enqueue(new Callback<ResponseModel<BanAccountModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<BanAccountModel>> call, Response<ResponseModel<BanAccountModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<BanAccountModel> responseModel = response.body();
                    if (responseModel != null && responseModel.isSuccess()) {
                        List<BanAccountModel> responseModelResult = responseModel.getResult();
                        banAccountModels.addAll(responseModelResult);
                    }

                    RecyclerView recyclerView = (RecyclerView) AdminBanAccountActivity.this.findViewById(R.id.rvBanAccount);
                    recyclerView.setLayoutManager(new LinearLayoutManager(AdminBanAccountActivity.this.getApplicationContext()));
                    recyclerView.setAdapter(new BanAccountAdapter(AdminBanAccountActivity.this.getApplicationContext(), banAccountModels));

                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<BanAccountModel>> call, Throwable t) {

            }
        });
    }
}