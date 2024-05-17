package HCMUTE.SocialMedia.Models;

import com.google.gson.annotations.SerializedName;

public class CommentCardModel {
    private int avatar;
    private String fullName;
    private String commentText;
    private int commentImage;
    private String commentTimeAt;


    public CommentCardModel(int avatar, String fullName, String commentText, int commentImage, String commentTimeAt) {
        this.avatar = avatar;
        this.fullName = fullName;
        this.commentText = commentText;
        this.commentImage = commentImage;
        this.commentTimeAt = commentTimeAt;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getCommentImage() {
        return commentImage;
    }

    public void setCommentImage(int commentImage) {
        this.commentImage = commentImage;
    }

    public String getCommentTimeAt() {
        return commentTimeAt;
    }

    public void setCommentTimeAt(String commentTimeAt) {
        this.commentTimeAt = commentTimeAt;
    }
}
