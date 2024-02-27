package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import HCMUTE.SocialMedia.Holders.FriendRequestHolder;
import HCMUTE.SocialMedia.Models.FriendRequestModel;
import HCMUTE.SocialMedia.R;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestHolder> {

    private Context context;
    private List<FriendRequestModel> friendRequests;

    public FriendRequestAdapter(Context context, List<FriendRequestModel> friendRequests) {
        this.context = context;
        this.friendRequests = friendRequests;
    }

    @NonNull
    @Override
    public FriendRequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendRequestHolder(LayoutInflater.from(context).inflate(R.layout.friend_request_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestHolder holder, int position) {
        FriendRequestModel friendRequestModel = friendRequests.get(position);
        holder.avatar.setImageResource(friendRequestModel.getAvatar());
        holder.fullName.setText(friendRequestModel.getFullName());
        holder.requestTimeAt.setText(friendRequestModel.getRequestTimeAt());

        Button btAccept = (Button) holder.itemView.findViewById(R.id.btAccept);
        Button btDecline = (Button) holder.itemView.findViewById(R.id.btDecline);

        btAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (friendRequests != null)
            return friendRequests.size();
        return 0;
    }
}
