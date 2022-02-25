package servlet;

import manager.ItemManager;
import model.Item;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/userHome")
public class UserHomeServlet extends HttpServlet {

    ItemManager itemManager = new ItemManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Item item = (Item) session.getAttribute("item");

        List<Item> itemsOfUserId = itemManager.getItemsByUserID(user);

        req.setAttribute("name", user.getName());
        req.setAttribute("surname", user.getSurname());
        req.setAttribute("items", itemsOfUserId);
//       req.setAttribute("item",item.getPicUrl());


        req.getRequestDispatcher("/userHome1.jsp").forward(req, resp);
    }
}

