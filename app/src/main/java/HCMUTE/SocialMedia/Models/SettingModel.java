package HCMUTE.SocialMedia.Models;

import java.util.List;

public class SettingModel {

    private int avatar;
    private String fullName;
    private List<SettingCardModel> settingCardModels;

    public SettingModel(int avatar, String fullName, List<SettingCardModel> settingCardModels) {
        this.avatar = avatar;
        this.fullName = fullName;
        this.settingCardModels = settingCardModels;
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

    public List<SettingCardModel> getSettingCardModels() {
        return settingCardModels;
    }

    public void setSettingCardModels(List<SettingCardModel> settingCardModels) {
        this.settingCardModels = settingCardModels;
    }
}
