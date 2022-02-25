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
    Category category = new Category();
    ItemManager itemManager = new ItemManager();
    List<Item> itemsByCat = itemManager.getItemsByCategoryID(category);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("item",itemsByCat);
        req.getRequestDispatcher("index.jsp");
    }
}
