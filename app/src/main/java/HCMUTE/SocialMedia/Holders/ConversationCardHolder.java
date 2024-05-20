package HCMUTE.SocialMedia.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import HCMUTE.SocialMedia.R;

public class ConversationCardHolder extends RecyclerView.ViewHolder {
    public ImageView avatar;
    public TextView fullname;

    public ConversationCardHolder(@NonNull View itemView) {
        super(itemView);
        this.avatar = itemView.findViewById(R.id.ivAvatar);
        this.fullname = itemView.findViewById(R.id.tvFullName);
    }
}
