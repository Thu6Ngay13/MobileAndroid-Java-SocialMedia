package HCMUTE.SocialMedia.Models;

import java.time.LocalDateTime;

import HCMUTE.SocialMedia.Enums.Role;

public class AccountCardModel {
    private String accessToken;
    private String fullName;
    private String username;
    private String email;
    private String avatarurl;
    private String location;
    private LocalDateTime dateOfBirth;
    private String description;
    private String gender;
    private Role role;

    public AccountCardModel() {
    }

    public AccountCardModel(String accessToken, String fullName, String username, String email, String avatarurl, String location, LocalDateTime dateOfBirth, String description, String gender, Role role) {
        this.accessToken = accessToken;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.avatarurl = avatarurl;
        this.location = location;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.gender = gender;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
