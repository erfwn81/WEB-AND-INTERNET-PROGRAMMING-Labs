package cs3220.servlet;

import cs3220.model.UserEntry;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Files;

/**
 * cs3220.servlet.Download
 * Handles GET /download       → show download page (session-protected)
 * Handles GET /download/file  → stream the image file as attachment
 *
 * If user is not logged in, redirect to /login.
 */
@Controller
public class DownloadController {

    // Image metadata: display name and file path under static/images/
    private static final String[][] IMAGES = {
        {"Apple",  "images/apples.jpg"},
        {"Orange", "images/oranges.jpg"},
        {"Banana", "images/bananas.jpg"}
    };

    /** Protected download listing page */
    @GetMapping("/download")
    public String showDownload(HttpSession session, Model model) {
        UserEntry user = (UserEntry) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("images", IMAGES);
        // Removed: model.addAttribute("user", user) — conflicts with session
        return "download";
    }

    /**
     * Stream a specific image as a download attachment.
     * @param file  the filename param, e.g. "images/apples.jpg"
     */
    @GetMapping("/download/file")
    public ResponseEntity<byte[]> downloadFile(
            @RequestParam String file,
            HttpSession session) throws IOException {

        // Session guard: 401 if not authenticated
        if (session.getAttribute("user") == null) {
            return ResponseEntity.status(401).build();
        }

        // Security: only allow known image paths (prevent path traversal)
        boolean allowed = false;
        for (String[] img : IMAGES) {
            if (img[1].equals(file)) {
                allowed = true;
                break;
            }
        }
        if (!allowed) {
            return ResponseEntity.badRequest().build();
        }

        // Read the file from static resources
        ClassPathResource resource = new ClassPathResource("static/" + file);
        byte[] data = Files.readAllBytes(resource.getFile().toPath());

        // Extract just the filename for the Content-Disposition header
        String filename = file.substring(file.lastIndexOf('/') + 1);

        return ResponseEntity.ok()
                // Forces browser to download rather than display inline
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.IMAGE_JPEG)
                .contentLength(data.length)
                .body(data);
    }
}
