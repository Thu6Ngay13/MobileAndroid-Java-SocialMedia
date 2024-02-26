package HCMUTE.SocialMedia;

public class InteractModel {
    private boolean liked;
    private int interactIcon;
    private String interactDesc;

    public InteractModel(int interactIcon, String interactDesc) {
        this.interactIcon = interactIcon;
        this.interactDesc = interactDesc;
    }

    public int getInteractIcon() {
        return interactIcon;
    }

    public void setInteractIcon(int interactIcon) {
        this.interactIcon = interactIcon;
    }

    public String getInteractDesc() {
        return interactDesc;
    }

    public void setInteractDesc(String interactDesc) {
        this.interactDesc = interactDesc;
    }

    public boolean getLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
