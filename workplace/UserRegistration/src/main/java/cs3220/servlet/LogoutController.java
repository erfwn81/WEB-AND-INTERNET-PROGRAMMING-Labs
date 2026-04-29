package cs3220.servlet;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * cs3220.servlet.Logout
 * Handles GET /logout → invalidates entire session, redirects to login.
 *
 * session.invalidate() is the proper way to destroy a session —
 * it removes all attributes and marks the session as invalid.
 */
@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Destroy the session completely (not just remove one attribute)
        session.invalidate();
        // Send user back to login page
        return "redirect:/login";
    }
}
