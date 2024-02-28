package HCMUTE.SocialMedia.Models;

import java.util.List;

public class NotifyModel {
    private String NotifyTime;
    private List<NotifyCardModel> notifyCardModels;

    public NotifyModel(String notifyTime, List<NotifyCardModel> notifyCardModels) {
        NotifyTime = notifyTime;
        this.notifyCardModels = notifyCardModels;
    }

    public String getNotifyTime() {
        return NotifyTime;
    }

    public void setNotifyTime(String notifyTime) {
        NotifyTime = notifyTime;
    }

    public List<NotifyCardModel> getNotifyCardModels() {
        return notifyCardModels;
    }

    public void setNotifyCardModels(List<NotifyCardModel> notifyCardModels) {
        this.notifyCardModels = notifyCardModels;
    }
}
