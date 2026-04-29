package cs3220.servlet;

import cs3220.model.UserEntry;
import cs3220.model.UserStore;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * cs3220.servlet.Login
 * Handles GET /login  → show login form
 * Handles POST /login → process credentials
 *
 * On success: creates session, redirects to /download
 * On failure: re-renders login with error message
 */
@Controller
public class LoginController {

    @Autowired
    private UserStore userStore;

    /** Show the login page */
    @GetMapping("/login")
    public String showLogin(HttpSession session,
                            @RequestParam(required = false) String registered,
                            Model model) {
        // If already logged in, skip the login page
        if (session.getAttribute("user") != null) {
            return "redirect:/download";
        }
        if (registered != null) {
            model.addAttribute("registered", true);
        }
        return "login"; // resolves to templates/login.ftlh
    }

    /** Handle login form submission */
    @PostMapping("/login")
    public String processLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        // Attempt authentication against in-memory store
        UserEntry user = userStore.login(email, password);

        if (user == null) {
            // Distinguish "user not found" vs "wrong password"
            if (!userStore.exists(email)) {
                model.addAttribute("error", "User not found");
            } else {
                model.addAttribute("error", "Incorrect password");
            }
            model.addAttribute("email", email); // preserve input
            return "login"; // re-render form with error
        }

        // SUCCESS: create server-side session with user object
        session.setAttribute("user", user);
        // Redirect to download page (PRG pattern prevents re-POST on refresh)
        return "redirect:/download";
    }

    /** Root URL redirects to login */
    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }
}