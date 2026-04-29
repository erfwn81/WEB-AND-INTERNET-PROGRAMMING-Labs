package cs3220.servlet;

import cs3220.model.GuestBookEntry;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/delete")
public class DeleteEntry extends HttpServlet {

    private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        List<GuestBookEntry> entries =
                (List<GuestBookEntry>) getServletContext()
                        .getAttribute("entries");

        Iterator<GuestBookEntry> iterator = entries.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().getId() == id) {
                iterator.remove();
                break;
            }
        }

        response.sendRedirect("GuestBook");
    }
}