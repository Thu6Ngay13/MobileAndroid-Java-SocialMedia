package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import HCMUTE.SocialMedia.Enums.TypeBanAccountEnum;
import HCMUTE.SocialMedia.Holders.BanAccountHolder;
import HCMUTE.SocialMedia.Models.BanAccountModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BanAccountAdapter extends RecyclerView.Adapter<BanAccountHolder> {
    private Context context;
    private List<BanAccountModel> banAccounts;

    public BanAccountAdapter(Context context, List<BanAccountModel> banAccounts) {
        this.context = context;
        this.banAccounts = banAccounts;
    }

    @Override
    public BanAccountHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BanAccountHolder(LayoutInflater.from(this.context).inflate(R.layout.ban_account_view, parent, false));
    }

    @Override
    public void onBindViewHolder(BanAccountHolder holder, int position) {
        BanAccountModel banAccount = this.banAccounts.get(position);
        Glide.with(this.context).load(banAccount.getAvatar()).into(holder.avatar);
        holder.fullName.setText(banAccount.getFullname());

        Button btDoSomething = holder.itemView.findViewById(R.id.btDoSomething);
        if (banAccount.getViewType() == TypeBanAccountEnum.IS_BANNED) {
            btDoSomething.setText("Unban");
            btDoSomething.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                    apiService.unbanAccount(banAccount.getUsername()).enqueue(new Callback<ResponseModel<String>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                            if (response.isSuccessful()) {
                                ResponseModel<String> responseModel = response.body();
                                if (responseModel != null && responseModel.isSuccess()){
                                    List<String> Strings = responseModel.getResult();

                                    String[] fullname = banAccount.getFullname().split(" ");
                                    Toast.makeText(context, "You have ban " + fullname[fullname.length - 1], Toast.LENGTH_SHORT).show();

                                    btDoSomething.setText("Ban");
                                    banAccount.setViewType(TypeBanAccountEnum.IS_NOT_BANNED);
                                    banAccount.setBanned(false);
                                    notifyItemChanged(position);
                                }
                            } else {
                                int statusCode = response.code();
                                // handle request errors depending on status code
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                        }
                    });
                }
            });
        }
        else if (banAccount.getViewType() == TypeBanAccountEnum.IS_NOT_BANNED) {
            btDoSomething.setText("Ban");
            btDoSomething.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                    apiService.banAccount(banAccount.getUsername()).enqueue(new Callback<ResponseModel<String>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                            if (response.isSuccessful()) {
                                ResponseModel<String> responseModel = response.body();
                                if (responseModel != null && responseModel.isSuccess()){
                                    List<String> Strings = responseModel.getResult();

                                    String[] fullname = banAccount.getFullname().split(" ");
                                    Toast.makeText(context, "You have unban " + fullname[fullname.length - 1], Toast.LENGTH_SHORT).show();

                                    btDoSomething.setText("Unban");
                                    banAccount.setViewType(TypeBanAccountEnum.IS_BANNED);
                                    banAccount.setBanned(true);
                                    notifyItemChanged(position);
                                }
                            } else {
                                int statusCode = response.code();
                                // handle request errors depending on status code
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                        }
                    });
                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        return this.banAccounts.get(position).getViewType().ordinal();
    }

    @Override
    public int getItemCount() {
        List<BanAccountModel> list = this.banAccounts;
        if (list != null) {
            return list.size();
        }
        return 0;
    }
}
