package HCMUTE.SocialMedia.Requests;

public class RegisterRequest {
    private String username;
    private String fullname;
    private String dateOfBirth;
    private String email;
    private String password;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String fullname, String dateOfBirth, String email, String password) {
        this.username = username;
        this.fullname = fullname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
