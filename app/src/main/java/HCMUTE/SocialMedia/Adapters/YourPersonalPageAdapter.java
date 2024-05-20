package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Activities.CreatePostActivity;
import HCMUTE.SocialMedia.Activities.LoginActivity;
import HCMUTE.SocialMedia.Activities.MessageActivity;
import HCMUTE.SocialMedia.Activities.SeeAllFriendActivity;
import HCMUTE.SocialMedia.Activities.VerifyNewAccountActivity;
import HCMUTE.SocialMedia.Activities.ViewProfileActivity;
import HCMUTE.SocialMedia.Enums.TypeSearchEnum;
import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.Models.ConversationCardModel;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourPersonalPageAdapter extends RecyclerView.Adapter<YourPersonalPageAdapter.YourPersonalPageHolder>{
    private Context context;
    private AccountCardModel accountModel;
    private List<YourFriendModel> yourFriends;
    private List<PostCardModel> posts;
    private boolean isFriend = false;

    public YourPersonalPageAdapter(Context context, AccountCardModel accountModel, List<YourFriendModel> yourFriends, List<PostCardModel> posts) {
        this.context = context;
        this.accountModel = accountModel;
        this.yourFriends = yourFriends;
        this.posts = posts;
    }

    @NonNull
    @Override
    public YourPersonalPageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new YourPersonalPageAdapter.YourPersonalPageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.your_personal_page_view, parent, false));
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull YourPersonalPageHolder holder, int position) {
        List<YourFriendModel> yourFriendWithoutMe = new ArrayList<>();
        for (YourFriendModel f: yourFriends) {
            if (!f.getUsername().equals(PrefManager.getUsername())){
                yourFriendWithoutMe.add(f);
            }
        }
        RecyclerView rvYourFriends = holder.itemView.findViewById(R.id.rvYourFriend);
        rvYourFriends.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvYourFriends.setAdapter(new FriendInPersonalPageAdapter(context, yourFriendWithoutMe));

        RecyclerView rvPost = holder.itemView.findViewById(R.id.rvPostArea);
        rvPost.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvPost.setAdapter(new PostAdapter(context, posts));

        Glide.with(context).load(accountModel.getAvatarURL()).into(holder.civAvatar);
        holder.tvFullname.setText(accountModel.getFullname());
        holder.tvUsername.setText(accountModel.getUsername());
        holder.tvFriends.setText(String.valueOf(accountModel.getCountFriend()) + " friends");
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("FRIEND_FULLNAME", accountModel.getFullname());
                bundle.putString("FRIEND_USERNAME", accountModel.getUsername());
                bundle.putString("FRIEND_GENDER", accountModel.getGender());
                bundle.putString("FRIEND_DESC", accountModel.getDescription());
                bundle.putString("FRIEND_COMPANY", accountModel.getCompany());
                bundle.putString("FRIEND_LOCATION", accountModel.getLocation());
                bundle.putBoolean("FRIEND_RELATIONSHIP", accountModel.isSingle());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        for (YourFriendModel a: yourFriends) {
            if (PrefManager.getUsername().equals(a.getUsername())){
                holder.ivFriend.setImageResource(R.mipmap.ic_accepted_48_dark);
                holder.tvFriend.setText("Friend");
                isFriend = true;
                break;
            }
        }
        if (isFriend == false){
            holder.ivFriend.setImageResource(R.mipmap.ic_plus_dark_26);
            holder.tvFriend.setText("Add");
        }
        holder.llFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFriend == true){
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Query");
                    alert.setMessage("Are you sure unfriend?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
                            apiService.unfriend(PrefManager.getUsername(), accountModel.getUsername()).enqueue(new Callback<ResponseModel<String>>() {
                                @Override
                                public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                                    if (response.isSuccessful()){
                                        if (response.body().isSuccess()){
                                            isFriend = false;
                                            holder.ivFriend.setImageResource(R.mipmap.ic_plus_dark_26);
                                            holder.tvFriend.setText("Add");
                                            Toast.makeText(context, "Unfriend success", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else {
                                        Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                                    Toast.makeText(context, "Unfriend fail", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert.show();
                }
                else {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                    apiService.makeFriend(PrefManager.getUsername(), accountModel.getUsername()).enqueue(new Callback<ResponseModel<String>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                            if (response.isSuccessful()) {
                                ResponseModel<String> responseModel = response.body();
                                if (responseModel != null && responseModel.isSuccess()){
                                    Toast.makeText(context, "Sent a friend request", Toast.LENGTH_SHORT).show();
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
            }
        });
        holder.tvSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SeeAllFriendActivity.class);
                intent.putExtra("SEE_FRIEND", accountModel.getUsername());
                context.startActivity(intent);
            }
        });
        holder.llMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                apiService.getConversationWithFriend(PrefManager.getUsername(), accountModel.getUsername()).enqueue(new Callback<ResponseModel<ConversationCardModel>>() {
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
                                bundle.putString("conversationAvatar", conversationCardModel.getConversationAvatar());
                                bundle.putString("conversationName", conversationCardModel.getConversationName());
                                intent.putExtras(bundle);

                                context.startActivity(intent);
                            }
                            else {
                                Toast.makeText(context, "You two are not friends", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "You two are not friends", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel<ConversationCardModel>> call, Throwable t) {
                    }
                });
            }
        });
    }

    class YourPersonalPageHolder extends RecyclerView.ViewHolder {
        private CircleImageView civAvatar;
        private TextView tvUsername, tvFullname, tvFriends, tvSeeAll, tvFriend;
        private Button btnDetail;
        private LinearLayout llFriend, llMessage;
        ImageView ivFriend;
        public YourPersonalPageHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvFullname = itemView.findViewById(R.id.tvFullname);
            tvFriends = itemView.findViewById(R.id.tvFriends);
            tvSeeAll = itemView.findViewById(R.id.tvSeeAll);
            btnDetail = itemView.findViewById(R.id.btnDetail);
            llFriend = itemView.findViewById(R.id.llFriend);
            llMessage = itemView.findViewById(R.id.llMessage);
            ivFriend = itemView.findViewById(R.id.ivFriend);
            tvFriend = itemView.findViewById(R.id.tvFriend);
        }
    }
}
