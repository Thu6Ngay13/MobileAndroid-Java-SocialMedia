package HCMUTE.SocialMedia.Models;

import HCMUTE.SocialMedia.Enums.TypeBanAccountEnum;

public class BanAccountModel {
    private TypeBanAccountEnum viewType;
    private String avatar;
    private String username;
    private String fullname;
    private boolean isBanned;

    public BanAccountModel(TypeBanAccountEnum viewType, String avatar, String username, String fullname, boolean isBanned) {
        this.viewType = viewType;
        this.avatar = avatar;
        this.username = username;
        this.fullname = fullname;
        this.isBanned = isBanned;
    }

    public TypeBanAccountEnum getViewType() {
        return viewType;
    }

    public void setViewType(TypeBanAccountEnum viewType) {
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }
}
