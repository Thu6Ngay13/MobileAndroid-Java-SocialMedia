package HCMUTE.SocialMedia.Models;

public class YourFriendModel {
    private String avatar;
    private String username;

    public YourFriendModel() {
    }

    public YourFriendModel(String avatar, String username) {
        this.avatar = avatar;
        this.username = username;
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

    public void setUsername(String fullName) {
        this.username = fullName;
    }

}
