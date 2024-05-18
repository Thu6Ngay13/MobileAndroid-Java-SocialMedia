package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
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

import HCMUTE.SocialMedia.Consts.Const;
import HCMUTE.SocialMedia.Enums.TypeViewLoad;
import HCMUTE.SocialMedia.Holders.PostHolder;
import HCMUTE.SocialMedia.Holders.WaitingLoadingHolder;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.Utils.ProcessTime;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

            String timeInput = postCardModel.getPostingTimeAt();
            List<String> timeResult = ProcessTime.getTimeFromString(timeInput);
            String timeShow = timeResult.get(0) + "-" + timeResult.get(1) + "-" + timeResult.get(2) + " " + timeResult.get(3) + ":" + timeResult.get(4);
            postHolder.postingTimeAt.setText(timeShow);

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
            ImageView ivLike = (ImageView) cvLike.findViewById(R.id.ivLike);

            if (postCardModel.isLiked()) {
                ivLike.setImageResource(R.mipmap.ic_like_72_full);
            } else {
                ivLike.setImageResource(R.mipmap.ic_like_72_line);
            }

            CardView cvComment = (CardView) holder.itemView.findViewById(R.id.cvComment);
            CardView cvShare = (CardView) holder.itemView.findViewById(R.id.cvShare);
            ImageView ivMenu = (ImageView) holder.itemView.findViewById(R.id.ivMenu);

            cvLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (postCardModel.isLiked()) {
                        APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                        apiService.unlikePost(Const.USERNAME, postCardModel.getPostId()).enqueue(new Callback<ResponseModel<String>>() {
                            @Override
                            public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                                if (response.isSuccessful()) {
                                    ResponseModel<String> responseModel = response.body();

                                    postCardModel.setLiked(false);
                                    ivLike.setImageResource(R.mipmap.ic_like_72_line);
                                } else {
                                    int statusCode = response.code();
                                    // handle request errors depending on status code
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                            }
                        });

                    } else {
                        APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                        apiService.likePost(Const.USERNAME, postCardModel.getPostId()).enqueue(new Callback<ResponseModel<String>>() {
                            @Override
                            public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                                if (response.isSuccessful()) {
                                    ResponseModel<String> responseModel = response.body();

                                    postCardModel.setLiked(true);
                                    ivLike.setImageResource(R.mipmap.ic_like_72_full);
                                } else {
                                    int statusCode = response.code();
                                    // handle request errors depending on status code
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                            }
                        });
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
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                    apiService.sharePost(Const.USERNAME, postCardModel.getPostId()).enqueue(new Callback<ResponseModel<String>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                            if (response.isSuccessful()) {
                                ResponseModel<String> responseModel = response.body();
                                Toast.makeText(context, "Share successful", Toast.LENGTH_SHORT).show();
                            } else {
                                int statusCode = response.code();
                                // handle request errors depending on status code
                                Toast.makeText(context, "Share failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                        }
                    });
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
            int start = getItemCount() - 1;
            posts.addAll(postCardModels);
            notifyItemRangeInserted(start, postCardModels.size());
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }
    }

}
