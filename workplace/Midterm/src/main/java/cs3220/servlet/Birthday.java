package cs3220.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs3220.model.BirthdayEntity;

/**
 * Servlet implementation class Birthday
 */
@WebServlet("/Birthday")
public class Birthday extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Birthday() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		// Guard: if not logged in, redirect to login page
		if (session == null || session.getAttribute("name") == null) {
			response.sendRedirect("index.jsp");
			return;
		}

		String name = (String) session.getAttribute("name");
		@SuppressWarnings("unchecked")
		List<BirthdayEntity> birthdays = (List<BirthdayEntity>) getServletContext().getAttribute("birthdays");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html><html lang=\"en\"><head>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<title>Birthday Calendar</title>");
		out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" crossorigin=\"anonymous\">");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"./assets/css/styles.css\">");
		out.println("</head><body>");

		out.println("<div class=\"center header\">Welcome " + name + "</div><br />");

		out.println("<div class=\"divTable\">");
		out.println("<div class=\"divTableHead\">");
		out.println("<div class=\"divTableCell\">Name</div>");
		out.println("<div class=\"divTableCell\">Birthday Date</div>");
		out.println("<div class=\"divTableCell\">Edit | Delete</div>");
		out.println("</div>");
		out.println("<div class=\"divTableBody\">");
		for (BirthdayEntity birthday : birthdays) {
			out.println("<div class=\"divTableRow\">");
			out.println("<div class=\"divTableCell\">" + birthday.getName() + "</div>");
			out.println("<div class=\"divTableCell center\">" + birthday.getBirthday() + "</div>");
			out.println("<div class=\"divTableCell center\">"
					+ "<a href=\"UpdateBirthday?id=" + birthday.getId() + "\">Edit</a>"
					+ " | "
					+ "<a href=\"DeleteBirthday?id=" + birthday.getId() + "\">Delete</a>"
					+ "</div>");
			out.println("</div>");
		}
		out.println("</div></div><br />");

		out.println("<div class=\"center\">");
		out.println("<a href=\"AddBirthday\" class=\"btn btn-primary\">Add New Birthday</a> ");
		out.println("<a href=\"Logout\" class=\"btn btn-primary\">Logout</a>");
		out.println("</div>");
		out.println("</body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}