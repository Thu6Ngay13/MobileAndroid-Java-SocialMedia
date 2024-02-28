package HCMUTE.SocialMedia.Models;

public class SettingCardModel {
    private int imageFunction;
    private String funtionName;

    public SettingCardModel(int imageFunction, String funtionName) {
        this.imageFunction = imageFunction;
        this.funtionName = funtionName;
    }

    public int getImageFunction() {
        return imageFunction;
    }

    public void setImageFunction(int imageFunction) {
        this.imageFunction = imageFunction;
    }

    public String getFuntionName() {
        return funtionName;
    }

    public void setFuntionName(String funtionName) {
        this.funtionName = funtionName;
    }
}
