package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import HCMUTE.SocialMedia.Holders.YourFriendHolder;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.R;

public class YourFriendAdapter extends RecyclerView.Adapter<YourFriendHolder> {

    private Context context;
    private List<YourFriendModel> yourFriends;

    public YourFriendAdapter(Context context, List<YourFriendModel> yourFriends) {
        this.context = context;
        this.yourFriends = yourFriends;
    }

    @NonNull
    @Override
    public YourFriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new YourFriendHolder(LayoutInflater.from(context).inflate(R.layout.your_friend_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull YourFriendHolder holder, int position) {
        YourFriendModel yourFriendModel = yourFriends.get(position);
        holder.avatar.setImageResource(yourFriendModel.getAvatar());
        holder.fullName.setText(yourFriendModel.getFullName());

        Button btViewProfile = (Button) holder.itemView.findViewById(R.id.btViewProfile);
        Button btSendMessage = (Button) holder.itemView.findViewById(R.id.btSendMessage);

        btViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (yourFriends != null)
            return yourFriends.size();
        return 0;
    }
}
