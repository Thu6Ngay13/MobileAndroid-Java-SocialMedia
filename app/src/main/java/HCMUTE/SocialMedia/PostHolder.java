package HCMUTE.SocialMedia;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostHolder extends RecyclerView.ViewHolder{
    public ImageView avatar;
    public TextView fullName;
    public TextView postingTimeAt;
    public ImageView mode;
    public TextView postText;
    public ImageView postImage;

    public PostHolder(@NonNull View itemView) {
        super(itemView);
        this.avatar = itemView.findViewById(R.id.ivAvatar);
        this.fullName = itemView.findViewById(R.id.tvFullName);
        this.postingTimeAt = itemView.findViewById(R.id.tvPostingTimeAt);
        this.mode = itemView.findViewById(R.id.ivMode);
        this.postText = itemView.findViewById(R.id.tvPostText);
        this.postImage = itemView.findViewById(R.id.ivPostImage);
    }
}
