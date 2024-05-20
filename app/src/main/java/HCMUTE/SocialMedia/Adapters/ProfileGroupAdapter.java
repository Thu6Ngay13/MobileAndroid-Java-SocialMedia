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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Activities.AcceptMemberInGroupActivity;
import HCMUTE.SocialMedia.Activities.CommentActivity;
import HCMUTE.SocialMedia.Activities.CreatePostActivity;
import HCMUTE.SocialMedia.Activities.CreatePostInGroupActivity;
import HCMUTE.SocialMedia.Activities.EditProfileActivity;
import HCMUTE.SocialMedia.Activities.EditProfileGroupActivity;
import HCMUTE.SocialMedia.Activities.GroupActivity;
import HCMUTE.SocialMedia.Activities.ProfileGroupActivity;
import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.Models.GroupModel;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Responses.SimpleResponse;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class ProfileGroupAdapter extends RecyclerView.Adapter<ProfileGroupAdapter.ProfileGroupHolder>{
    private Context context;
    private GroupModel groupModel;
    private List<YourFriendModel> yourFriends;
    private List<PostCardModel> posts;
    private RecyclerView rvPost;
    private PostAdapter postAdapter;

    private APIService apiService;

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

        if(PrefManager.getUsername().equals(groupModel.getHolderUsername())) {
            holder.btnAcceptMembers.setVisibility(View.VISIBLE);
            holder.btnEditProfile.setText("Edit Profile");
        } else {
            holder.btnEditProfile.setText("View Profile");
        }
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.isAcceptGroup(PrefManager.getUsername(), groupModel.getGroupId()).enqueue(new Callback<SimpleResponse<String>>() {
            @Override
            public void onResponse(Call<SimpleResponse<String>> call, Response<SimpleResponse<String>> response) {
                if (response.isSuccessful()) {
                    if(response.body().getResult().toString() == "Waiting")
                    {
                        holder.btnJoinGroup.setText("Waiting");
                    } else if (response.body().getResult().toString() == "Join Group")
                    {
                        holder.btnJoinGroup.setText("Join Group");
                    } else
                    {
                        holder.btnJoinGroup.setText("Out Group");
                    }
                } else {
                    holder.btnJoinGroup.setText("Join Group");
                }
            }
            @Override
            public void onFailure(Call<SimpleResponse<String>> call, Throwable t) {
            }
        });

        holder.btnJoinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.btnJoinGroup.getText() == "Join Group") {
                    apiService = RetrofitClient.getRetrofit().create(APIService.class);
                    apiService.joinGroupByUsernameAndGroupId(PrefManager.getUsername(), groupModel.getGroupId()).enqueue(new Callback<ResponseModel<String>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(context, "Vui lòng đợi accept", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Join group false", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                        }
                    });
                    holder.btnJoinGroup.setText("Waiting");

                }else if (holder.btnJoinGroup.getText() == "Out Group"){
                    apiService.unjoinGroupByUsernameAndGroupId(PrefManager.getUsername(), groupModel.getGroupId()).enqueue(new Callback<ResponseModel<String>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(context,"Out group successful", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context,"Out group false", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                        }
                    });

                    holder.btnJoinGroup.setText("Join Group");
                }
                else {
                    apiService.unjoinGroupByUsernameAndGroupId(PrefManager.getUsername(), groupModel.getGroupId()).enqueue(new Callback<ResponseModel<String>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(context,"Successful", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context,"Out group false", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                        }
                    });

                    holder.btnJoinGroup.setText("Join Group");
                }
            }

        });



            holder.btnEditProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(PrefManager.getUsername().equals(groupModel.getHolderUsername()))
                    {
                    Intent intent = new Intent(context, EditProfileGroupActivity.class);
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
                    context.startActivity(intent);}
                    else {
                        Intent intent = new Intent(context, EditProfileGroupActivity.class);
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
                }
            });


        holder.btnAcceptMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnAcceptMembers.setVisibility(View.VISIBLE);
                Log.d("", groupModel.toString());
                Intent intent = new Intent(context, AcceptMemberInGroupActivity.class);
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
                context.startActivity(intent);}

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
        private TextView tvHolderFullName, tvGroupName,  ibTextPosting, btnAcceptMembers;
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
            btnAcceptMembers = itemView.findViewById(R.id.btnAcceptMembers);
        }
    }
    private void closeActivity() {
        if (context instanceof ProfileGroupActivity) {
            ((ProfileGroupActivity) context).finish();
        }
    }
}
