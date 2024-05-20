package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import HCMUTE.SocialMedia.Activities.ProfileGroupActivity;
import HCMUTE.SocialMedia.Holders.GroupCardHolder;
import HCMUTE.SocialMedia.Models.GroupModel;
import HCMUTE.SocialMedia.R;

public class GroupCardAdapter extends RecyclerView.Adapter<GroupCardHolder> {

    private Context context;
    private List<GroupModel> groupCards;

    public GroupCardAdapter(Context context, List<GroupModel> groupCards) {
        this.context = context;
        this.groupCards = groupCards;
    }

    @NonNull
    @Override
    public GroupCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GroupCardHolder(LayoutInflater.from(context).inflate(R.layout.group_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GroupCardHolder holder, int position) {
        final GroupModel groupCardModel = groupCards.get(position);
        Glide.with(context)
                .load(groupCardModel.getAvatarURL())
                .into(holder.avatar);

        String holderFullName = groupCardModel.getHolderFullName();
        String groupName = groupCardModel.getGroupName();

        holder.holderFullName.setText(holderFullName);
        holder.groupName.setText(groupName);
        holder.groupId.setText(String.valueOf(groupCardModel.getGroupId()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("", groupCardModel.toString());
                Intent intent = new Intent(context, ProfileGroupActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putLong("GROUP_GROUPID", groupCardModel.getGroupId());
                bundle.putString("GROUP_GROUPNAME", groupCardModel.getGroupName());
                bundle.putString("GROUP_AVATARURL", groupCardModel.getAvatarURL());
                bundle.putString("GROUP_DESCRIPSION", groupCardModel.getDescription());
                bundle.putString("GROUP_TIME", groupCardModel.getCreationTimeAt());
                bundle.putLong("GROUP_MODEID", groupCardModel.getModeId());
                bundle.putString("GROUP_USERNAME", groupCardModel.getHolderUsername());
                bundle.putString("GROUP_FULLNAME", groupCardModel.getHolderFullName());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (groupCards != null)
            return groupCards.size();
        return 0;
    }
}
