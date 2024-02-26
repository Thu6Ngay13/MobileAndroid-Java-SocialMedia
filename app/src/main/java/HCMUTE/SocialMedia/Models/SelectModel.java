package HCMUTE.SocialMedia.Models;

import android.widget.ImageButton;

import java.util.Objects;

import HCMUTE.SocialMedia.Enums.Select;

public class SelectModel {
    private Select id;
    private ImageButton ib;
    private int img;

    public SelectModel(Select id, ImageButton ib, int img) {
        this.id = id;
        this.ib = ib;
        this.img = img;
    }

    public Select getId() {
        return id;
    }

    public void setId(Select id) {
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
