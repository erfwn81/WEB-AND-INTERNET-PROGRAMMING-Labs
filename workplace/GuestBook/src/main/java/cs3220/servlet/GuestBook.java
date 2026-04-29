package cs3220.servlet;

import cs3220.model.GuestBookEntry;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns="/", loadOnStartup=1)
public class GuestBook extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        List<GuestBookEntry> entries = new ArrayList<>();
        entries.add(new GuestBookEntry("John", "Hello!"));
        entries.add(new GuestBookEntry("Jane", "Hello Again!"));

        getServletContext().setAttribute("entries", entries);
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<GuestBookEntry> entries =
                (List<GuestBookEntry>) getServletContext()
                        .getAttribute("entries");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("""
            <html>
            <head>
                <meta charset="utf-8" />
                <title>Guest Book</title>
            </head>
            <body>
                <h2>Guest Book</h2>
                <table border="1">
                    <tr>
                        <th>Name</th>
                        <th>Message</th>
                        <th>Edit | Delete</th>
                    </tr>
        """);

        for (GuestBookEntry entry : entries) {
            out.println("<tr>");
            out.println("<td>" + entry.getName() + "</td>");
            out.println("<td>" + entry.getMessage() + "</td>");
            out.println("<td>");
            out.println("<a href='edit?id=" + entry.getId() + "'>Edit</a> | ");
            out.println("<a href='delete?id=" + entry.getId() + "'>Delete</a>");
            out.println("</td>");
            out.println("</tr>");
        }

        out.println("""
                </table>
                <br>
                <a href='add'>Add Comment</a>
            </body>
            </html>
        """);
    }
}