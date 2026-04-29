package cs3220.servlet;

import cs3220.model.GuestBookEntry;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/add")
public class AddComment extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("""
            <html>
            <body>
                <h2>Add Comment</h2>
                <form method='post'>
                    Name: <input type='text' name='name'><br><br>
                    Message:<br>
                    <textarea name='message'></textarea><br><br>
                    <input type='submit' value='Add Comment'>
                </form>
            </body>
            </html>
        """);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String message = request.getParameter("message");

        List<GuestBookEntry> entries =
                (List<GuestBookEntry>) getServletContext()
                        .getAttribute("entries");

        entries.add(new GuestBookEntry(name, message));

        response.sendRedirect("GuestBook");
    }
}