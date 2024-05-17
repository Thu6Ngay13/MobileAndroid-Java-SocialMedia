package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.CommentCardAdapter;
import HCMUTE.SocialMedia.Adapters.ConversationCardAdapter;
import HCMUTE.SocialMedia.Adapters.NotifyWithTimeAdapter;
import HCMUTE.SocialMedia.Models.CommentCardModel;
import HCMUTE.SocialMedia.Models.ConversationCardModel;
import HCMUTE.SocialMedia.Models.NotifyCardModel;
import HCMUTE.SocialMedia.Models.NotifyWithTimeModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {

    int x = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        final List<CommentCardModel> commentCardModels = new ArrayList<>();

        APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getCommentWithPostId((long)1).enqueue(new Callback<ResponseModel<CommentCardModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<CommentCardModel>> call, Response<ResponseModel<CommentCardModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<CommentCardModel> responseModel = response.body();
                    if (responseModel != null && responseModel.isSuccess()) {
                        List<CommentCardModel> responseModelResult = responseModel.getResult();
                        commentCardModels.addAll(responseModelResult);
                    }

                    RecyclerView recyclerView = (RecyclerView) CommentActivity.this.findViewById(R.id.rvConversationArea);
                    recyclerView.setLayoutManager(new LinearLayoutManager(CommentActivity.this.getApplicationContext()));
                    recyclerView.setAdapter(new CommentCardAdapter(CommentActivity.this.getApplicationContext(), commentCardModels));

                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<CommentCardModel>> call, Throwable t) {
            }
        });



    }
}