package HCMUTE.SocialMedia.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import HCMUTE.SocialMedia.R;

public class FriendHolder extends RecyclerView.ViewHolder {
    public ImageView avatar;
    public TextView fullName;
    public TextView requestTimeAt;

    public FriendHolder(View itemView) {
        super(itemView);
        this.avatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
        this.fullName = (TextView) itemView.findViewById(R.id.tvFullName);
        this.requestTimeAt = (TextView) itemView.findViewById(R.id.tvRequestTimeAt);
    }

}
