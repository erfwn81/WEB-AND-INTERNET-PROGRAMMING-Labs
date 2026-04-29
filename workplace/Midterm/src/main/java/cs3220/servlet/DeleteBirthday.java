package cs3220.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs3220.model.BirthdayEntity;

/**
 * Servlet implementation class DeleteBirthday
 */
@WebServlet("/DeleteBirthday")
public class DeleteBirthday extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteBirthday() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Guard: must be logged in
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("name") == null) {
			response.sendRedirect("index.jsp");
			return;
		}

		int id = Integer.parseInt(request.getParameter("id"));
		@SuppressWarnings("unchecked")
		List<BirthdayEntity> birthdays = (List<BirthdayEntity>) getServletContext().getAttribute("birthdays");

		// Remove entry matching the id passed from the Birthday list page
		birthdays.removeIf(b -> b.getId() == id);

		response.sendRedirect("Birthday");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}