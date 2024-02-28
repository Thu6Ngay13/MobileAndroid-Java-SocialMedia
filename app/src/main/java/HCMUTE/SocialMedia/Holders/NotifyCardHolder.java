package HCMUTE.SocialMedia.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.security.PublicKey;

import HCMUTE.SocialMedia.R;

public class NotifyCardHolder extends RecyclerView.ViewHolder {
    public ImageView avatar;
    public TextView fullNameAndContent;
    public TextView notifyTimeAt;

    public NotifyCardHolder(@NonNull View itemView) {
        super(itemView);
        this.avatar = itemView.findViewById(R.id.ivAvatar);
        this.fullNameAndContent = itemView.findViewById(R.id.tvFullNameAndContent);
        this.notifyTimeAt = itemView.findViewById(R.id.tvNotifyTimeAt);
    }
}
