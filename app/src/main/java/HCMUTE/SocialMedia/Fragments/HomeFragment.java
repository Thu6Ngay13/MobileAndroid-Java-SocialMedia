package HCMUTE.SocialMedia.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.PostAdapter;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    public HomeFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<PostCardModel> postCardModels = new ArrayList<>();

        //Goi Interface trong APIService
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getPostsWithUsername("binhbinh").enqueue(new Callback<ResponseModel<PostCardModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<PostCardModel>> call, Response<ResponseModel<PostCardModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<PostCardModel> responseModel = response.body();
                    if (responseModel != null && responseModel.isSuccess()) {
                        List<PostCardModel> responseModelResult = responseModel.getResult();
                        postCardModels.addAll(responseModelResult);
                    }

                    RecyclerView recyclerView = view.findViewById(R.id.rvPostArea);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(new PostAdapter(getContext(), postCardModels));

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
}
