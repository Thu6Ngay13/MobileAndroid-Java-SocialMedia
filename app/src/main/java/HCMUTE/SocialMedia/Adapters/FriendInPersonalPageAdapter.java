package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import HCMUTE.SocialMedia.Activities.MyPersonalPageActivity;
import HCMUTE.SocialMedia.Activities.SearchActivity;
import HCMUTE.SocialMedia.Activities.YourPersonalPageActivity;
import HCMUTE.SocialMedia.Consts.Const;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.R;

public class FriendInPersonalPageAdapter extends RecyclerView.Adapter<FriendInPersonalPageAdapter.FriendHolder> {
    private Context context;
    private List<YourFriendModel> yourFriends;

    public FriendInPersonalPageAdapter(Context context, List<YourFriendModel> yourFriends){
        this.context = context;
        this.yourFriends = yourFriends;
    }

    @NonNull
    @Override
    public FriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.your_friend_vertical_view, parent, false);
        return new FriendHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendHolder holder, int position) {
        YourFriendModel yourFriendModel = yourFriends.get(position);
        Glide.with(context)
                .load(yourFriendModel.getAvatar())
                .into(holder.avatar);
        holder.username.setText(yourFriendModel.getUsername());
        holder.cvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yourFriendModel.getUsername().equals(Const.USERNAME)){
                    Intent intent = new Intent(context, MyPersonalPageActivity.class);
                    context.startActivity(intent);
                }
                else{
                    Intent intent = new Intent(context, YourPersonalPageActivity.class);
                    intent.putExtra("YOUR_FRIEND_USERNAME", yourFriendModel.getUsername());
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return yourFriends != null ? yourFriends.size() : 0;
    }

    class FriendHolder extends RecyclerView.ViewHolder {
        public CardView cvUser;
        public ImageView avatar;
        public TextView username;

        public FriendHolder(@NonNull View itemView) {
            super(itemView);
            this.cvUser = itemView.findViewById(R.id.cvUser);
            this.avatar = itemView.findViewById(R.id.ivAvatar);
            this.username = itemView.findViewById(R.id.tvUsername);
        }
    }
}
