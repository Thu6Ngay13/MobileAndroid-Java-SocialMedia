package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import HCMUTE.SocialMedia.Activities.MessageActivity;
import HCMUTE.SocialMedia.Consts.Const;
import HCMUTE.SocialMedia.Enums.TypeFriendEnum;
import HCMUTE.SocialMedia.Holders.FriendHolder;
import HCMUTE.SocialMedia.Models.ConversationCardModel;
import HCMUTE.SocialMedia.Models.FriendModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendAdapter extends RecyclerView.Adapter<FriendHolder> {
    private Context context;
    private List<FriendModel> friendModels;

    public FriendAdapter(Context context, List<FriendModel> friendModels) {
        this.context = context;
        this.friendModels = friendModels;
    }

    @Override
    public FriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TypeFriendEnum.YOUR_FRIEND.ordinal()) {
            return new FriendHolder(LayoutInflater.from(this.context).inflate(R.layout.your_friend_view, parent, false));
        }
        if (viewType == TypeFriendEnum.FRIEND_REQUEST.ordinal()) {
            return new FriendHolder(LayoutInflater.from(this.context).inflate(R.layout.friend_request_view, parent, false));
        }
        return new FriendHolder(LayoutInflater.from(this.context).inflate(R.layout.your_friend_view, parent, false));
    }

    @Override
    public void onBindViewHolder(FriendHolder holder, int position) {
        FriendModel friendModel = this.friendModels.get(position);
        Glide.with(this.context).load(friendModel.getAvatar()).into(holder.avatar);
        holder.fullName.setText(friendModel.getFullName());

        if (friendModel.getViewType() == TypeFriendEnum.YOUR_FRIEND) {
            Button btViewProfile = (Button) holder.itemView.findViewById(R.id.btViewProfile);
            Button btSendMessage = (Button) holder.itemView.findViewById(R.id.btSendMessage);
            btViewProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            btSendMessage.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                    apiService.getConversationWithFriend(Const.USERNAME, friendModel.getUsername()).enqueue(new Callback<ResponseModel<ConversationCardModel>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<ConversationCardModel>> call, Response<ResponseModel<ConversationCardModel>> response) {
                            if (response.isSuccessful()) {
                                ResponseModel<ConversationCardModel> responseModel = response.body();
                                if (responseModel != null && responseModel.isSuccess()) {
                                    List<ConversationCardModel> responseModelResult = responseModel.getResult();
                                    ConversationCardModel conversationCardModel = responseModelResult.get(0);

                                    Intent intent = new Intent(context, MessageActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                    Bundle bundle = new Bundle();
                                    bundle.putLong("conversationId", conversationCardModel.getConversationId());
                                    intent.putExtras(bundle);

                                    context.startActivity(intent);
                                }
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
            });
            return;
        }
        if (friendModel.getViewType() == TypeFriendEnum.FRIEND_REQUEST) {
            holder.requestTimeAt.setText(friendModel.getRequestTimeAt());
            Button btAccept = (Button) holder.itemView.findViewById(R.id.btAccept);
            Button btDecline = (Button) holder.itemView.findViewById(R.id.btDecline);
            btAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                    apiService.acceptFriend(Const.USERNAME, friendModel.getUsername()).enqueue(new Callback<ResponseModel<String>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                            if (response.isSuccessful()) {
                                ResponseModel<String> responseModel = response.body();

                                friendModels.remove(friendModel);
                                notifyAll();

                                String[] fullname = friendModel.getFullName().split(" ");
                                Toast.makeText(context, "You and " + fullname[fullname.length -1] + " have become friends", Toast.LENGTH_SHORT).show();
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
            btDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                    apiService.declineFriend(Const.USERNAME, friendModel.getUsername()).enqueue(new Callback<ResponseModel<String>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                            if (response.isSuccessful()) {
                                ResponseModel<String> responseModel = response.body();

                                friendModels.remove(friendModel);
                                notifyAll();

                                String[] fullname = friendModel.getFullName().split(" ");
                                Toast.makeText(context, "You declined " + fullname[fullname.length -1] + "'s friend request", Toast.LENGTH_SHORT).show();
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
        return this.friendModels.get(position).getViewType().ordinal();
    }

    @Override
    public int getItemCount() {
        List<FriendModel> list = this.friendModels;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

}
