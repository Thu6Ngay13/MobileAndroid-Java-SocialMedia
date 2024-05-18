package HCMUTE.SocialMedia.Models;

import HCMUTE.SocialMedia.Enums.TypeSearchEnum;

public class SearchModel {
    private TypeSearchEnum viewType;
    private String avatar;
    private long groupdId;
    private String username;
    private String fullName;
    private String requestTimeAt;

    public SearchModel(TypeSearchEnum viewType, String avatar, long groupdId, String username, String fullName, String requestTimeAt) {
        this.viewType = viewType;
        this.avatar = avatar;
        this.groupdId = groupdId;
        this.username = username;
        this.fullName = fullName;
        this.requestTimeAt = requestTimeAt;
    }

    public TypeSearchEnum getViewType() {
        return viewType;
    }

    public void setViewType(TypeSearchEnum viewType) {
        this.viewType = viewType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getGroupdId() {
        return groupdId;
    }

    public void setGroupdId(long groupdId) {
        this.groupdId = groupdId;
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
