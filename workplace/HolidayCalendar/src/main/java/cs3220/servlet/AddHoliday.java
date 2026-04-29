package cs3220.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs3220.model.HolidayEntry;

@WebServlet("/AddHoliday")
public class AddHoliday extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    private List<HolidayEntry> getEntries() {
        return (List<HolidayEntry>) getServletContext().getAttribute("entries");
    }

    private void sortByDate(List<HolidayEntry> entries) {
        entries.sort(Comparator.comparing(e -> LocalDate.parse(e.getHolidayDate())));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!doctype html>");
        out.println("<html><head>");
        out.println("<meta charset='utf-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Add Holiday</title>");
        out.println("<link rel='stylesheet' href='assets/styles/styles.css'>");
        out.println("</head><body>");
        out.println("<div class='page'>");

        out.println("<h1>Add Holiday</h1>");

        out.println("<form class='form' method='post' action='AddHoliday'>");

        // Dropdown date input (year/month/day)
        out.println("<div class='form-row'>");
        out.println("<label>Date:</label>");
        out.println("<select name='day'>");
        for (int d = 1; d <= 31; d++) out.println("<option value='" + d + "'>" + d + "</option>");
        out.println("</select>");
        String[] months = {
        	    "January","February","March","April","May","June",
        	    "July","August","September","October","November","December"
        	};

        	out.println("<select name='month'>");
        	for (int m = 1; m <= 12; m++) {
        	    out.println("<option value='" + m + "'>" + months[m - 1] + "</option>");
        	}
        	out.println("</select>");
        	
        	
        out.println("<select name='year'>");
        for (int y = 2000; y <= 2026; y++) out.println("<option value='" + y + "'>" + y + "</option>");
        out.println("</select>");

       

       
        out.println("</div>");

        out.println("<div class='form-row'>");
        out.println("<label>Holiday:</label>");
        out.println("<input type='text' name='holiday' required>");
        out.println("</div>");

        out.println("<div class='actions'>");
        out.println("<button class='btn' type='submit'>Add Holiday</button>");
        out.println("<a class='btn secondary' href='HolidayCalendar'>Cancel</a>");
        out.println("</div>");

        out.println("</form>");

        out.println("</div></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int year = Integer.parseInt(request.getParameter("year"));
        int month = Integer.parseInt(request.getParameter("month"));
        int day = Integer.parseInt(request.getParameter("day"));
        String holiday = request.getParameter("holiday");

        String date = String.format("%04d-%02d-%02d", year, month, day);

        List<HolidayEntry> entries = getEntries();

        // Ignore duplicates (same date)
        boolean exists = false;
        for (HolidayEntry e : entries) {
            if (e.getHolidayDate().equals(date)) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            entries.add(new HolidayEntry(date, holiday));
            sortByDate(entries);
        }

        response.sendRedirect("HolidayCalendar");
    }
}