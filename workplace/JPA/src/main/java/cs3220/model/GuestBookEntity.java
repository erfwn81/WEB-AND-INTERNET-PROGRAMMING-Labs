package cs3220.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "guestbook")
public class GuestBookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDate date;

    // Many guestbook entries belong to one user
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    // ---- Constructors ----

    public GuestBookEntity() {}

    public GuestBookEntity(String message, LocalDate date, UserEntity user) {
        this.message = message;
        this.date = date;
        this.user = user;
    }

    // ---- Formatted date for Freemarker templates ----
    // Returns "22 April 2026" format without needing freemarker-java-8 library
    public String getFormattedDate() {
        if (date == null) return "";
        return date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
    }

    // ---- Getters & Setters ----

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }
}
