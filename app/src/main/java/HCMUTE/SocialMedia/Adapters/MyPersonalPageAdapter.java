package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

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
        rvYourFriends.setAdapter(new FriendInPersonalPageAdapter(context.getApplicationContext(), yourFriends));

        RecyclerView rvPost = holder.itemView.findViewById(R.id.rvPostArea);
        rvPost.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvPost.setAdapter(new PostAdapter(context.getApplicationContext(), posts));
        holder.tvUsername.setText(accountModel.getUsername());
        Glide.with(context).load(accountModel.getAvatarURL()).into(holder.civAvatar);
        holder.tvCountFriend.setText(String.valueOf(accountModel.getCountFriend()) + " friends");
        holder.tvFriends.setText(String.valueOf(accountModel.getCountFriend()) + " friends");
    }

    class MyPersonalPageHolder extends RecyclerView.ViewHolder {
        private CircleImageView civAvatar, civAvatarSmall;
        private TextView tvUsername, tvCountFriend, tvFriends;
        public MyPersonalPageHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            civAvatarSmall = itemView.findViewById(R.id.civAvatarSmall);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvCountFriend = itemView.findViewById(R.id.tvCountFriend);
            tvFriends = itemView.findViewById(R.id.tvFriends);
        }
    }
}
