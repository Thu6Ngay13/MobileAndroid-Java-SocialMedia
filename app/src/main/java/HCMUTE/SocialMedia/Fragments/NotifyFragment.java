package HCMUTE.SocialMedia.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.NotifyWithTimeAdapter;
import HCMUTE.SocialMedia.Consts.Const;
import HCMUTE.SocialMedia.Enums.TypeMessageEnum;
import HCMUTE.SocialMedia.Models.MessageModel;
import HCMUTE.SocialMedia.Models.NotifyCardModel;
import HCMUTE.SocialMedia.Models.NotifyWithTimeModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.RealTime.SocketIO;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;
import HCMUTE.SocialMedia.Utils.ProcessTime;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifyFragment extends Fragment {
    public NotifyFragment() {
    }

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
        apiService.getNotificationReceiptsWithUsername(PrefManager.getUsername()).enqueue(new Callback<ResponseModel<NotifyCardModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<NotifyCardModel>> call, Response<ResponseModel<NotifyCardModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<NotifyCardModel> responseModel = response.body();
                    List<NotifyCardModel> notifyCardModels = null;
                    if (responseModel != null && responseModel.isSuccess()) {
                        notifyCardModels = responseModel.getResult();
                    }

                    if (notifyCardModels != null) {
                        for (int i = 0; i < notifyCardModels.size(); i++) {
                            String time = notifyCardModels.get(i).getNotifyTimeAt();
                            if (ProcessTime.getDurationWithNowInSecond(time) < 4320) {
                                notifyCardModelTodays.add(notifyCardModels.get(i));
                            } else {
                                notifyCardModel3DaysAgos.add(notifyCardModels.get(i));
                            }
                        }
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