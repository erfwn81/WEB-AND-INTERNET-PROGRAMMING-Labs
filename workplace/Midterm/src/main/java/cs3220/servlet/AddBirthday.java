package cs3220.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs3220.model.BirthdayEntity;

/**
 * Servlet implementation class AddBirthday
 */
@WebServlet("/AddBirthday")
public class AddBirthday extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddBirthday() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Guard: must be logged in
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("name") == null) {
			response.sendRedirect("index.jsp");
			return;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html><html lang=\"en\"><head>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<title>Add Birthday</title>");
		out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" crossorigin=\"anonymous\">");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"./assets/css/styles.css\">");
		out.println("</head><body>");

		out.println("<div class=\"center header\">Add Birthday</div><br />");
		out.println("<form id=\"addBirthdayForm\" action=\"AddBirthday\" method=\"post\">");
		out.println("<div class=\"divTable\"><div class=\"divTableBody\">");

		// Name row
		out.println("<div class=\"divTableRow\">");
		out.println("<div class=\"divTableCell\"><label for=\"name\">Name:</label></div>");
		out.println("<div class=\"divTableCell\"><input type=\"text\" placeholder=\"Name\" name=\"name\" id=\"name\" required /></div>");
		out.println("</div>");

		// Birthday row — day and month dropdowns
		out.println("<div class=\"divTableRow\">");
		out.println("<div class=\"divTableCell\"><label>Birthday:</label></div>");
		out.println("<div class=\"divTableCell\">");
		out.println("<select id=\"day\" name=\"day\">");
		for (int day = 1; day <= 31; day++) {
			out.println("<option value=\"" + day + "\">" + day + "</option>");
		}
		out.println("</select>");
		out.println("<select id=\"month\" name=\"month\">");
		out.println("<option value=\"1\">January</option>"
				+ "<option value=\"2\">February</option>"
				+ "<option value=\"3\">March</option>"
				+ "<option value=\"4\">April</option>"
				+ "<option value=\"5\">May</option>"
				+ "<option value=\"6\">June</option>"
				+ "<option value=\"7\">July</option>"
				+ "<option value=\"8\">August</option>"
				+ "<option value=\"9\">September</option>"
				+ "<option value=\"10\">October</option>"
				+ "<option value=\"11\">November</option>"
				+ "<option value=\"12\">December</option>");
		out.println("</select></div></div>");

		out.println("</div></div><br />");
		out.println("<div class=\"center\">");
		out.println("<button type=\"submit\" class=\"btn btn-primary\">Add New Birthday</button> ");
		out.println("<a href=\"Birthday\" class=\"btn btn-primary\">Back to Birthday</a>");
		out.println("</div></form></body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		// Zero-pad month and day to get MM/DD format
		String month = String.format("%02d", Integer.parseInt(request.getParameter("month")));
		String day   = String.format("%02d", Integer.parseInt(request.getParameter("day")));
		String birthday = month + "/" + day;

		@SuppressWarnings("unchecked")
		List<BirthdayEntity> birthdays = (List<BirthdayEntity>) getServletContext().getAttribute("birthdays");
		birthdays.add(new BirthdayEntity(name, birthday));

		// Keep list sorted by date after every add
		Collections.sort(birthdays, new Comparator<BirthdayEntity>() {
			public int compare(BirthdayEntity a, BirthdayEntity b) {
				if (a.getBirthday() == null || b.getBirthday() == null) return 0;
				return a.getBirthday().compareTo(b.getBirthday());
			}
		});

		response.sendRedirect("Birthday");
	}
}