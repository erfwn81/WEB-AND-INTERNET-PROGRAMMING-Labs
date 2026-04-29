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

@WebServlet("/UpdateHoliday")
public class UpdateHoliday extends HttpServlet {
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

        int id = Integer.parseInt(request.getParameter("id"));
        List<HolidayEntry> entries = getEntries();

        HolidayEntry selected = null;
        for (HolidayEntry e : entries) {
            if (e.getId() == id) {
                selected = e;
                break;
            }
        }

        if (selected == null) {
            response.sendRedirect("HolidayCalendar");
            return;
        }

        LocalDate d = LocalDate.parse(selected.getHolidayDate());

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!doctype html>");
        out.println("<html><head>");
        out.println("<meta charset='utf-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Update Holiday</title>");
        out.println("<link rel='stylesheet' href='assets/styles/styles.css'>");
        out.println("</head><body>");
        out.println("<div class='page'>");

        out.println("<h1>Update Holiday</h1>");

        out.println("<form class='form' method='post' action='UpdateHoliday'>");
        out.println("<input type='hidden' name='id' value='" + selected.getId() + "'>");

        out.println("<div class='form-row'>");
        out.println("<label>Date:</label>");

        // Day dropdown — was already correct
        out.println("<select name='day'>");
        for (int day = 1; day <= 31; day++) {
            String sel = (day == d.getDayOfMonth()) ? " selected" : "";
            out.println("<option value='" + day + "'" + sel + ">" + day + "</option>");
        }
        out.println("</select>");

        // FIX: Month dropdown — added selected check
        String[] months = {
            "January","February","March","April","May","June",
            "July","August","September","October","November","December"
        };
        out.println("<select name='month'>");
        for (int m = 1; m <= 12; m++) {
            String sel = (m == d.getMonthValue()) ? " selected" : "";
            out.println("<option value='" + m + "'" + sel + ">" + months[m - 1] + "</option>");
        }
        out.println("</select>");

        // FIX: Year dropdown — added selected check so it doesn't default to 2000
        out.println("<select name='year'>");
        for (int y = 2000; y <= 2030; y++) {
            String sel = (y == d.getYear()) ? " selected" : "";
            out.println("<option value='" + y + "'" + sel + ">" + y + "</option>");
        }
        out.println("</select>");

        out.println("</div>");

        out.println("<div class='form-row'>");
        out.println("<label>Holiday:</label>");
        out.println("<input type='text' name='holiday' value='" + escapeHtml(selected.getHoliday()) + "' required>");
        out.println("</div>");

        out.println("<div class='actions'>");
        out.println("<button class='btn' type='submit'>Update Holiday</button>");
        out.println("<a class='btn secondary' href='HolidayCalendar'>Cancel</a>");
        out.println("</div>");

        out.println("</form>");

        out.println("</div></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        int year = Integer.parseInt(request.getParameter("year"));
        int month = Integer.parseInt(request.getParameter("month"));
        int day = Integer.parseInt(request.getParameter("day"));
        String holiday = request.getParameter("holiday");

        String date = String.format("%04d-%02d-%02d", year, month, day);

        List<HolidayEntry> entries = getEntries();

        for (HolidayEntry e : entries) {
            if (e.getId() == id) {
                e.setHolidayDate(date);
                e.setHoliday(holiday);
                break;
            }
        }

        sortByDate(entries);
        response.sendRedirect("HolidayCalendar");
    }

    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}