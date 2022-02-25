package manager;

import db.DBConnectionProvider;
import model.Category;
import model.Item;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    Connection connection = DBConnectionProvider.getInstance().getConnection();
    UserManager userManager = new UserManager();
    CategoryManager categoryManager = new CategoryManager();
    Item item = new Item();


    public boolean addItem(Item item) {
        String sql = "INSERT INTO my_item_am.item(title,price,category_id,pic_url,user_id) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, item.getTitle());
            statement.setDouble(2, item.getPrice());
            statement.setInt(3, item.getCategory().getId());
            statement.setString(4, item.getPicUrl());
            statement.setInt(5, item.getUser().getId());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                item.setId(resultSet.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM my_item_am.item";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                items.add(getItemFromResultSet(resultSet));
            }
            return items;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Item> getItemsByCategoryID(Category category){
        String sql = "SELECT * FROM my_item_am.item WHERE category_id=?";
        List<Item> items = new ArrayList<>();
        try {
           PreparedStatement statement = connection.prepareStatement(sql);
           statement.setInt(1,category.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                items.add(getItemFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    public List<Item> getItemsByUserID(User user){
        String sql = "SELECT * FROM my_item_am.item WHERE user_id=?";
        List<Item> items = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,user.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                items.add(getItemFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

  private Item getItemFromResultSet(ResultSet resultSet) {
        try {
         return Item.builder()
                 .id(resultSet.getInt(1))
                 .title(resultSet.getString(2))
                 .price(resultSet.getDouble(3))
                 .category(categoryManager.getCategoryByID(resultSet.getInt(4)))
                 .picUrl(resultSet.getString(5))
                 .user(userManager.getUserById(resultSet.getInt(6)))
                 .build();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteItemById(int id) {
        String sql = "DELETE FROM my_item_am WHERE id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
