package cs3220.servlet;

import cs3220.model.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * cs3220.servlet.Register
 * Handles GET  /register → show registration form
 * Handles POST /register → validate & create user account
 *
 * Validation rules:
 *   - All fields (email, name, password) must be non-empty
 *   - Email must not already be registered
 */
@Controller
public class RegisterController {

    @Autowired
    private UserStore userStore;

    /** Show empty registration form */
    @GetMapping("/register")
    public String showRegister() {
        return "register"; // resolves to templates/register.ftlh
    }

    /** Process registration form */
    @PostMapping("/register")
    public String processRegister(
            @RequestParam(defaultValue = "") String email,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String password,
            Model model) {

        // Validate: all fields required
        if (email.isBlank() || name.isBlank() || password.isBlank()) {
            model.addAttribute("error", "All fields are required");
            // Preserve what the user already typed
            model.addAttribute("email", email);
            model.addAttribute("name", name);
            return "register";
        }

        // Attempt to register; returns false if email already taken
        boolean success = userStore.register(email, name, password);
        if (!success) {
            model.addAttribute("error", "Email already registered");
            model.addAttribute("email", email);
            model.addAttribute("name", name);
            return "register";
        }

        // Registration successful → redirect to login so user can sign in
        return "redirect:/login?registered=true";
    }
}
