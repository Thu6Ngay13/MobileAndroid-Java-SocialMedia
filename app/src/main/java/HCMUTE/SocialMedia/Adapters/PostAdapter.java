package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

import HCMUTE.SocialMedia.Activities.CommentActivity;
import HCMUTE.SocialMedia.Enums.TypeViewLoad;
import HCMUTE.SocialMedia.Holders.PostHolder;
import HCMUTE.SocialMedia.Holders.WaitingLoadingHolder;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.R;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<PostCardModel> posts;

    public PostAdapter(Context context, List<PostCardModel> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TypeViewLoad.VIEW_TYPE_ITEM.ordinal()) {
            return new PostHolder(LayoutInflater.from(context).inflate(R.layout.post_view, parent, false));
        } else {
            return new WaitingLoadingHolder(LayoutInflater.from(context).inflate(R.layout.waiting_loading_view, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PostHolder) {
            PostHolder postHolder = (PostHolder) holder;
            PostCardModel postCardModel = posts.get(position);
            postHolder.fullName.setText(postCardModel.getFullName());
            postHolder.postingTimeAt.setText(postCardModel.getPostingTimeAt());

            //        holder.mode.setImageResource((int) postCardModel.getMode());
            postHolder.postText.setText(postCardModel.getPostText());

            //        postCardModel.getPostMedia().endsWith(".jpg");
            Glide.with(context)
                    .load(postCardModel.getAvatar())
                    .into(postHolder.avatar);

            Glide.with(context)
                    .load(postCardModel.getPostMedia())
                    .into(postHolder.postImage);

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
                    Intent intent = new Intent(context, CommentActivity.class);
                    // intent.putExtra("id", postId.getText());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
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

    @Override
    public int getItemViewType(int position) {
        if (posts.get(position) != null)
            return TypeViewLoad.VIEW_TYPE_ITEM.ordinal();
        return TypeViewLoad.VIEW_TYPE_LOADING.ordinal();
    }

    public void addItems(List<PostCardModel> postCardModels, RecyclerView recyclerView) {
        if (postCardModels.isEmpty()) return;

        PostAdapter.AsyncTaskUI asyncTaskUI = new PostAdapter.AsyncTaskUI(postCardModels, recyclerView);
        asyncTaskUI.execute();
    }

    private class AsyncTaskUI extends AsyncTask<Void, Integer, Void> {
        private List<PostCardModel> postCardModels;
        private RecyclerView recyclerView;

        public AsyncTaskUI(List<PostCardModel> messageModels, RecyclerView recyclerView) {
            this.postCardModels = messageModels;
            this.recyclerView = recyclerView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            publishProgress();
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            posts.addAll(postCardModels);
            notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }
    }

}
