package HCMUTE.SocialMedia;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InteractHolder extends RecyclerView.ViewHolder{
    public ImageView interactIcon;
    public TextView interactDesc;
    public InteractHolder(@NonNull View itemView) {
        super(itemView);
        this.interactIcon = itemView.findViewById(R.id.ivInteractIcon);
        this.interactDesc = itemView.findViewById(R.id.ivInteractDesc);
    }
}
