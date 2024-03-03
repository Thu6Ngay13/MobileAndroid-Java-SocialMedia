package HCMUTE.SocialMedia.Models;

public class PolicyModel {
    private int id;
    private String detail;

    public PolicyModel(int id, String detail) {
        this.id = id;
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
