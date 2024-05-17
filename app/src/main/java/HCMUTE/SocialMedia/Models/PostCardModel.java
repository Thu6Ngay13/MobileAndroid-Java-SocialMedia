package HCMUTE.SocialMedia.Models;

import HCMUTE.SocialMedia.Enums.TypeViewLoad;

public class PostCardModel {
    private long postId;
    private String avatar;
    private String username;
    private String fullName;
    private String postingTimeAt;
    private long mode;
    private String postText;
    private String postMedia;
    private boolean liked;
    private long groupId;

    public PostCardModel() {
    }

    public PostCardModel(String avatar, String username, String fullName, String postingTimeAt, long mode, String postText, String postMedia, boolean liked) {
        this.avatar = avatar;
        this.username = username;
        this.fullName = fullName;
        this.postingTimeAt = postingTimeAt;
        this.mode = mode;
        this.postText = postText;
        this.postMedia = postMedia;
        this.liked = liked;
    }
    public PostCardModel(long postId, String avatar, String username, String fullName, String postingTimeAt, long mode, String postText, String postMedia, boolean liked) {
        this.postId = postId;
        this.avatar = avatar;
        this.username = username;
        this.fullName = fullName;
        this.postingTimeAt = postingTimeAt;
        this.mode = mode;
        this.postText = postText;
        this.postMedia = postMedia;
        this.liked = liked;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
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

    public String getPostingTimeAt() {
        return postingTimeAt;
    }

    public void setPostingTimeAt(String postingTimeAt) {
        this.postingTimeAt = postingTimeAt;
    }

    public long getMode() {
        return mode;
    }

    public void setMode(long mode) {
        this.mode = mode;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostMedia() {
        return postMedia;
    }

    public void setPostMedia(String postMedia) {
        this.postMedia = postMedia;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public long getGroupId() {
        return groupId;
    }
    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

}
