package cs3220.controller;

import cs3220.model.GuestBookEntity;
import cs3220.model.UserEntity;
import cs3220.repository.GuestBookEntityRepository;
import cs3220.repository.UserEntityRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserEntityRepository userRepo;

    @Autowired
    private GuestBookEntityRepository guestBookRepo;

    // ============================================================
    // LOGIN PAGE
    // ============================================================

    /** Show the login form */
    @GetMapping("/")
    public String showLoginPage() {
        return "index";
    }

    /** Process login form submission */
    @PostMapping("/login")
    public String processLogin(
            @RequestParam(defaultValue = "") String email,
            @RequestParam(defaultValue = "") String password,
            HttpSession session,
            Model model) {

        boolean hasError = false;

        // Field-level validation
        if (email.trim().isEmpty()) {
            model.addAttribute("emailError", "Email is required");
            hasError = true;
        }
        if (password.trim().isEmpty()) {
            model.addAttribute("passwordError", "Password is required");
            hasError = true;
        }
        if (hasError) {
            model.addAttribute("email", email);
            return "index";
        }

        // Authenticate against DB
        UserEntity user = userRepo.findByEmailAndPassword(email.trim(), password);
        if (user == null) {
            model.addAttribute("loginError", "Email or Password incorrect");
            model.addAttribute("email", email);
            return "index";
        }

        // Store only id and name in session (avoids lazy-loading serialization issues)
        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getName());
        return "redirect:/guestbook";
    }

    // ============================================================
    // LOGOUT
    // ============================================================

    /** Invalidate session and redirect to login */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // ============================================================
    // REGISTER PAGE
    // ============================================================

    /** Show the registration form */
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    /** Process registration form submission */
    @PostMapping("/register")
    public String processRegister(
            @RequestParam(defaultValue = "") String email,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String password,
            Model model) {

        boolean hasError = false;

        // Field-level validation
        if (email.trim().isEmpty()) {
            model.addAttribute("emailError", "Email is required");
            hasError = true;
        }
        if (password.trim().isEmpty()) {
            model.addAttribute("passwordError", "Password is required");
            hasError = true;
        }
        if (hasError) {
            model.addAttribute("email", email);
            model.addAttribute("name", name);
            return "register";
        }

        // Check if email is already taken
        if (userRepo.findByEmail(email.trim()) != null) {
            model.addAttribute("registerError", "You are already registered");
            model.addAttribute("email", email);
            model.addAttribute("name", name);
            return "register";
        }

        // Save new user
        UserEntity newUser = new UserEntity(name.trim(), email.trim(), password);
        userRepo.save(newUser);

        // Redirect to login after successful registration
        return "redirect:/";
    }

    // ============================================================
    // GUESTBOOK PAGE
    // ============================================================

    /** Show all guestbook messages (requires login) */
    @GetMapping("/guestbook")
    public String showGuestBook(HttpSession session, Model model) {
        // Access control: redirect to login if not logged in
        if (!isLoggedIn(session)) return "redirect:/";

        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("messages", guestBookRepo.findAllByOrderByDateDesc());
        return "guestBook";
    }

    // ============================================================
    // ADD MESSAGE
    // ============================================================

    /** Show the add-message form (requires login) */
    @GetMapping("/guestbook/add")
    public String showAddMessagePage(HttpSession session, Model model) {
        if (!isLoggedIn(session)) return "redirect:/";
        model.addAttribute("userName", session.getAttribute("userName"));
        return "addMessage";
    }

    /** Process new message submission */
    @PostMapping("/guestbook/add")
    public String processAddMessage(
            @RequestParam(defaultValue = "") String message,
            HttpSession session,
            Model model) {

        if (!isLoggedIn(session)) return "redirect:/";

        // Message content validation
        if (message.trim().isEmpty()) {
            model.addAttribute("messageError", "Message is required");
            model.addAttribute("userName", session.getAttribute("userName"));
            return "addMessage";
        }

        // Load the current user from DB using id stored in session
        Long userId = (Long) session.getAttribute("userId");
        UserEntity user = userRepo.findById(userId).orElse(null);
        if (user == null) return "redirect:/";

        GuestBookEntity entry = new GuestBookEntity(message.trim(), LocalDate.now(), user);
        guestBookRepo.save(entry);

        return "redirect:/guestbook";
    }

    // ============================================================
    // EDIT MESSAGE
    // ============================================================

    /** Show the edit-message form pre-filled with existing content */
    @GetMapping("/guestbook/edit/{id}")
    public String showEditMessagePage(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        if (!isLoggedIn(session)) return "redirect:/";

        GuestBookEntity entry = guestBookRepo.findById(id).orElse(null);
        if (entry == null) return "redirect:/guestbook";

        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("entry", entry);
        return "editMessage";
    }

    /** Process edited message submission */
    @PostMapping("/guestbook/edit/{id}")
    public String processEditMessage(
            @PathVariable Long id,
            @RequestParam(defaultValue = "") String message,
            HttpSession session,
            Model model) {

        if (!isLoggedIn(session)) return "redirect:/";

        GuestBookEntity entry = guestBookRepo.findById(id).orElse(null);
        if (entry == null) return "redirect:/guestbook";

        // Validation
        if (message.trim().isEmpty()) {
            model.addAttribute("messageError", "Message is required");
            model.addAttribute("userName", session.getAttribute("userName"));
            model.addAttribute("entry", entry);
            return "editMessage";
        }

        entry.setMessage(message.trim());
        guestBookRepo.save(entry);

        return "redirect:/guestbook";
    }

    // ============================================================
    // DELETE MESSAGE
    // ============================================================

    /** Delete a guestbook entry by id (POST to prevent CSRF via GET) */
    @PostMapping("/guestbook/delete/{id}")
    public String deleteMessage(@PathVariable Long id, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/";
        guestBookRepo.deleteById(id);
        return "redirect:/guestbook";
    }

    // ============================================================
    // HELPER
    // ============================================================

    /** Returns true if a valid userId is present in the session */
    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("userId") != null;
    }
}
