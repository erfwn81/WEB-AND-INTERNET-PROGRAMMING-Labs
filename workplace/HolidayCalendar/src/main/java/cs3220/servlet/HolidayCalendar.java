package cs3220.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs3220.model.HolidayEntry;

@WebServlet(urlPatterns = "/HolidayCalendar", loadOnStartup = 1)
public class HolidayCalendar extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Formatter for stored ISO format
    private final DateTimeFormatter inputFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Formatter for display (matches lab screenshot)
    private final DateTimeFormatter displayFormatter =
            DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);

    @Override
    public void init() throws ServletException {

        // Only initialize once
        if (getServletContext().getAttribute("entries") == null) {

            List<HolidayEntry> entries = new ArrayList<>();

            entries.add(new HolidayEntry("2026-01-01", "New Year's Day"));
            entries.add(new HolidayEntry("2026-01-19", "Martin Luther King Jr. Day"));
            entries.add(new HolidayEntry("2026-02-16", "Presidents' Day"));
            entries.add(new HolidayEntry("2026-05-25", "Memorial Day"));
            entries.add(new HolidayEntry("2026-07-03", "Independence Day (Observed)"));
            entries.add(new HolidayEntry("2026-09-07", "Labor Day"));
            entries.add(new HolidayEntry("2026-11-11", "Veterans Day"));
            entries.add(new HolidayEntry("2026-11-26", "Thanksgiving Day"));
            entries.add(new HolidayEntry("2026-12-25", "Christmas Day"));

            sortByDate(entries);
            getServletContext().setAttribute("entries", entries);
        }
    }

    @SuppressWarnings("unchecked")
    private List<HolidayEntry> getEntries() {
        return (List<HolidayEntry>) getServletContext().getAttribute("entries");
    }

    private void sortByDate(List<HolidayEntry> entries) {
        entries.sort(Comparator.comparing(
                e -> LocalDate.parse(e.getHolidayDate(), inputFormatter)
        ));
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<HolidayEntry> entries = getEntries();
        sortByDate(entries);

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!doctype html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Holiday Calendar</title>");
        out.println("<link rel='stylesheet' href='" 
                + request.getContextPath() 
                + "/assets/styles/styles.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='page'>");

        out.println("<h1>US Holidays</h1>");

        out.println("<div class='actions'>");
        out.println("<a class='btn' href='AddHoliday'>Add Holiday</a>");
        out.println("</div>");

        out.println("<div class='table-wrap'>");
        out.println("<table class='holiday-table'>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Date</th>");
        out.println("<th>Holiday</th>");
        out.println("<th>Update / Delete</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");

        for (HolidayEntry e : entries) {

            LocalDate date =
                    LocalDate.parse(e.getHolidayDate(), inputFormatter);

            String formattedDate =
                    date.format(displayFormatter);

            out.println("<tr>");
            out.println("<td>" + formattedDate + "</td>");
            out.println("<td>" + escapeHtml(e.getHoliday()) + "</td>");
            out.println("<td>");
            out.println("<a href='UpdateHoliday?id=" + e.getId() + "'>Update</a> | ");
            out.println("<a href='DeleteHoliday?id=" + e.getId() + "'>Delete</a>");
            out.println("</td>");
            out.println("</tr>");
        }

        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}