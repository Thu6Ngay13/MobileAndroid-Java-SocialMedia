package HCMUTE.SocialMedia.Models;

import HCMUTE.SocialMedia.Enums.TypeFriendEnum;

public class FriendModel {
	private TypeFriendEnum viewType;
    private String avatar;
    private String username;
    private String fullName;
    private String requestTimeAt;

    public FriendModel(TypeFriendEnum viewType, String avatar, String username, String fullName, String requestTimeAt) {
        this.viewType = viewType;
        this.avatar = avatar;
        this.username = username;
        this.fullName = fullName;
        this.requestTimeAt = requestTimeAt;
    }

    public TypeFriendEnum getViewType() {
        return viewType;
    }

    public void setViewType(TypeFriendEnum viewType) {
        this.viewType = viewType;
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
