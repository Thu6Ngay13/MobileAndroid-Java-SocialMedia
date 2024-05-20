package HCMUTE.SocialMedia.Holders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import HCMUTE.SocialMedia.R;

public class AcceptMemberInGroupHolder  extends RecyclerView.ViewHolder{
    public ImageView avatar;
    public TextView holderFullName;
    public Button btDecline, btAccept;

    public AcceptMemberInGroupHolder(@NonNull View itemView) {
        super(itemView);
        this.avatar = itemView.findViewById(R.id.ivAvatar);
        this.holderFullName = itemView.findViewById(R.id.tvHolderFullName);
        this.btDecline = itemView.findViewById(R.id.btDecline);
        this.btAccept = itemView.findViewById(R.id.btAccept);

    }
}
