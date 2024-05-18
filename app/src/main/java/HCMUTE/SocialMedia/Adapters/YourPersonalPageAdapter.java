package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import HCMUTE.SocialMedia.Activities.CreatePostActivity;
import HCMUTE.SocialMedia.Activities.ViewProfileActivity;
import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class YourPersonalPageAdapter extends RecyclerView.Adapter<YourPersonalPageAdapter.YourPersonalPageHolder>{
    private Context context;
    private AccountCardModel accountModel;
    private List<YourFriendModel> yourFriends;
    private List<PostCardModel> posts;

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
        RecyclerView rvYourFriends = holder.itemView.findViewById(R.id.rvYourFriend);
        rvYourFriends.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvYourFriends.setAdapter(new FriendInPersonalPageAdapter(context, yourFriends));

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
    }

    class YourPersonalPageHolder extends RecyclerView.ViewHolder {
        private CircleImageView civAvatar;
        private TextView tvUsername, tvFullname, tvFriends, tvSeeAll;
        private Button btnDetail;
        private LinearLayout llFriend, llMessage;
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
        }
    }
}
