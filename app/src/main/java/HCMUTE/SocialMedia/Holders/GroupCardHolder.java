package HCMUTE.SocialMedia.Holders;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import HCMUTE.SocialMedia.Activities.ProfileGroupActivity;
import HCMUTE.SocialMedia.R;

public class GroupCardHolder extends RecyclerView.ViewHolder {
    public ImageView avatar;
    public TextView groupName;
    public TextView holderFullName;
    public TextView groupId;

    public GroupCardHolder(@NonNull View itemView) {
        super(itemView);
        this.avatar = itemView.findViewById(R.id.ivAvatar);
        this.groupName = itemView.findViewById(R.id.tvGroupName);
        this.groupId = itemView.findViewById(R.id.groupId);
        this.holderFullName = itemView.findViewById(R.id.tvHolderFullName);
/*
        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProfileGroupActivity.class);
                intent.putExtra("groupId", groupId.getText());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
            }
        });*/
    }
}
