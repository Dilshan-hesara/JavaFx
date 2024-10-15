package edu.fxdemo.supermarketfx.model;

import edu.fxdemo.supermarketfx.db.DBConnection;
import edu.fxdemo.supermarketfx.dto.OrderDetailsDto;
import edu.fxdemo.supermarketfx.dto.OrdersDto;
import edu.fxdemo.supermarketfx.util.CrudUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsModel {



    //private final OrderDetailsModel orderDetailsModel = new OrderDetailsModel();


    private final ItemModel itemModel = new ItemModel();

    public boolean saveOrderDetailsList(ArrayList<OrderDetailsDto> orderDetailsDTOS) throws SQLException {
        // Iterate through each order detail in the list
        for (OrderDetailsDto orderDetailsDTO : orderDetailsDTOS) {
            // @isOrderDetailsSaved: Saves the individual order detail
            boolean isOrderDetailsSaved = saveOrderDetail(orderDetailsDTO);
            if (!isOrderDetailsSaved) {
                // Return false if saving any order detail fails
                return false;
            }

            // @isItemUpdated: Updates the item quantity in the stock for the corresponding order detail
            boolean isItemUpdated = itemModel.reduceQuantity(orderDetailsDTO);
            if (!isItemUpdated) {
                // Return false if updating the item quantity fails
                return false;
            }
        }
        // Return true if all order details are saved and item quantities updated successfully
        return true;
    }
    private boolean saveOrderDetail(OrderDetailsDto orderDetailsDTO) throws SQLException {
        // Executes an insert query to save the order detail into the database
        return CrudUtil.execute(
                "insert into orderdetails values (?,?,?,?)",
                orderDetailsDTO.getOrderId(),
                orderDetailsDTO.getItemId(),
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getPrice()
        );
    }

}
