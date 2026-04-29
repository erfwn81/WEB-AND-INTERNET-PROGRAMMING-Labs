package cs3220.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs3220.model.BirthdayEntity;
import cs3220.model.UserEntity;

@WebServlet(urlPatterns = "/Login", loadOnStartup = 1)
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Login() {
        super();
    }

    /**
     * init() runs at startup because loadOnStartup = 1
     * Seeds both users and birthdays into ServletContext
     * before any request comes in
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        List<BirthdayEntity> birthdays = new ArrayList<BirthdayEntity>();
        birthdays.add(new BirthdayEntity("Tom", "03/05"));
        birthdays.add(new BirthdayEntity("Steven", "05/20"));
        getServletContext().setAttribute("birthdays", birthdays);

        List<UserEntity> users = new ArrayList<UserEntity>();
        users.add(new UserEntity("john@gmail.com", "John", "test123"));
        users.add(new UserEntity("jane@gmail.com", "Jane", "test456"));
        getServletContext().setAttribute("users", users);
    }

    // GET: show the login page
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    // POST: validate credentials, create session or return error
    @SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        List<UserEntity> users = (List<UserEntity>) getServletContext().getAttribute("users");

        UserEntity matched = null;
        for (UserEntity u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                matched = u;
                break;
            }
        }

        if (matched != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("name", matched.getName());
            response.sendRedirect("Birthday");
        } else {
            request.setAttribute("systemMessage",
                    "<span style='color:red;'>Email and Password does not match</span>");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}