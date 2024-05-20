package HCMUTE.SocialMedia.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.BanAccountAdapter;
import HCMUTE.SocialMedia.Adapters.ReportPostAdapter;
import HCMUTE.SocialMedia.Models.BanAccountModel;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.ReportPostModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminProcessReportActivity extends AppCompatActivity {

    private List<ReportPostModel> reportPostModels;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_process_report);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ivBack = findViewById(R.id.ibBack);
        ivBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getReport();
    }

    private void getReport(){
        if (reportPostModels == null) reportPostModels = new ArrayList<>();
        reportPostModels.clear();

        APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAllReports().enqueue(new Callback<ResponseModel<ReportPostModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<ReportPostModel>> call, Response<ResponseModel<ReportPostModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<ReportPostModel> responseModel = response.body();
                    if (responseModel != null && responseModel.isSuccess()) {
                        List<ReportPostModel> responseModelResult = responseModel.getResult();
                        reportPostModels.addAll(responseModelResult);
                        Log.d("XXX", "onResponse: " + reportPostModels.size());
                    }

                    RecyclerView recyclerView = (RecyclerView) AdminProcessReportActivity.this.findViewById(R.id.rvProcessReport);
                    recyclerView.setLayoutManager(new LinearLayoutManager(AdminProcessReportActivity.this.getApplicationContext()));
                    recyclerView.setAdapter(new ReportPostAdapter(AdminProcessReportActivity.this.getApplicationContext(), reportPostModels));

                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code

                }
            }

            @Override
            public void onFailure(Call<ResponseModel<ReportPostModel>> call, Throwable t) {

            }
        });
    }
}