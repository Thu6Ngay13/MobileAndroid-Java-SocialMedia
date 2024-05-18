package HCMUTE.SocialMedia.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Activities.CreatePostActivity;
import HCMUTE.SocialMedia.Activities.LoginActivity;
import HCMUTE.SocialMedia.Activities.RegisterActivity;
import HCMUTE.SocialMedia.Adapters.PostAdapter;
import HCMUTE.SocialMedia.Consts.Const;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private List<PostCardModel> postCardModels;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private boolean isLoading = false;
    private int page = 0;
    private static final int pageSize = 5;

    private Button btnCreatePost;
    private Context context;

    public HomeFragment(Context context) {
        this.context = context;
    }

    public HomeFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postCardModels = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rvPostArea);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        postAdapter = new PostAdapter(getActivity(), postCardModels);
        recyclerView.setAdapter(postAdapter);

        btnCreatePost = view.findViewById(R.id.ibTextPosting);
        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreatePostActivity.class);
                startActivity(intent);
            }
        });
        nextPost();
        initScrollListener();
    }

    private void nextPost(){
        //Goi Interface trong APIService
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getPostOfNewFeedWithUsername(Const.USERNAME, page, pageSize).enqueue(new Callback<ResponseModel<PostCardModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<PostCardModel>> call, Response<ResponseModel<PostCardModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<PostCardModel> responseModel = response.body();
                    if (responseModel != null && responseModel.isSuccess()) {
                        List<PostCardModel> responseModelResult = responseModel.getResult();
                        postAdapter.addItems(responseModelResult, recyclerView);
                        page++;
                    }

                } else {
                    int statusCode = response.code();
                    Toast.makeText(context, "An error occurred please try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<PostCardModel>> call, Throwable t) {
            }
        });
    }

    private void initScrollListener(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == postCardModels.size()-1) {
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        postCardModels.add(null);
        postAdapter.notifyItemInserted(postCardModels.size()-1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                postCardModels.remove(postCardModels.size() - 1);
                int scrollPosition = postCardModels.size();
                postAdapter.notifyItemRemoved(scrollPosition);

                nextPost();
                isLoading = false;
            }
        }, 1000);
    }
}
