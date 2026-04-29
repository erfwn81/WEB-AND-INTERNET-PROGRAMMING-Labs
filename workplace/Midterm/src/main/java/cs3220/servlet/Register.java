package cs3220.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs3220.model.UserEntity;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Register() {
		super();
	}

	// GET: show the registration page
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("register.jsp").forward(request, response);
	}

	// POST: check for duplicate email, register new user, start session
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String name  = request.getParameter("name");
		String pass  = request.getParameter("password");

		List<UserEntity> users = (List<UserEntity>) getServletContext().getAttribute("users");

		// Check if email already exists (emails must be unique)
		boolean duplicate = false;
		for (UserEntity u : users) {
			if (u.getEmail().equalsIgnoreCase(email)) {
				duplicate = true;
				break;
			}
		}

		if (duplicate) {
			// Re-populate fields and show error
			request.setAttribute("email", email);
			request.setAttribute("name", name);
			request.setAttribute("systemMessage",
					"<span style='color:red;'>User already exist</span>");
			request.getRequestDispatcher("register.jsp").forward(request, response);
		} else {
			// Add new user and log them in immediately
			users.add(new UserEntity(email, name, pass));
			HttpSession session = request.getSession(true);
			session.setAttribute("name", name);
			response.sendRedirect("Birthday");
		}
	}
}