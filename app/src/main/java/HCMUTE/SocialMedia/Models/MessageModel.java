package HCMUTE.SocialMedia.Models;

import android.graphics.Bitmap;

import HCMUTE.SocialMedia.Enums.TypeMessageEnum;

public class MessageModel {
    private final TypeMessageEnum viewType;
    private String fullname;
    private String messageSendingAt;
    private String text;
    private Bitmap media;
    private Boolean seen;

    public MessageModel(TypeMessageEnum viewType, String fullname, String messageSendingAt, String text, Bitmap media) {
        this.viewType = viewType;
        this.fullname = fullname;
        this.messageSendingAt = messageSendingAt;
        this.text = text;
        this.media = media;
    }

    public TypeMessageEnum getViewType() {
        return viewType;
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

    public Bitmap getMedia() {
        return media;
    }

    public void setMedia(Bitmap media) {
        this.media = media;
    }

    public Boolean isSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }
}