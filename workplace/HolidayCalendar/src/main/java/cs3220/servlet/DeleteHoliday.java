package cs3220.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs3220.model.HolidayEntry;

@WebServlet("/DeleteHoliday")
public class DeleteHoliday extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    private List<HolidayEntry> getEntries() {
        return (List<HolidayEntry>) getServletContext().getAttribute("entries");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        List<HolidayEntry> entries = getEntries();

        Iterator<HolidayEntry> it = entries.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                break;
            }
        }

        response.sendRedirect("HolidayCalendar");
    }
}