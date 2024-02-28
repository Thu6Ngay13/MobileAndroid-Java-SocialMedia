package HCMUTE.SocialMedia.Models;

public class NotifyCardModel {
    private int avatar;
    private String fullName;
    private String content;
    private String notifyTimeAt;

    public NotifyCardModel(int avatar, String fullName, String content, String notifyTimeAt) {
        this.avatar = avatar;
        this.fullName = fullName;
        this.content = content;
        this.notifyTimeAt = notifyTimeAt;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNotifyTimeAt() {
        return notifyTimeAt;
    }

    public void setNotifyTimeAt(String notifyTimeAt) {
        this.notifyTimeAt = notifyTimeAt;
    }
}
