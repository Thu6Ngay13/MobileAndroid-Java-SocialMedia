package HCMUTE.SocialMedia.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.ConversationCardAdapter;
import HCMUTE.SocialMedia.Consts.Const;
import HCMUTE.SocialMedia.Models.ConversationCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationActivity extends AppCompatActivity {

    private ImageButton ibBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        ibBack = findViewById(R.id.ibBack);
        ibBack.setOnClickListener(v -> finish());

        final List<ConversationCardModel> conversationCards = new ArrayList<>();

        APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getConversationsWithUsername(PrefManager.getUsername()).enqueue(new Callback<ResponseModel<ConversationCardModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<ConversationCardModel>> call, Response<ResponseModel<ConversationCardModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<ConversationCardModel> responseModel = response.body();
                    if (responseModel != null && responseModel.isSuccess()) {
                        List<ConversationCardModel> responseModelResult = responseModel.getResult();
                        conversationCards.addAll(responseModelResult);
                    }

                    RecyclerView recyclerView = (RecyclerView) ConversationActivity.this.findViewById(R.id.rvConversationArea);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ConversationActivity.this.getApplicationContext()));
                    recyclerView.setAdapter(new ConversationCardAdapter(ConversationActivity.this.getApplicationContext(), conversationCards));

                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<ConversationCardModel>> call, Throwable t) {
            }
        });

    }
}