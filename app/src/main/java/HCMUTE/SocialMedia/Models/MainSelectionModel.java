package HCMUTE.SocialMedia.Models;

import android.widget.ImageButton;

import HCMUTE.SocialMedia.Enums.MainSelectionEnum;

public class MainSelectionModel {
    private MainSelectionEnum id;
    private ImageButton ib;
    private int img;

    public MainSelectionModel(MainSelectionEnum id, ImageButton ib, int img) {
        this.id = id;
        this.ib = ib;
        this.img = img;
    }

    public MainSelectionEnum getId() {
        return id;
    }

    public void setId(MainSelectionEnum id) {
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
