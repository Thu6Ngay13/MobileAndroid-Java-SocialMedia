package HCMUTE.SocialMedia.Models;

public class FriendRequestModel {
    private String avatar;
    private String username;
    private String fullName;
    private String requestTimeAt;

    public FriendRequestModel(String avatar, String username, String fullName, String requestTimeAt) {
        this.avatar = avatar;
        this.username = username;
        this.fullName = fullName;
        this.requestTimeAt = requestTimeAt;
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

    public String getRequestTimeAt() {
        return requestTimeAt;
    }

    public void setRequestTimeAt(String requestTimeAt) {
        this.requestTimeAt = requestTimeAt;
    }
}
