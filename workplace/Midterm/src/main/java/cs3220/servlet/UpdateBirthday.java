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
 * Servlet implementation class UpdateBirthday
 */
@WebServlet("/UpdateBirthday")
public class UpdateBirthday extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateBirthday() {
		super();
	}

	// GET: load existing data and pre-fill the edit form
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

		// Find the birthday entry to edit
		BirthdayEntity target = null;
		for (BirthdayEntity b : birthdays) {
			if (b.getId() == id) { target = b; break; }
		}
		if (target == null) { response.sendRedirect("Birthday"); return; }

		// Parse MM/DD to pre-select the correct dropdowns
		String[] parts = target.getBirthday().split("/");
		int currentMonth = Integer.parseInt(parts[0]);
		int currentDay   = Integer.parseInt(parts[1]);

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html><html lang=\"en\"><head>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<title>Update Birthday</title>");
		out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" crossorigin=\"anonymous\">");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"./assets/css/styles.css\">");
		out.println("</head><body>");

		out.println("<div class=\"center header\">Update Birthday</div><br />");
		out.println("<form action=\"UpdateBirthday\" method=\"post\">");
		// Pass the id as hidden field so POST knows which record to update
		out.println("<input type=\"hidden\" name=\"id\" value=\"" + id + "\" />");
		out.println("<div class=\"divTable\"><div class=\"divTableBody\">");

		// Name field pre-filled with current value
		out.println("<div class=\"divTableRow\">");
		out.println("<div class=\"divTableCell\"><label for=\"name\">Name:</label></div>");
		out.println("<div class=\"divTableCell\"><input type=\"text\" name=\"name\" id=\"name\" value=\""
				+ target.getName() + "\" required /></div>");
		out.println("</div>");

		// Birthday dropdowns pre-selected to current values
		out.println("<div class=\"divTableRow\">");
		out.println("<div class=\"divTableCell\"><label>Birthday:</label></div>");
		out.println("<div class=\"divTableCell\">");
		out.println("<select name=\"day\">");
		for (int d = 1; d <= 31; d++) {
			out.println("<option value=\"" + d + "\"" + (d == currentDay ? " selected" : "") + ">" + d + "</option>");
		}
		out.println("</select>");
		out.println("<select name=\"month\">");
		String[] months = {"January","February","March","April","May","June",
				"July","August","September","October","November","December"};
		for (int m = 1; m <= 12; m++) {
			out.println("<option value=\"" + m + "\"" + (m == currentMonth ? " selected" : "") + ">" + months[m-1] + "</option>");
		}
		out.println("</select></div></div>");

		out.println("</div></div><br />");
		out.println("<div class=\"center\">");
		out.println("<button type=\"submit\" class=\"btn btn-primary\">Update Birthday</button> ");
		out.println("<a href=\"Birthday\" class=\"btn btn-primary\">Back to Birthday</a>");
		out.println("</div></form></body></html>");
	}

	// POST: apply the edit and re-sort the list
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id      = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String month = String.format("%02d", Integer.parseInt(request.getParameter("month")));
		String day   = String.format("%02d", Integer.parseInt(request.getParameter("day")));
		String birthday = month + "/" + day;

		@SuppressWarnings("unchecked")
		List<BirthdayEntity> birthdays = (List<BirthdayEntity>) getServletContext().getAttribute("birthdays");

		// Update the matching entry in-place
		for (BirthdayEntity b : birthdays) {
			if (b.getId() == id) {
				b.setName(name);
				b.setBirthday(birthday);
				break;
			}
		}

		// Re-sort after every update
		Collections.sort(birthdays, new Comparator<BirthdayEntity>() {
			public int compare(BirthdayEntity a, BirthdayEntity b) {
				if (a.getBirthday() == null || b.getBirthday() == null) return 0;
				return a.getBirthday().compareTo(b.getBirthday());
			}
		});

		response.sendRedirect("Birthday");
	}
}