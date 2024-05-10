package HCMUTE.SocialMedia.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.NotifyWithTimeAdapter;
import HCMUTE.SocialMedia.Models.NotifyCardModel;
import HCMUTE.SocialMedia.Models.NotifyWithTimeModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifyFragment extends Fragment {
    public NotifyFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notify, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final List<NotifyWithTimeModel> notifyWithTimeModels = new ArrayList<>();
        final List<NotifyCardModel> notifyCardModelTodays = new ArrayList<>();
        final List<NotifyCardModel> notifyCardModel3DaysAgos = new ArrayList<>();

        //G0i Interface trong APIService
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getNotificationReceiptsWithUsername("binhbinh").enqueue(new Callback<ResponseModel<NotifyCardModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<NotifyCardModel>> call, Response<ResponseModel<NotifyCardModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<NotifyCardModel> responseModel = response.body();
                    if (responseModel != null && responseModel.isSuccess()){
                        List<NotifyCardModel> notifyCardModels = responseModel.getResult();
                        notifyCardModelTodays.addAll(notifyCardModels);
                    }

                    notifyWithTimeModels.add(new NotifyWithTimeModel("Today", notifyCardModelTodays));
                    notifyWithTimeModels.add(new NotifyWithTimeModel("3 days ago", notifyCardModel3DaysAgos));

                    RecyclerView recyclerView = view.findViewById(R.id.rvNotifyArea);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(new NotifyWithTimeAdapter(getContext(), notifyWithTimeModels));
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<NotifyCardModel>> call, Throwable t) {
            }
        });
    }
}