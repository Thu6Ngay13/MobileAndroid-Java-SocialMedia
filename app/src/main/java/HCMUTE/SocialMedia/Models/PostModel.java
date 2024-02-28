package HCMUTE.SocialMedia.Models;

public class PostModel {
    private int avatar;
    private String fullName;
    private String postingTimeAt;
    private int mode;
    private String postText;
    private int postImage;
    private boolean liked;

    public PostModel(int avatar, String fullName, String postingTimeAt, int mode, String postText, int postImage, boolean liked) {
        this.avatar = avatar;
        this.fullName = fullName;
        this.postingTimeAt = postingTimeAt;
        this.mode = mode;
        this.postText = postText;
        this.postImage = postImage;
        this.liked = liked;
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

    public boolean getLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
