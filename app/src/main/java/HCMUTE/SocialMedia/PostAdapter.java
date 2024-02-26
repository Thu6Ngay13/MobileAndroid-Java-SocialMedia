package HCMUTE.SocialMedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
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
        PostModel postModel = posts.get(position);
        holder.avatar.setImageResource(postModel.getAvatar());
        holder.fullName.setText(postModel.getFullName());
        holder.postingTimeAt.setText(postModel.getPostingTimeAt());
        holder.mode.setImageResource(postModel.getMode());
        holder.postText.setText(postModel.getPostText());
        holder.postImage.setImageResource(postModel.getPostImage());

        List<InteractModel> interactModels = new ArrayList<>();
        interactModels.add(new InteractModel(R.mipmap.ic_like_72_line, "Like"));
        interactModels.add(new InteractModel(R.mipmap.ic_comment_72_dark, "Comment"));
        interactModels.add(new InteractModel(R.mipmap.ic_share_72_dark, "Share"));

        RecyclerView recyclerView = holder.itemView.findViewById(R.id.glInteract);
        GridLayoutManager gridLayout = new GridLayoutManager(context, 3, RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayout);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL));
        recyclerView.setAdapter(new InteractAdapter(context.getApplicationContext(), interactModels));
    }

    @Override
    public int getItemCount() {
        if (posts != null)
            return posts.size();
        return 0;
    }
}
