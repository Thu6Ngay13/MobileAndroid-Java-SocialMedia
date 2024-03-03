package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import HCMUTE.SocialMedia.Holders.PostHolder;
import HCMUTE.SocialMedia.Models.PostModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Utils.ImageUtil;

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

        if(postModel.getPostImage() > 0){
            holder.postImage.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    holder.postImage.getViewTreeObserver().removeOnPreDrawListener(this);

                    Resources resources = context.getResources();
                    int imageResourceId = postModel.getPostImage();
                    int[] dimensions = ImageUtil.getImageDimensions(resources, imageResourceId);

                    int img_width = dimensions[0];
                    int img_height = dimensions[1];

                    int width = holder.postImage.getWidth();
                    int height = width * img_height / img_width;

                    holder.postImage.setImageResource(postModel.getPostImage());
                    holder.postImage.getLayoutParams().height = height;
                    holder.postImage.requestLayout();

                    return true;
                }
            });
        }

        CardView cvLike = (CardView) holder.itemView.findViewById(R.id.cvLike);
        CardView cvComment = (CardView) holder.itemView.findViewById(R.id.cvComment);
        CardView cvShare = (CardView) holder.itemView.findViewById(R.id.cvShare);

        cvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView ivLike = (ImageView) cvLike.findViewById(R.id.ivLike);
                if(postModel.getLiked()){
                    postModel.setLiked(false);
                    ivLike.setImageResource(R.mipmap.ic_like_72_line);
                } else {
                    postModel.setLiked(true);
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

    }

    @Override
    public int getItemCount() {
        if (posts != null)
            return posts.size();
        return 0;
    }
}
