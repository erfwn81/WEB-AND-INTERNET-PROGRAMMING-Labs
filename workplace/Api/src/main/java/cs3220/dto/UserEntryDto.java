package cs3220.dto;

import cs3220.model.UserEntry;

public class UserEntryDto {
    private Long id;
    private String email;
    private String name;
    private String password; // only used for register/login requests; never sent back

    public UserEntryDto() {}

    public static UserEntryDto fromEntity(UserEntry u) {
        UserEntryDto dto = new UserEntryDto();
        dto.id = u.getId();
        dto.email = u.getEmail();
        dto.name = u.getName();
        return dto; // password intentionally omitted in response
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}