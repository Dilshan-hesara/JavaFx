
/*
 * Copyright (c) 2024 Dilshan Hesara
 * Author: Dilshan Hesara
 * GitHub: https://github.com/Dilshan-hesara
 *
 * All Rights Reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to use
 * the Software for **personal or educational purposes only**, subject to the following conditions:
 *
 * - The Software may **not be sold** for commercial purposes.
 * - The Software may **not be modified** or altered in any way.
 * - Redistribution of this Software is permitted, provided that the original
 *   copyright notice and this permission notice appear in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES, OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT, OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package edu.fxdemo.supermarketfx.model;

import edu.fxdemo.supermarketfx.db.DBConnection;
import edu.fxdemo.supermarketfx.dto.CustomerDto;
import edu.fxdemo.supermarketfx.dto.TM.CustomerTM;
import edu.fxdemo.supermarketfx.util.CrudUtil;
import javafx.fxml.FXML;


import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//import static edu.fxdemo.supermarketfx.controller.cust_con.CUSTOMER_MODEL;

public class CustomerModel {


    private static final CustomerModel CUSTOMER_MODEL = new CustomerModel();

    public String getNextCustomerId() throws SQLException {

      /*  Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select customer_id from customer order by customer_id desc limit 1";
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rst = pst.executeQuery(); */

        ResultSet rst = CrudUtil.execute("select customer_id from customer order by customer_id desc limit 1");
        if (rst.next()){
            String lastId = rst.getString(1); // C002
            String substring = lastId.substring(1); // 002
            int i = Integer.parseInt(substring); // 2
            int newIdIndex = i+1; // 3
//            String newId = ; // C003
            return String.format("C%03d",newIdIndex);
        }
        return  "C001";
    }
    public boolean saveCustomer(CustomerDto customerDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
    /*    String sql = "insert into customer values (?,?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setObject(1,customerDTO.getCustomerId());
        pst.setObject(2,customerDTO.getName());
        pst.setObject(3,customerDTO.getNic());
        pst.setObject(4,customerDTO.getEmail());
        pst.setObject(5,customerDTO.getPhone());
        int result = pst.executeUpdate();
        boolean isSaved = result>0;*/
        boolean isSaved =  CrudUtil.execute(
                "insert into customer values (?,?,?,?,?)",
                customerDTO.getCustomerId(),
                customerDTO.getName(),
                customerDTO.getNic(),
                customerDTO.getEmail(),
                customerDTO.getPhone()
        );
        return isSaved;
    }


    public ArrayList<CustomerDto> getAllCustomers() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer");

        ArrayList<CustomerDto> customerDTOS = new ArrayList<>();

        while (rst.next()) {
            CustomerDto customerDTO = new CustomerDto(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4),  // Email
                    rst.getString(5)   // Phone
            );
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }



}
