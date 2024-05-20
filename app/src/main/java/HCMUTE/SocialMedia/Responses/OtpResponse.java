package HCMUTE.SocialMedia.Responses;

public class OtpResponse {
    private String message;
    private boolean success;
    private boolean error;

    public OtpResponse(String message, boolean success, boolean error) {
        this.message = message;
        this.success = success;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
