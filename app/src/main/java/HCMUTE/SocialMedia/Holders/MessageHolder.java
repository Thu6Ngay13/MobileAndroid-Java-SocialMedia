package HCMUTE.SocialMedia.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import HCMUTE.SocialMedia.R;

public class MessageHolder extends RecyclerView.ViewHolder {

    public TextView fullname;
    public TextView messageSendingAt;
    public TextView textMessage;
    public ImageView mediaMessage;

    public MessageHolder(@NonNull View itemView) {
        super(itemView);
        this.fullname = itemView.findViewById(R.id.tvFullName);
        this.messageSendingAt = itemView.findViewById(R.id.tvMessageSendingAt);
        this.textMessage = itemView.findViewById(R.id.tvTextMessage);
        this.mediaMessage = itemView.findViewById(R.id.ivMediaMessage);
    }
}
