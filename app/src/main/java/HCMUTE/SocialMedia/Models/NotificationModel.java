package HCMUTE.SocialMedia.Models;

public class NotificationModel {
	private String avatar;
	private String username;
    private String fullName;
    private String text;
    private Boolean isSeen;
    private String notifyTimeAt;

    public NotificationModel(String avatar, String username, String fullName, String text, Boolean isSeen, String notifyTimeAt) {
        this.avatar = avatar;
        this.username = username;
        this.fullName = fullName;
        this.text = text;
        this.isSeen = isSeen;
        this.notifyTimeAt = notifyTimeAt;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getSeen() {
        return isSeen;
    }

    public void setSeen(Boolean seen) {
        isSeen = seen;
    }

    public String getNotifyTimeAt() {
        return notifyTimeAt;
    }

    public void setNotifyTimeAt(String notifyTimeAt) {
        this.notifyTimeAt = notifyTimeAt;
    }
}
