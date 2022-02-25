package servlet;

import manager.UserManager;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private final UserManager userManager = new UserManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        StringBuilder message = new StringBuilder();
        if (email == null || email.length() == 0) {
            message.append("email field is required");
        }
        if (password == null || password.length() == 0) {
            message.append("password field is required");
        }

        User user = userManager.getUserByEmailAndPassword(email, password);
        if (user == null) {
            req.getRequestDispatcher("/firstPage.jsp").forward(req, resp);
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("/userHome");
        }

    }
}


