package HCMUTE.SocialMedia.Models;

import java.util.List;

public class HomeModel {
    private List<PostModel> postModels;

    public HomeModel(List<PostModel> postModels) {
        this.postModels = postModels;
    }

    public List<PostModel> getPostModels() {
        return postModels;
    }

    public void setPostModels(List<PostModel> postModels) {
        this.postModels = postModels;
    }
}
