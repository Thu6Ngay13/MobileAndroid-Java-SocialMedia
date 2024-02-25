package HCMUTE.SocialMedia;

import android.net.Uri;

public class PostModel {
    private int avatar;
    private String fullName;
    private String postingTimeAt;
    private int mode;
    private String postText;
    private int postImage;
    private int menu;
    private int like;
    private int comment;
    private int share;

    public PostModel(int avatar, String fullName, String postingTimeAt, int mode, String postText, int postImage, int menu, int like, int comment, int share) {
        this.avatar = avatar;
        this.fullName = fullName;
        this.postingTimeAt = postingTimeAt;
        this.mode = mode;
        this.postText = postText;
        this.postImage = postImage;
        this.menu = menu;
        this.like = like;
        this.comment = comment;
        this.share = share;
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

    public String getPostingTimeAt() {
        return postingTimeAt;
    }

    public void setPostingTimeAt(String postingTimeAt) {
        this.postingTimeAt = postingTimeAt;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public int getPostImage() {
        return postImage;
    }

    public void setPostImage(int postImage) {
        this.postImage = postImage;
    }

    public int getMenu() {
        return menu;
    }

    public void setMenu(int menu) {
        this.menu = menu;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }
}
