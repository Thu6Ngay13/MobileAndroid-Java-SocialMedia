package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import HCMUTE.SocialMedia.Holders.AcceptMemberInGroupHolder;
import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Responses.SimpleResponse;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptMemberInGroupAdapter extends RecyclerView.Adapter<AcceptMemberInGroupHolder>{
    private Context context;
    private List<AccountCardModel> accountModels;
    private long groupId;

    private APIService apiService;

    public AcceptMemberInGroupAdapter(Context context, List<AccountCardModel> accountModels, long groupId) {
        this.context = context;
        this.accountModels = accountModels;
        this.groupId = groupId;
    }

    @NonNull
    @Override
    public AcceptMemberInGroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AcceptMemberInGroupHolder(LayoutInflater.from(context).inflate(R.layout.accept_member_in_group_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AcceptMemberInGroupHolder holder, int position) {
        final AccountCardModel accountModel = accountModels.get(position);
        Glide.with(context)
                .load(accountModel.getAvatarURL())
                .into(holder.avatar);

        holder.holderFullName.setText(accountModel.getFullname());

        holder.btAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService = RetrofitClient.getRetrofit().create(APIService.class);
                final List<AccountCardModel> accountCardModels = new ArrayList<>();
                apiService.acceptMember(accountModel.getUsername(), groupId).enqueue(new Callback<SimpleResponse<String>>() {
                    @Override
                    public void onResponse(Call<SimpleResponse<String>> call, Response<SimpleResponse<String>> response) {
                        if (response.isSuccessful()) {
                            SimpleResponse<String> responseModel = response.body();
                            Toast.makeText(context,"Accept successful", Toast.LENGTH_LONG).show();
                        } else {
                            int statusCode = response.code();
                        }
                    }

                    @Override
                    public void onFailure(Call<SimpleResponse<String>> call, Throwable t) {
                    }
                });
            }
        });

        holder.btDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService = RetrofitClient.getRetrofit().create(APIService.class);
                final List<AccountCardModel> accountCardModels = new ArrayList<>();
                apiService.unjoinGroupByUsernameAndGroupId(accountModel.getUsername(), groupId).enqueue(new Callback<ResponseModel<String>>() {
                    @Override
                    public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                        if (response.isSuccessful()) {
                            ResponseModel<String> responseModel = response.body();
                            Toast.makeText(context,"Decline successful", Toast.LENGTH_LONG).show();
                        } else {
                            int statusCode = response.code();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if (accountModels != null)
            return accountModels.size();
        return 0;
    }
}
