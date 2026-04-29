package cs3220.servlet;

import cs3220.model.UserEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns = "/Login", loadOnStartup = 1)
public class Login extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private List<UserEntry> users = new ArrayList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);  // FIX: was missing

        users.add(new UserEntry("admin@csula.edu", "1234"));
        users.add(new UserEntry("user@csula.edu", "1234"));
        users.add(new UserEntry("mirzaee.erfan81@gmail.com", "1234"));
        users.add(new UserEntry("john@gmail.com", "1234"));
    }

    // FIX: doGet was completely missing
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        for (UserEntry user : users) {
            if (user.getEmail().equals(email) &&
                user.getPassword().equals(password)) {

                HttpSession session = request.getSession();
                session.setAttribute("user", email);

                response.sendRedirect("GuestBook");
                return;
            }
        }

        response.sendRedirect("index.jsp?error=1");
    }
}