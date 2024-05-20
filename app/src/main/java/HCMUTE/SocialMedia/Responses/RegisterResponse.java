package HCMUTE.SocialMedia.Responses;

public class RegisterResponse {
    private String message;
    private boolean error;
    private boolean success;

    public RegisterResponse(String message, boolean error, boolean success) {
        this.message = message;
        this.error = error;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
