package cs3220.model;

/**
 * cs3220.model.UserEntry
 * Holds all user account information.
 * Properties exposed via getters for FreeMarker template access.
 */
public class UserEntry {

    private String email;
    private String name;
    private String password;

    public UserEntry() {}

    public UserEntry(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    // --- Getters & Setters (FreeMarker reads these as properties) ---

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
