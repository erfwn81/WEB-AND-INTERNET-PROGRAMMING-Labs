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

    private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // 🔒 Session protection
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

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

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Edit Comment</title>");
        out.println("<link rel='stylesheet' href='" 
                + request.getContextPath() 
                + "/assets/css/Styles.css'>");
        out.println("<style>");
        out.println("body { margin:0; padding:40px; background: linear-gradient(120deg, #2980b9, #8e44ad); font-family: Arial, Helvetica, sans-serif; min-height:100vh; box-sizing:border-box; }");
        out.println("h2 { font-size:60px; margin-bottom:20px; color:white; }");
        out.println(".form-container { margin-top:30px; max-width:600px; padding:30px; background-color:rgba(255,255,255,0.15); border-radius:8px; }");
        out.println(".form-label { font-size:24px; color:white; display:block; margin-bottom:10px; }");
        out.println(".form-input { font-size:20px; padding:10px; width:100%; max-width:500px; margin-bottom:20px; border:2px solid black; border-radius:4px; box-sizing:border-box; }");
        out.println(".form-textarea { font-size:20px; padding:10px; width:100%; max-width:500px; height:150px; margin-bottom:20px; border:2px solid black; border-radius:4px; box-sizing:border-box; font-family:Arial, sans-serif; }");
        out.println(".form-button { font-size:22px; padding:12px 30px; background-color:#1a73e8; color:white; border:none; border-radius:8px; cursor:pointer; }");
        out.println(".form-button:hover { background-color:#1558b0; }");
        out.println(".back-link { font-size:24px; text-decoration:none; color:yellowgreen; margin-top:30px; display:inline-block; }");
        out.println(".back-link:hover { color:hotpink; text-decoration:underline; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        out.println("<div class='page'>");

        out.println("<h2>Edit Comment</h2>");

        out.println("<form method='post' class='form-container'>");

        out.println("<input type='hidden' name='id' value='" + selected.getId() + "'>");

        out.println("<label class='form-label'>Name:</label>");
        out.println("<input class='form-input' type='text' name='name' value='" 
                + selected.getName() + "' required>");

        out.println("<label class='form-label'>Message:</label>");
        out.println("<textarea class='form-textarea' name='message' required>"
                + selected.getMessage()
                + "</textarea>");

        out.println("<input class='form-button' type='submit' value='Update Comment'>");

        out.println("</form>");

        out.println("<br>");
        out.println("<a class='back-link' href='GuestBook'>Back to Guest Book</a>");

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    @SuppressWarnings("unchecked")
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