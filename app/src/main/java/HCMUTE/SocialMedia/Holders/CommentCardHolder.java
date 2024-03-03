package HCMUTE.SocialMedia.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import HCMUTE.SocialMedia.R;

public class CommentCardHolder extends RecyclerView.ViewHolder {

    public ImageView avatar;

    public TextView fullName;
    public TextView commentText;
    public ImageView commentImage;
    public TextView commentTimeAt;

    public CommentCardHolder(@NonNull View itemView) {
        super(itemView);
        this.avatar = itemView.findViewById(R.id.ivAvatar);
        this.fullName = itemView.findViewById(R.id.tvFullName);
        this.commentTimeAt = itemView.findViewById(R.id.tvCommentTimeAt);
        this.commentText = itemView.findViewById(R.id.tvTextComment);
        this.commentImage = itemView.findViewById(R.id.ivCommentImage);
    }
}
