package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import HCMUTE.SocialMedia.Holders.PostHolder;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.R;

public class PostAdapter extends RecyclerView.Adapter<PostHolder> {
    private Context context;
    private List<PostCardModel> posts;

    public PostAdapter(Context context, List<PostCardModel> posts) {
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
        PostCardModel postCardModel = posts.get(position);
        holder.fullName.setText(postCardModel.getFullName());
        holder.postingTimeAt.setText(postCardModel.getPostingTimeAt());

//        holder.mode.setImageResource((int) postCardModel.getMode());
        holder.postText.setText(postCardModel.getPostText());

        //        postCardModel.getPostMedia().endsWith(".jpg");
        Glide.with(context)
                .load(postCardModel.getAvatar())
                .into(holder.avatar);

        Glide.with(context)
                .load(postCardModel.getPostMedia())
                .into(holder.postImage);

        CardView cvLike = (CardView) holder.itemView.findViewById(R.id.cvLike);
        CardView cvComment = (CardView) holder.itemView.findViewById(R.id.cvComment);
        CardView cvShare = (CardView) holder.itemView.findViewById(R.id.cvShare);
        ImageView ivMenu = (ImageView) holder.itemView.findViewById(R.id.ivMenu);

        cvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView ivLike = (ImageView) cvLike.findViewById(R.id.ivLike);
                if (postCardModel.isLiked()) {
                    postCardModel.setLiked(false);
                    ivLike.setImageResource(R.mipmap.ic_like_72_line);
                } else {
                    postCardModel.setLiked(true);
                    ivLike.setImageResource(R.mipmap.ic_like_72_full);
                }
            }
        });

        cvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Comment", Toast.LENGTH_SHORT).show();
            }
        });

        cvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Share", Toast.LENGTH_SHORT).show();
            }
        });

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_post, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        if (posts != null)
            return posts.size();
        return 0;
    }
}
