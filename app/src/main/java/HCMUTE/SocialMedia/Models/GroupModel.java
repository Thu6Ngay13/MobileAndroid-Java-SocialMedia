package HCMUTE.SocialMedia.Models;

public class GroupModel {
    private long groupId;
    private String groupName;
    private String avatarURL;
    private String creationTimeAt;
    private long modeId;
    private String holderFullName;
    private String holderUsername;
    private String description;

    public GroupModel() {
    }

    public GroupModel(long groupId, String groupName, String avatarURL, String creationTimeAt, long modeId, String holderFullName, String holderUsername) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.avatarURL = avatarURL;
        this.creationTimeAt = creationTimeAt;
        this.modeId = modeId;
        this.holderFullName = holderFullName;
        this.holderUsername = holderUsername;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getCreationTimeAt() {
        return creationTimeAt;
    }

    public void setCreationTimeAt(String creationTimeAt) {
        this.creationTimeAt = creationTimeAt;
    }

    public long getModeId() {
        return modeId;
    }

    public void setModeId(long modeId) {
        this.modeId = modeId;
    }

    public String getHolderFullName() {
        return holderFullName;
    }

    public void setHolderFullName(String holderFullName) {
        this.holderFullName = holderFullName;
    }

    public String getHolderUsername() {
        return holderUsername;
    }

    public void setHolderUsername(String holderUsername) {
        this.holderUsername = holderUsername;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
