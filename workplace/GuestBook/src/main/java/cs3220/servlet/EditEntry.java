package cs3220.servlet;

import cs3220.model.GuestBookEntry;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/edit")
public class EditEntry extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        List<GuestBookEntry> entries =
                (List<GuestBookEntry>) getServletContext()
                        .getAttribute("entries");

        GuestBookEntry selected = null;

        for (GuestBookEntry entry : entries) {
            if (entry.getId() == id) {
                selected = entry;
                break;
            }
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h2>Edit Comment</h2>");
        out.println("<form method='post'>");
        out.println("<input type='hidden' name='id' value='" + selected.getId() + "'>");
        out.println("Name: <input type='text' name='name' value='" + selected.getName() + "'><br><br>");
        out.println("Message:<br>");
        out.println("<textarea name='message'>" + selected.getMessage() + "</textarea><br><br>");
        out.println("<input type='submit' value='Update Comment'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String message = request.getParameter("message");

        List<GuestBookEntry> entries =
                (List<GuestBookEntry>) getServletContext()
                        .getAttribute("entries");

        for (GuestBookEntry entry : entries) {
            if (entry.getId() == id) {
                entry.setName(name);
                entry.setMessage(message);
                break;
            }
        }

        response.sendRedirect("GuestBook");
    }
}