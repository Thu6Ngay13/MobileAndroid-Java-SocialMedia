package HCMUTE.SocialMedia.Models;

import android.graphics.Bitmap;

import HCMUTE.SocialMedia.Enums.TypeMessageEnum;

public class MessageModel {
    private String fullname;
    private Bitmap media;
    private String messageSendingAt;
    private Boolean seen;
    private String text;
    private final TypeMessageEnum viewType;

    public MessageModel(TypeMessageEnum viewType, String fullname, String messageSendingAt, String text, Bitmap media) {
        this.viewType = viewType;
        this.fullname = fullname;
        this.messageSendingAt = messageSendingAt;
        this.text = text;
        this.media = media;
    }

    public TypeMessageEnum getViewType() {
        return this.viewType;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMessageSendingAt() {
        return this.messageSendingAt;
    }

    public void setMessageSendingAt(String messageSendingAt) {
        this.messageSendingAt = messageSendingAt;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getMedia() {
        return this.media;
    }

    public void setMedia(Bitmap media) {
        this.media = media;
    }

    public Boolean isSeen() {
        return this.seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

}