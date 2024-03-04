package HCMUTE.SocialMedia.Models;

import HCMUTE.SocialMedia.Enums.TypeReceiveMessageEnum;

public class MessageModel {

    private final TypeReceiveMessageEnum viewType;
    private int avatar;
    private String fullname;
    private String messageSendingAt;
    private String text;
    private int media;
    private Boolean seen;

    public MessageModel(TypeReceiveMessageEnum viewType, int avatar, String fullname, String messageSendingAt, String text, int media, Boolean seen) {
        this.viewType = viewType;
        this.avatar = avatar;
        this.fullname = fullname;
        this.messageSendingAt = messageSendingAt;
        this.text = text;
        this.media = media;
        this.seen = seen;
    }

    public TypeReceiveMessageEnum getViewType() {
        return viewType;
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

    public String getMessageSendingAt() {
        return messageSendingAt;
    }

    public void setMessageSendingAt(String messageSendingAt) {
        this.messageSendingAt = messageSendingAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMedia() {
        return media;
    }

    public void setMedia(int media) {
        this.media = media;
    }

    public Boolean isSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }
}