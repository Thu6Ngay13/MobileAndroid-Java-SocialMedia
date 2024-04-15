package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.R;

public class YourPersonalPageAdapter extends RecyclerView.Adapter<YourPersonalPageAdapter.YourPersonalPageHolder>{
    private Context context;
    private List<YourFriendModel> yourFriends;
    private List<PostCardModel> posts;
    public YourPersonalPageAdapter(Context context, List<YourFriendModel> yourFriends, List<PostCardModel> posts){
        this.context = context;
        this.yourFriends = yourFriends;
        this.posts = posts;
    }
    @NonNull
    @Override
    public YourPersonalPageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new YourPersonalPageAdapter.YourPersonalPageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.your_personal_page_view, parent, false));
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull YourPersonalPageHolder holder, int position) {
        RecyclerView rvYourFriends = holder.itemView.findViewById(R.id.rvYourFriend);
        rvYourFriends.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvYourFriends.setAdapter(new FriendInPersonalPageAdapter(context.getApplicationContext(), yourFriends));

        RecyclerView rvPost = holder.itemView.findViewById(R.id.rvPostArea);
        rvPost.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvPost.setAdapter(new PostAdapter(context.getApplicationContext(), posts));
    }

    class YourPersonalPageHolder extends RecyclerView.ViewHolder {
        public YourPersonalPageHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
