package HCMUTE.SocialMedia.Models;

import java.util.List;

public class CommentModel {
    private List<CommentCardModel> commentCardModels;

    public CommentModel(List<CommentCardModel> commentCardModels) {
        this.commentCardModels = commentCardModels;
    }

    public List<CommentCardModel> getCommentCardModels() {
        return commentCardModels;
    }

    public void setCommentCardModels(List<CommentCardModel> commentCardModels) {
        this.commentCardModels = commentCardModels;
    }
}
