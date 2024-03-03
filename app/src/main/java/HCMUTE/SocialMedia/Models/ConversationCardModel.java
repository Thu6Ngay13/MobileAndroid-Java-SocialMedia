package HCMUTE.SocialMedia.Models;

public class ConversationCardModel {
    private int avatar;
    private String fullname;

    public ConversationCardModel(int avatar, String fullname) {
        this.avatar = avatar;
        this.fullname = fullname;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
