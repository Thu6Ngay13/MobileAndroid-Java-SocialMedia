package HCMUTE.SocialMedia.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import HCMUTE.SocialMedia.R;

public class GroupCardHolder extends RecyclerView.ViewHolder {
    public ImageView avatar;
    public TextView groupName;
    public TextView holderFullName;

    public GroupCardHolder(@NonNull View itemView) {
        super(itemView);
        this.avatar = itemView.findViewById(R.id.ivAvatar);
        this.groupName = itemView.findViewById(R.id.tvGroupName);
        this.holderFullName = itemView.findViewById(R.id.tvHolderFullName);
    }
}
