package HCMUTE.SocialMedia.Models;

public class FriendRequestModel {
    private int avatar;
    private String fullName;
    private String requestTimeAt;

    public FriendRequestModel(int avatar, String fullName, String requestTimeAt) {
        this.avatar = avatar;
        this.fullName = fullName;
        this.requestTimeAt = requestTimeAt;
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

    public String getRequestTimeAt() {
        return requestTimeAt;
    }

    public void setRequestTimeAt(String requestTimeAt) {
        this.requestTimeAt = requestTimeAt;
    }
}
