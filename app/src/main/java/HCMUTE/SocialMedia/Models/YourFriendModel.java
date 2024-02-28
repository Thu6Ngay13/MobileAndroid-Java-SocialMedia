package HCMUTE.SocialMedia.Models;

public class YourFriendModel {
    private int avatar;
    private String fullName;

    public YourFriendModel(int avatar, String fullName) {
        this.avatar = avatar;
        this.fullName = fullName;
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
}
