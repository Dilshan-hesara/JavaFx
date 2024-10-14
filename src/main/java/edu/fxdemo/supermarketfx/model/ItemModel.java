package edu.fxdemo.supermarketfx.model;

import edu.fxdemo.supermarketfx.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {
    public ArrayList<String> getAllItemIds() throws SQLException {
        // Execute SQL query to get all item IDs
        ResultSet rst = CrudUtil.execute("select item_id from item");

        // Create an ArrayList to store the item IDs
        ArrayList<String> itemIds = new ArrayList<>();

        // Iterate through the result set and add each item ID to the list
        while (rst.next()) {
            itemIds.add(rst.getString(1));
        }

        // Return the list of item IDs
        return itemIds;
    }
}
