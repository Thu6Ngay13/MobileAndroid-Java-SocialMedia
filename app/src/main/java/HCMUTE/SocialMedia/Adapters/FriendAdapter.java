package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import HCMUTE.SocialMedia.Enums.TypeFriendEnum;
import HCMUTE.SocialMedia.Holders.FriendHolder;
import HCMUTE.SocialMedia.Models.FriendModel;
import HCMUTE.SocialMedia.R;

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

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(FriendHolder holder, int position) {
        FriendModel friendModel = this.friendModels.get(position);
        Glide.with(this.context).load(friendModel.getAvatar()).into(holder.avatar);
        holder.fullName.setText(friendModel.getFullName());

        if (friendModel.getViewType() == TypeFriendEnum.YOUR_FRIEND) {
            Button btViewProfile = (Button) holder.itemView.findViewById(R.id.btViewProfile);
            Button btSendMessage = (Button) holder.itemView.findViewById(R.id.btSendMessage);
            btViewProfile.setOnClickListener(new View.OnClickListener() {
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                }
            });
            btSendMessage.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
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
                }
            });
            btDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
