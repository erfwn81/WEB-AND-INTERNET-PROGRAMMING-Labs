package cs3220.model;

public class UserEntry {

    private String email;
    private String password;

    public UserEntry(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}