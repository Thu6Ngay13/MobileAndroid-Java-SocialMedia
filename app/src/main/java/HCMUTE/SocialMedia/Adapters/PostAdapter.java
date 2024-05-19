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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import HCMUTE.SocialMedia.Activities.CommentActivity;
import HCMUTE.SocialMedia.Activities.MyPersonalPageActivity;
import HCMUTE.SocialMedia.Activities.YourPersonalPageActivity;
import HCMUTE.SocialMedia.Enums.TypeViewLoad;
import HCMUTE.SocialMedia.Holders.PostHolder;
import HCMUTE.SocialMedia.Holders.WaitingLoadingHolder;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;
import HCMUTE.SocialMedia.Utils.ProcessTime;
import okhttp3.MediaType;
import okhttp3.RequestBody;
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
            postHolder.postText.setText(postCardModel.getPostText());

            Glide.with(context)
                    .load(postCardModel.getAvatar())
                    .into(postHolder.avatar);
            postHolder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (postCardModel.getUsername().equals(PrefManager.getUsername())) {
                        Intent intent = new Intent(context, MyPersonalPageActivity.class);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, YourPersonalPageActivity.class);
                        intent.putExtra("YOUR_FRIEND_USERNAME", postCardModel.getUsername());
                        context.startActivity(intent);
                    }
                }
            });

            Glide.with(context)
                    .load(postCardModel.getPostMedia())
                    .into(postHolder.postImage);
            if (postCardModel.getMode() == 1) {
                postHolder.mode.setImageResource(R.mipmap.ic_global_72_dark);
            } else if (postCardModel.getMode() == 2) {
                postHolder.mode.setImageResource(R.mipmap.ic_friend_72_full);
            } else if (postCardModel.getMode() == 3) {
                postHolder.mode.setImageResource(R.mipmap.ic_lock_72_dark);
            } else {
                postHolder.mode.setImageResource(R.mipmap.ic_user_groups_72_full);
            }

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
                        apiService.unlikePost(PrefManager.getUsername(), postCardModel.getPostId()).enqueue(new Callback<ResponseModel<String>>() {
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
                        apiService.likePost(PrefManager.getUsername(), postCardModel.getPostId()).enqueue(new Callback<ResponseModel<String>>() {
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
                    Intent intent = new Intent(context, CommentActivity.class);
                    intent.putExtra("postId", String.valueOf(postCardModel.getPostId()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            cvShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                    apiService.sharePost(PrefManager.getUsername(), postCardModel.getPostId()).enqueue(new Callback<ResponseModel<String>>() {
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
                    showPopupMenu(v, postCardModel);
                }
            });
        }
    }

    private void showPopupMenu(View view, PostCardModel postCardModel) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_post, popupMenu.getMenu());

        MenuItem iReport = popupMenu.getMenu().findItem(R.id.iReport);
        MenuItem editPostItem = popupMenu.getMenu().findItem(R.id.iEditPost);

        iReport.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("contentReport", "Spam");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                RequestBody jsonBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

                APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                apiService.reportPost(PrefManager.getUsername(), postCardModel.getPostId(), jsonBody).enqueue(new Callback<ResponseModel<String>>() {
                    @Override
                    public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                        if (response.isSuccessful()) {
                            ResponseModel<String> responseModel = response.body();
                            Toast.makeText(context, "Report successful", Toast.LENGTH_SHORT).show();
                        } else {
                            int statusCode = response.code();
                            // handle request errors depending on status code
                            Toast.makeText(context, "Report failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                    }
                });
                return false;
            }
        });

        // Kiểm tra điều kiện và ẩn mục menu nếu cần
        if (!postCardModel.getUsername().equals(PrefManager.getUsername())) {
            editPostItem.setVisible(false);
        }

        // Kiểm tra điều kiện và ẩn mục menu nếu cần
        if (postCardModel.getUsername().equals(PrefManager.getUsername())) {
            iReport.setVisible(false);
        }

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
