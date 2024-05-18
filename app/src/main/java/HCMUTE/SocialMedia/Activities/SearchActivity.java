package HCMUTE.SocialMedia.Activities;

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import HCMUTE.SocialMedia.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
=======
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.SeachAdapter;
import HCMUTE.SocialMedia.Consts.Const;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.Models.SearchModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private ImageButton ibBack;
    private EditText etKeyword;
    private ImageButton ibSearch;
    private List<SearchModel> searchModels;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        searchModels = new ArrayList<>();
        ibBack = findViewById(R.id.ibBack);
        etKeyword = findViewById(R.id.etKeyword);
        ibSearch = findViewById(R.id.ibSearch);

        ibBack.setOnClickListener(v -> finish());
        ibSearch.setOnClickListener(v -> {
            String keyword = String.valueOf(etKeyword.getText()).trim();
            if (keyword.isEmpty()) return;

            searchModels.clear();
            APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
            apiService.search(Const.USERNAME, keyword).enqueue(new Callback<ResponseModel<SearchModel>>() {
                @Override
                public void onResponse(Call<ResponseModel<SearchModel>> call, Response<ResponseModel<SearchModel>> response) {
                    if (response.isSuccessful()) {
                        ResponseModel<SearchModel> responseModel = response.body();
                        if (responseModel != null && responseModel.isSuccess()) {
                            List<SearchModel> responseModelResult = responseModel.getResult();
                            searchModels.addAll(responseModelResult);

                        }

                        RecyclerView recyclerView = (RecyclerView) SearchActivity.this.findViewById(R.id.rvSearch);
                        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this.getApplicationContext()));
                        recyclerView.setAdapter(new SeachAdapter(SearchActivity.this.getApplicationContext(), searchModels));

                    } else {
                        int statusCode = response.code();
                        // handle request errors depending on status code

                    }
                }

                @Override
                public void onFailure(Call<ResponseModel<SearchModel>> call, Throwable t) {
                }
            });
        });

>>>>>>> origin/Review
    }
}