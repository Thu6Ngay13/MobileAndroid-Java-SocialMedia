package HCMUTE.SocialMedia.Holders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import HCMUTE.SocialMedia.R;

public class FriendRequestHolder extends RecyclerView.ViewHolder {
    public ImageView avatar;
    public TextView fullName;
    public TextView requestTimeAt;

    public FriendRequestHolder(@NonNull View itemView) {
        super(itemView);
        this.avatar = itemView.findViewById(R.id.ivAvatar);
        this.fullName = itemView.findViewById(R.id.tvFullName);
        this.requestTimeAt = itemView.findViewById(R.id.tvRequestTimeAt);
    }
}
