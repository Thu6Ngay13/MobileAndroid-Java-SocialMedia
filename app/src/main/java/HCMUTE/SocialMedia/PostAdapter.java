package HCMUTE.SocialMedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostHolder> {

    private Context context;
    private List<PostModel> posts;

    public PostAdapter(Context context, List<PostModel> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostHolder(LayoutInflater.from(context).inflate(R.layout.post_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.avatar.setImageResource(posts.get(position).getAvatar());
        holder.fullName.setText(posts.get(position).getFullName());
        holder.postingTimeAt.setText(posts.get(position).getPostingTimeAt());
        holder.mode.setImageResource(posts.get(position).getMode());
        holder.postImage.setImageResource(posts.get(position).getPostImage());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
