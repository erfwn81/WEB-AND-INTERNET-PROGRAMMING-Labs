package cs3220.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Intercepts Spring Boot's /error endpoint to render custom error pages.
 * Replaces the default Whitelabel error page.
 *
 * For a 404: renders the 404.ftlh template.
 * For other errors: renders a generic error page.
 */
@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Read the HTTP status code Spring stored in the request
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("statusCode", statusCode);

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "404"; // resolves to templates/404.ftlh
            }
        }

        return "error"; // generic error template fallback
    }
}
