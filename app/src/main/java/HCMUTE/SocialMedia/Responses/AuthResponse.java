package HCMUTE.SocialMedia.Responses;

import HCMUTE.SocialMedia.Enums.TypeRoleEnum;

public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private boolean error;
    private boolean success;
    private String message;
    private String fullName;
    private String username;
    private String email;
    private String avatarurl;
    private TypeRoleEnum typeRoleEnum;

    public AuthResponse(String accessToken, String refreshToken, boolean error, boolean success, String message, String fullName, String username, String email, String avatarurl, TypeRoleEnum typeRoleEnum) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.error = error;
        this.success = success;
        this.message = message;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.avatarurl = avatarurl;
        this.typeRoleEnum = typeRoleEnum;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public TypeRoleEnum getRole() {
        return typeRoleEnum;
    }

    public void setRole(TypeRoleEnum typeRoleEnum) {
        this.typeRoleEnum = typeRoleEnum;
    }
}
