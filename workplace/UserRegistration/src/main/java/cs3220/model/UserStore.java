package cs3220.model;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton Spring bean acting as the in-memory user database.
 * Keyed by email (lowercase) for fast O(1) lookup.
 * No real DB needed for this lab — data lives in memory per JVM session.
 */
@Component
public class UserStore {

    // email -> UserEntry map; initialized with no users
    private final Map<String, UserEntry> users = new HashMap<>();

    /**
     * Register a new user. Returns false if email already exists.
     */
    public boolean register(String email, String name, String password) {
        String key = email.toLowerCase().trim();
        if (users.containsKey(key)) {
            return false; // duplicate
        }
        users.put(key, new UserEntry(email.trim(), name.trim(), password));
        return true;
    }

    /**
     * Attempt login. Returns the UserEntry on success, null on failure.
     */
    public UserEntry login(String email, String password) {
        String key = email.toLowerCase().trim();
        UserEntry user = users.get(key);
        if (user == null) {
            return null; // user not found
        }
        if (!user.getPassword().equals(password)) {
            return null; // wrong password
        }
        return user;
    }

    /**
     * Check if a user exists by email.
     */
    public boolean exists(String email) {
        return users.containsKey(email.toLowerCase().trim());
    }
}
