package HCMUTE.SocialMedia.Models;

import java.util.List;

public class ResponseModel<T> {
    private boolean success;
    private String message;
    private List<T> result;

    public ResponseModel(boolean success, String message, List<T> result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
