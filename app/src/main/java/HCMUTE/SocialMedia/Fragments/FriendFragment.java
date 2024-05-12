package HCMUTE.SocialMedia.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.FriendAdapter;
import HCMUTE.SocialMedia.Models.FriendModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendFragment extends Fragment {
    private Button btRequestedFriend;
    private Button btYourFriend;
    private boolean yourFriendNow;

    public FriendFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btYourFriend = view.findViewById(R.id.btYourFriend);
        btRequestedFriend = view.findViewById(R.id.btRequestedFriend);
        onCickYourFriend(view, getContext());

        btYourFriend.setOnClickListener(v -> {
            if (yourFriendNow) return;
            onCickYourFriend(view, getContext());
        });

        btRequestedFriend.setOnClickListener(v -> {
            if (!yourFriendNow) return;
            onCickFriendRequest(view, getContext());
        });
    }

    private void onCickYourFriend(View view, Context context) {
        this.btYourFriend.setTextColor(ContextCompat.getColor(context, R.color.white));
        this.btYourFriend.setBackgroundColor(ContextCompat.getColor(context, R.color.green_dark));
        this.btRequestedFriend.setTextColor(ContextCompat.getColor(context, R.color.green_dark));
        this.btRequestedFriend.setBackgroundColor(ContextCompat.getColor(context, R.color.white));

        this.yourFriendNow = true;
        final List<FriendModel> FriendModels = new ArrayList<>();

        APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getYourFriendsWithUsername("vanE.12.31").enqueue(new Callback<ResponseModel<FriendModel>>() { // from class: HCMUTE.SocialMedia.Fragments.FriendFragment.1
            @Override
            public void onResponse(Call<ResponseModel<FriendModel>> call, Response<ResponseModel<FriendModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<FriendModel> responseModel = response.body();
                    if (responseModel != null && responseModel.isSuccess()) {
                        List<FriendModel> responseModelResult = responseModel.getResult();
                        FriendModels.addAll(responseModelResult);
                    }
                    RecyclerView recyclerView1 = (RecyclerView) view.findViewById(R.id.rvFriendArea);
                    recyclerView1.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView1.setAdapter(new FriendAdapter(context, FriendModels));
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<FriendModel>> call, Throwable t) {
            }
        });

    }

    private void onCickFriendRequest(View view, Context context) {
        this.btYourFriend.setTextColor(ContextCompat.getColor(context, R.color.green_dark));
        this.btYourFriend.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        this.btRequestedFriend.setTextColor(ContextCompat.getColor(context, R.color.white));
        this.btRequestedFriend.setBackgroundColor(ContextCompat.getColor(context, R.color.green_dark));

        this.yourFriendNow = false;
        final List<FriendModel> FriendModels = new ArrayList<>();

        APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getFriendRequestsWithUsername("vanE.12.31").enqueue(new Callback<ResponseModel<FriendModel>>() { // from class: HCMUTE.SocialMedia.Fragments.FriendFragment.2
            @Override
            public void onResponse(Call<ResponseModel<FriendModel>> call, Response<ResponseModel<FriendModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<FriendModel> responseModel = response.body();
                    if (responseModel != null && responseModel.isSuccess()) {
                        List<FriendModel> responseModelResult = responseModel.getResult();
                        FriendModels.addAll(responseModelResult);
                    }
                    RecyclerView recyclerView1 = (RecyclerView) view.findViewById(R.id.rvFriendArea);
                    recyclerView1.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView1.setAdapter(new FriendAdapter(context, FriendModels));
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<FriendModel>> call, Throwable t) {
            }
        });

    }
}