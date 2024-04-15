package HCMUTE.SocialMedia.Models;

import java.util.List;

public class HomeModel {
    private List<PostCardModel> postCardModels;

    public HomeModel(List<PostCardModel> postCardModels) {
        this.postCardModels = postCardModels;
    }

    public List<PostCardModel> getPostModels() {
        return postCardModels;
    }

    public void setPostModels(List<PostCardModel> postCardModels) {
        this.postCardModels = postCardModels;
    }
}
