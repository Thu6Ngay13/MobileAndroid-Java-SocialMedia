package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import HCMUTE.SocialMedia.Activities.EditProfileActivity;
import HCMUTE.SocialMedia.Activities.YourPersonalPageActivity;
import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyPersonalPageAdapter extends RecyclerView.Adapter<MyPersonalPageAdapter.MyPersonalPageHolder>{
    private Context context;
    private AccountCardModel accountModel;
    private List<YourFriendModel> yourFriends;
    private List<PostCardModel> posts;
    private OnEditProfileClickListener editProfileClickListener;
    private FriendInPersonalPageAdapter adapterFriend;

    public void setOnEditProfileClickListener(OnEditProfileClickListener listener) {
        this.editProfileClickListener = listener;
    }
    public interface OnEditProfileClickListener {
        void onEditProfileClick();
    }

    public MyPersonalPageAdapter(Context context, AccountCardModel accountModel, List<YourFriendModel> yourFriends, List<PostCardModel> posts) {
        this.context = context;
        this.accountModel = accountModel;
        this.yourFriends = yourFriends;
        this.posts = posts;
    }

    @NonNull
    @Override
    public MyPersonalPageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyPersonalPageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_personal_page_view, parent, false));
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPersonalPageHolder holder, int position) {
        RecyclerView rvYourFriends = holder.itemView.findViewById(R.id.rvYourFriend);
        rvYourFriends.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        adapterFriend = new FriendInPersonalPageAdapter(context.getApplicationContext(), yourFriends);
        rvYourFriends.setAdapter(adapterFriend);

        RecyclerView rvPost = holder.itemView.findViewById(R.id.rvPostArea);
        rvPost.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvPost.setAdapter(new PostAdapter(context.getApplicationContext(), posts));
        holder.tvUsername.setText(accountModel.getUsername());
        Glide.with(context).load(accountModel.getAvatarURL()).into(holder.civAvatar);
        Glide.with(context).load(accountModel.getAvatarURL()).into(holder.civAvatarSmall);
        holder.tvCountFriend.setText(String.valueOf(accountModel.getCountFriend()) + " friends");
        holder.tvFriends.setText(String.valueOf(accountModel.getCountFriend()) + " friends");
        holder.btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editProfileClickListener != null) {
                    editProfileClickListener.onEditProfileClick();
                }
            }
        });
    }

    class MyPersonalPageHolder extends RecyclerView.ViewHolder {
        private CircleImageView civAvatar, civAvatarSmall;
        private TextView tvUsername, tvCountFriend, tvFriends;
        private Button btnEditProfile;
        public MyPersonalPageHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            civAvatarSmall = itemView.findViewById(R.id.civAvatarSmall);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvCountFriend = itemView.findViewById(R.id.tvCountFriend);
            tvFriends = itemView.findViewById(R.id.tvFriends);
            btnEditProfile = itemView.findViewById(R.id.btnEditProfile);
        }
    }
}
