package HCMUTE.SocialMedia.Models;

import java.io.Serializable;
import java.time.LocalDateTime;

import HCMUTE.SocialMedia.Enums.Role;

public class AccountCardModel implements Serializable {
    private String username;
    private String fullname;
    private String gender;
    private String avatarURL;
    private String email;
    private String phoneNumber;
    private String description;
    private String company;
    private String location;
    private boolean isSingle;
    private Role role;

    public AccountCardModel() {
    }

    public AccountCardModel(String username, String fullname, String gender, String avatarURL, String email, String phoneNumber, String description, String company, String location, boolean isSingle, Role role) {
        this.username = username;
        this.fullname = fullname;
        this.gender = gender;
        this.avatarURL = avatarURL;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.company = company;
        this.location = location;
        this.isSingle = isSingle;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
