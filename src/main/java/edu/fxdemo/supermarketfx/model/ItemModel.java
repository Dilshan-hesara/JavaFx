package edu.fxdemo.supermarketfx.model;

import edu.fxdemo.supermarketfx.dto.ItemDto;
import edu.fxdemo.supermarketfx.dto.OrderDetailsDto;
import edu.fxdemo.supermarketfx.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {
    public ArrayList<String> getAllItemIds() throws SQLException {
        // Execute SQL query to get all item IDs
        ResultSet rst = CrudUtil.execute("SELECT item_id FROM item");

        // Create an ArrayList to store the item IDs
        ArrayList<String> itemIds = new ArrayList<>();

        // Iterate through the result set and add each item ID to the list
        while (rst.next()) {
            itemIds.add(rst.getString(1));
        }

        // Return the list of item IDs
        return itemIds;
    }

    public ItemDto findById(String selectedItemId) throws SQLException {
        // Execute SQL query to find the item by ID
        ResultSet rst = CrudUtil.execute("SELECT * FROM item WHERE item_id=?", selectedItemId);

        // If the item is found, create an ItemDto object with the retrieved data
        if (rst.next()) {
            return new ItemDto(
                    rst.getString(1),  // Item ID
                    rst.getString(2),  // Item Name
                    rst.getInt(3),     // Item Quantity
                    rst.getDouble(4)   // Item Price
            );
        }

        return null;  // Return null if the item is not found
    }

    public boolean reduceQuantity(OrderDetailsDto orderDetailsDto) throws SQLException {
        // Execute SQL query to update the item quantity in the database
        return CrudUtil.execute(
                "UPDATE item SET quantity = quantity - ? WHERE item_id = ?",
                orderDetailsDto.getQuantity(),   // Quantity to reduce
                orderDetailsDto.getItemId()      // Item ID
        );
    }




}
