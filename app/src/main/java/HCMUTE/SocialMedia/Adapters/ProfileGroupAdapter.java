package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import HCMUTE.SocialMedia.Activities.CommentActivity;
import HCMUTE.SocialMedia.Activities.CreatePostActivity;
import HCMUTE.SocialMedia.Activities.CreatePostInGroupActivity;
import HCMUTE.SocialMedia.Activities.EditProfileActivity;
import HCMUTE.SocialMedia.Activities.ProfileGroupActivity;
import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.Models.GroupModel;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileGroupAdapter extends RecyclerView.Adapter<ProfileGroupAdapter.ProfileGroupHolder>{
    private Context context;
    private GroupModel groupModel;
    private List<YourFriendModel> yourFriends;
    private List<PostCardModel> posts;
    private RecyclerView rvPost;
    private PostAdapter postAdapter;

    public ProfileGroupAdapter(Context context, GroupModel groupModel, List<YourFriendModel> yourFriends, List<PostCardModel> posts) {
        this.context = context;
        this.groupModel = groupModel;
        this.yourFriends = yourFriends;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ProfileGroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProfileGroupHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_group_view, parent, false));
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileGroupAdapter.ProfileGroupHolder holder, int position) {
        RecyclerView rvJoinedAccount = holder.itemView.findViewById(R.id.rvJoinedAccount);
        rvJoinedAccount.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvJoinedAccount.setAdapter(new FriendInPersonalPageAdapter(context, yourFriends));

        rvPost = holder.itemView.findViewById(R.id.rvPostArea);
        rvPost.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        postAdapter = new PostAdapter(context, posts);
        rvPost.setAdapter(postAdapter);

        holder.tvGroupName.setText(groupModel.getGroupName());
        holder.tvHolderFullName.setText(groupModel.getHolderFullName());
        Glide.with(context).load(groupModel.getAvatarURL()).into(holder.civAvatar);
        Glide.with(context).load(groupModel.getAvatarURL()).into(holder.civAvatarSmall);

        if(PrefManager.getUsername() == groupModel.getHolderFullName())
        {
            holder.btnEditProfile.setText("Edit Profile");
            holder.btnEditProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("", groupModel.toString());
                    Intent intent = new Intent(context, EditProfileActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("GROUP_GROUPID", groupModel.getGroupId());
                    bundle.putString("GROUP_GROUPNAME", groupModel.getGroupName());
                    bundle.putString("GROUP_AVATARURL", groupModel.getAvatarURL());
                    bundle.putString("GROUP_DESCRIPSION", groupModel.getDescription());
                    bundle.putString("GROUP_TIME", groupModel.getCreationTimeAt());
                    bundle.putLong("GROUP_MODEID", groupModel.getModeId());
                    bundle.putString("GROUP_USERNAME", groupModel.getHolderUsername());
                    bundle.putString("GROUP_FULLNAME", groupModel.getHolderFullName());
                    intent.putExtras(bundle);
                    closeActivity();
                    context.startActivity(intent);
                }
            });
        }
        else {
            holder.btnEditProfile.setText("View Profile");
            holder.btnEditProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("", groupModel.toString());
                    Intent intent = new Intent(context, EditProfileActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("GROUP_GROUPID", groupModel.getGroupId());
                    bundle.putString("GROUP_GROUPNAME", groupModel.getGroupName());
                    bundle.putString("GROUP_AVATARURL", groupModel.getAvatarURL());
                    bundle.putString("GROUP_DESCRIPSION", groupModel.getDescription());
                    bundle.putString("GROUP_TIME", groupModel.getCreationTimeAt());
                    bundle.putLong("GROUP_MODEID", groupModel.getModeId());
                    bundle.putString("GROUP_USERNAME", groupModel.getHolderUsername());
                    bundle.putString("GROUP_FULLNAME", groupModel.getHolderFullName());
                    intent.putExtras(bundle);
                    closeActivity();
                    context.startActivity(intent);
                }
            });
        }

        holder.btnJoinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("", groupModel.toString());
                Intent intent = new Intent(context, EditProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("GROUP_GROUPID", groupModel.getGroupId());
                bundle.putString("GROUP_GROUPNAME", groupModel.getGroupName());
                bundle.putString("GROUP_AVATARURL", groupModel.getAvatarURL());
                bundle.putString("GROUP_DESCRIPSION", groupModel.getDescription());
                bundle.putString("GROUP_TIME", groupModel.getCreationTimeAt());
                bundle.putLong("GROUP_MODEID", groupModel.getModeId());
                bundle.putString("GROUP_USERNAME", groupModel.getHolderUsername());
                bundle.putString("GROUP_FULLNAME", groupModel.getHolderFullName());
                intent.putExtras(bundle);
                closeActivity();
                context.startActivity(intent);
            }
        });

        holder.ibTextPosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreatePostInGroupActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("GROUP_GROUPID", groupModel.getGroupId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


    }

    class ProfileGroupHolder extends RecyclerView.ViewHolder {
        private CircleImageView civAvatar, civAvatarSmall;
        private TextView tvHolderFullName, tvGroupName,  ibTextPosting;
        private Button btnEditProfile, btnJoinGroup;
        public ProfileGroupHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            civAvatarSmall = itemView.findViewById(R.id.civAvatarSmall);
            tvHolderFullName = itemView.findViewById(R.id.tvHolderFullName);
            tvGroupName = itemView.findViewById(R.id.tvGroupName);
            btnEditProfile = itemView.findViewById(R.id.btnEditProfile);
            btnJoinGroup = itemView.findViewById(R.id.btnJoinGroup);
            ibTextPosting = itemView.findViewById(R.id.ibTextPosting);
        }
    }
    private void closeActivity() {
        if (context instanceof ProfileGroupActivity) {
            ((ProfileGroupActivity) context).finish();
        }
    }
}
