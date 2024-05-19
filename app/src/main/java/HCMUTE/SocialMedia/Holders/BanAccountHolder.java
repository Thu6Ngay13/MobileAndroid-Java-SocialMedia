package HCMUTE.SocialMedia.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import HCMUTE.SocialMedia.R;

public class BanAccountHolder extends RecyclerView.ViewHolder {
    public ImageView avatar;
    public TextView fullName;

    public BanAccountHolder(View itemView) {
        super(itemView);
        this.avatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
        this.fullName = (TextView) itemView.findViewById(R.id.tvFullName);
    }
}
