package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import HCMUTE.SocialMedia.Holders.CommentCardHolder;
import HCMUTE.SocialMedia.Models.CommentCardModel;
import HCMUTE.SocialMedia.R;

public class CommentCardAdapter extends RecyclerView.Adapter<CommentCardHolder> {

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
        holder.avatar.setImageResource(commentCardModel.getAvatar());
        holder.fullName.setText(commentCardModel.getFullName());
        holder.commentText.setText(commentCardModel.getCommentText());
        holder.commentImage.setImageResource(commentCardModel.getCommentImage());
        holder.commentTimeAt.setText(commentCardModel.getCommentTimeAt());

    }

    @Override
    public int getItemCount() {
        if (commentCards != null)
            return commentCards.size();
        return 0;
    }
}
