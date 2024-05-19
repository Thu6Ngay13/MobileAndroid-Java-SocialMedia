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

import org.w3c.dom.Text;

import java.util.List;

import HCMUTE.SocialMedia.Activities.YourPersonalPageActivity;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.R;

public class SeeAllFriendAdapter extends RecyclerView.Adapter<SeeAllFriendAdapter.YourFriendHolder>{
    private Context context;
    private List<YourFriendModel> friends;

    public SeeAllFriendAdapter(Context context, List<YourFriendModel> friends) {
        this.context = context;
        this.friends = friends;
    }

    @NonNull
    @Override
    public YourFriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new YourFriendHolder(LayoutInflater.from(context).inflate(R.layout.your_friend_vertical_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull YourFriendHolder holder, int position) {
        YourFriendModel friendModel = friends.get(position);
        Glide.with(context).load(friendModel.getAvatar()).into(holder.ivAvatar);
        holder.tvUsername.setText(friendModel.getUsername());
        holder.cvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YourPersonalPageActivity.class);
                intent.putExtra("YOUR_FRIEND_USERNAME", friendModel.getUsername());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (friends != null)
            return friends.size();
        return 0;
    }

    class YourFriendHolder extends RecyclerView.ViewHolder{
        ImageView ivAvatar;
        TextView tvUsername;
        CardView cvUser;

        public YourFriendHolder(@NonNull View itemView) {
            super(itemView);
            this.ivAvatar = itemView.findViewById(R.id.ivAvatar);
            this.tvUsername = itemView.findViewById(R.id.tvUsername);
            this.cvUser = itemView.findViewById(R.id.cvUser);
        }
    }
}
