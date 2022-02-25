package servlet;

import manager.ItemManager;
import model.Category;
import model.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@WebServlet(urlPatterns = "/category")
public class SelectCategoryServlet extends HttpServlet {

    ItemManager itemManager = new ItemManager();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String catId = req.getParameter("catId");

        int id = Integer.parseInt(catId);
        List<Item> itemsByCategoryID = itemManager.getItemsByCategoryID(id);
        req.setAttribute("items",itemsByCategoryID);

        req.getRequestDispatcher("firstPage.jsp").forward(req,resp);
    }
}
