package HCMUTE.SocialMedia.Models;

import android.widget.ImageButton;

import HCMUTE.SocialMedia.Enums.MainSelection;

public class MainSelectionModel {
    private MainSelection id;
    private ImageButton ib;
    private int img;

    public MainSelectionModel(MainSelection id, ImageButton ib, int img) {
        this.id = id;
        this.ib = ib;
        this.img = img;
    }

    public MainSelection getId() {
        return id;
    }

    public void setId(MainSelection id) {
        this.id = id;
    }

    public ImageButton getIb() {
        return ib;
    }

    public void setIb(ImageButton ib) {
        this.ib = ib;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

}
