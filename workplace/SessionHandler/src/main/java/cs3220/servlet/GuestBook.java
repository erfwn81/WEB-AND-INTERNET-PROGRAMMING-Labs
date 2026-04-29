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

@WebServlet(urlPatterns = "/GuestBook")
public class GuestBook extends HttpServlet {

    private static final long serialVersionUID = 1L;

	@Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        if (getServletContext().getAttribute("entries") == null) {

            List<GuestBookEntry> entries = new ArrayList<>();
            entries.add(new GuestBookEntry("John", "Hello!"));
            entries.add(new GuestBookEntry("Jane", "Hello Again!"));

            getServletContext().setAttribute("entries", entries);
        }
    }

    @SuppressWarnings("unchecked")
    private List<GuestBookEntry> getEntries() {
        return (List<GuestBookEntry>)
                getServletContext().getAttribute("entries");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        List<GuestBookEntry> entries = getEntries();

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Guest Book</title>");
        out.println("<link rel='stylesheet' href='" 
                + request.getContextPath() 
                + "/assets/css/Styles.css'>");
        out.println("<style>");
        out.println("body { margin:0; padding:40px; background: linear-gradient(120deg, #2980b9, #8e44ad); font-family: Arial, Helvetica, sans-serif; min-height:100vh; box-sizing:border-box; }");
        out.println("h2 { font-size:60px; margin-bottom:20px; color:white; }");
        out.println(".welcome { font-size:26px; margin-bottom:15px; color:white; }");
        out.println("table { margin-top:28px; margin-left:auto; margin-right:auto; border-collapse:collapse; font-size:28px; border:2px solid black; background-color:rgba(255,255,255,0.15); }");
        out.println("th, td { padding:8px 20px; border:2px solid black; color:white; }");
        out.println("th { font-weight:bold; background-color:rgba(0,0,0,0.25); }");
        out.println("tr:hover td { background:lightblue; color:black; }");
        out.println("th:hover { background-color:rgba(0,0,0,0.25) !important; color:white !important; }");
        out.println("a { font-size:26px; text-decoration:none; color:yellowgreen; }");
        out.println("a:visited { color:orange; }");
        out.println("a:hover { color:hotpink; text-decoration:underline; }");
        out.println("a:active { color:blue; }");
        out.println(".add-btn { display:inline-block; margin-top:30px; padding:10px 20px; font-size:24px; background-color:#1a73e8; color:white; border-radius:8px; }");
        out.println(".add-btn:hover { background-color:#1558b0; color:white; }");
        out.println(".logout { font-size:30px; margin-top:20px; display:inline-block; color:hotpink; }");
        out.println(".logout:hover { color:white; text-decoration:underline; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        out.println("<div class='page'>");

        out.println("<h2>Guest Book</h2>");

        out.println("<div class='welcome'>Welcome, "
                + session.getAttribute("user") + "</div>");

        out.println("<table class='guest-table'>");
        out.println("<tr>");
        out.println("<th>Name</th>");
        out.println("<th>Message</th>");
        out.println("<th>Edit | Delete</th>");
        out.println("</tr>");

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

        out.println("</table>");

        out.println("<br>");
        out.println("<a class='add-btn' href='add'>Add Comment</a>");

        out.println("<br><br>");
        out.println("<a class='logout' href='Logout'>Logout</a>");

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}