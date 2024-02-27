package HCMUTE.SocialMedia.Models;

import java.util.List;

public class FriendModel {
    private List<FriendRequestModel> friendRequestModels;
    private List<YourFriendModel> yourFriendModels;
    private boolean yourFriendNow;

    public FriendModel(List<FriendRequestModel> friendRequestModels, List<YourFriendModel> yourFriendModels) {
        this.friendRequestModels = friendRequestModels;
        this.yourFriendModels = yourFriendModels;
    }

    public List<FriendRequestModel> getFriendRequestModels() {
        return friendRequestModels;
    }

    public void setFriendRequestModels(List<FriendRequestModel> friendRequestModels) {
        this.friendRequestModels = friendRequestModels;
    }

    public List<YourFriendModel> getYourFriendModels() {
        return yourFriendModels;
    }

    public void setYourFriendModels(List<YourFriendModel> yourFriendModels) {
        this.yourFriendModels = yourFriendModels;
    }

    public boolean isYourFriendNow() {
        return yourFriendNow;
    }

    public void setYourFriendNow(boolean yourFriendNow) {
        this.yourFriendNow = yourFriendNow;
    }
}
