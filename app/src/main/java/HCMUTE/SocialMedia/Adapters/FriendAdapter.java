package HCMUTE.SocialMedia.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import HCMUTE.SocialMedia.Activities.MainActivity;
import HCMUTE.SocialMedia.Holders.FriendHolder;
import HCMUTE.SocialMedia.Models.FriendModel;
import HCMUTE.SocialMedia.R;

public class FriendAdapter extends RecyclerView.Adapter<FriendHolder> {

    private Context context;
    private List<FriendModel> friends;

    public FriendAdapter(Context context, List<FriendModel> friends) {
        this.context = context;
        this.friends = friends;
    }

    @NonNull
    @Override
    public FriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendHolder(LayoutInflater.from(context).inflate(R.layout.friend_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendHolder holder, int position) {
        FriendModel friendModel = friends.get(position);
        Button btYourFriend = (Button) holder.itemView.findViewById(R.id.btYourFriend);
        Button btRequestedFriend = (Button) holder.itemView.findViewById(R.id.btRequestedFriend);

        friendModel.setYourFriendNow(true);
        onCickYourFriend(holder, friendModel);
        btYourFriend.setTextColor(ContextCompat.getColor(context, R.color.green_dark));
        btRequestedFriend.setTextColor(ContextCompat.getColor(context, R.color.green_light));

        btYourFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(friendModel.isYourFriendNow()) return;

                btYourFriend.setTextColor(ContextCompat.getColor(context, R.color.green_dark));
                btRequestedFriend.setTextColor(ContextCompat.getColor(context, R.color.green_light));
                friendModel.setYourFriendNow(true);
                onCickYourFriend(holder, friendModel);
            }
        });

        btRequestedFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!friendModel.isYourFriendNow()) return;

                btYourFriend.setTextColor(ContextCompat.getColor(context, R.color.green_light));
                btRequestedFriend.setTextColor(ContextCompat.getColor(context, R.color.green_dark));
                friendModel.setYourFriendNow(false);
                onCickFriendRequest(holder, friendModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (friends != null)
            return friends.size();
        return 0;
    }

    private void onCickYourFriend(@NonNull FriendHolder holder, FriendModel friendModel){
        RecyclerView recyclerView = holder.itemView.findViewById(R.id.rvFriend);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new YourFriendAdapter(context.getApplicationContext(), friendModel.getYourFriendModels()));
    }

    private void onCickFriendRequest(@NonNull FriendHolder holder, FriendModel friendModel){
        RecyclerView recyclerView = holder.itemView.findViewById(R.id.rvFriend);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new FriendRequestAdapter(context.getApplicationContext(), friendModel.getFriendRequestModels()));
    }
}
