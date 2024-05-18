package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Activities.CommentActivity;
import HCMUTE.SocialMedia.Holders.CommentCardHolder;
import HCMUTE.SocialMedia.Models.CommentCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentCardAdapter extends RecyclerView.Adapter<CommentCardHolder> {
    private APIService apiService;
    private Context context;
    private List<CommentCardModel> commentCards;

    public CommentCardAdapter(Context context, List<CommentCardModel> commentCards) {
        this.context = context;
        this.commentCards = commentCards;
    }

    @NonNull
    @Override
    public CommentCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentCardHolder(LayoutInflater.from(context).inflate(R.layout.comment_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentCardHolder holder, int position) {

        CommentCardModel commentCardModel = commentCards.get(position);
        Glide.with(context).load(commentCardModel.getAvatar()).into(holder.avatar);
        holder.fullName.setText(commentCardModel.getFullName());
        holder.commentText.setText(commentCardModel.getCommentText());
        if (!TextUtils.isEmpty(commentCardModel.getCommentImage())) {
            holder.commentImage.setVisibility(View.VISIBLE);
            Glide.with(context).load(commentCardModel.getCommentImage()).into(holder.commentImage);
        } else {
            holder.commentImage.setVisibility(View.GONE);
        }
        holder.commentTimeAt.setText(commentCardModel.getCommentTimeAt());
        ImageButton ibEditComment = (ImageButton) holder.itemView.findViewById(R.id.ibEditComment);

        ImageButton ibDeleteComment = (ImageButton) holder.itemView.findViewById(R.id.ibDeleteComment);
        ibDeleteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService = RetrofitClient.getRetrofit().create(APIService.class);
                Call<CommentCardModel> call = apiService.deleteComment(commentCardModel.getCommentId());
                call.enqueue(new Callback<CommentCardModel>() {
                    @Override
                    public void onResponse(Call<CommentCardModel> call, Response<CommentCardModel> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context,"Delete comment successful", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(context, CommentActivity.class);
                            intent.putExtra("postId", String.valueOf(commentCardModel.getPostId()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        else {
                            Toast.makeText(context, "Vui lòng thêm nội dung!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<CommentCardModel> call, Throwable t) {
                        Log.d("Activity", "Request failed: " + t.getMessage());
                    }
                });
            }
        });


    }



    @Override
    public int getItemCount() {
        if (commentCards != null)
            return commentCards.size();
        return 0;
    }
}
