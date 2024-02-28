package HCMUTE.SocialMedia.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import HCMUTE.SocialMedia.R;

public class SettingHolder extends RecyclerView.ViewHolder {
    public ImageView avatar;
    public TextView fullname;
    public SettingHolder(@NonNull View itemView) {
        super(itemView);
        this.avatar = itemView.findViewById(R.id.ibAvatar);
        this.fullname = itemView.findViewById(R.id.tvFullname);
    }
}
