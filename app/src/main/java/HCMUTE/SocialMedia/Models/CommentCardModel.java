package HCMUTE.SocialMedia.Models;

import com.google.gson.annotations.SerializedName;

public class CommentCardModel {
    private String avatar;
    private String username;
    private String fullName;
    private Long commentId;
    private String commentText;
    private String commentImage;
    private Boolean isDeleted = false;
    private String commentTimeAt;
    private Long postId;

    public CommentCardModel(String avatar, String username, String fullName, Long commentId, String commentText, String commentImage, Boolean isDeleted, String commentTimeAt, Long postId) {
        this.avatar = avatar;
        this.username = username;
        this.fullName = fullName;
        this.commentId = commentId;
        this.commentText = commentText;
        this.commentImage = commentImage;
        this.isDeleted = isDeleted;
        this.commentTimeAt = commentTimeAt;
        this.postId = postId;
    }

    public CommentCardModel() {
    }

    public CommentCardModel(String avatar, String username, String fullName, String commentText, String commentImage, Boolean isDeleted, String commentTimeAt, Long postId) {
        this.avatar = avatar;
        this.username = username;
        this.fullName = fullName;
        this.commentText = commentText;
        this.commentImage = commentImage;
        this.isDeleted = isDeleted;
        this.commentTimeAt = commentTimeAt;
        this.postId = postId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentImage() {
        return commentImage;
    }

    public void setCommentImage(String commentImage) {
        this.commentImage = commentImage;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getCommentTimeAt() {
        return commentTimeAt;
    }

    public void setCommentTimeAt(String commentTimeAt) {
        this.commentTimeAt = commentTimeAt;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
