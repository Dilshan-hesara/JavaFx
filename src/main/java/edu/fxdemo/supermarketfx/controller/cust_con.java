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

package edu.fxdemo.supermarketfx.controller;

import edu.fxdemo.supermarketfx.dto.CustomerDto;
import edu.fxdemo.supermarketfx.dto.TM.CustomerTM;
import edu.fxdemo.supermarketfx.model.CustomerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class cust_con implements Initializable {

    private static final CustomerModel CUSTOMER_MODEL = new CustomerModel();

    @FXML
    private TableColumn<CustomerTM, Integer> col_id;

    @FXML
    private TableColumn<CustomerTM, String> col_mail;

    @FXML
    private TableColumn<CustomerTM, String> col_name;

    @FXML
    private TableColumn<CustomerTM, String> col_nic;

    @FXML
    private TableColumn<CustomerTM, String> col_phone;

    @FXML
    private TableColumn<CustomerTM, String> col_add;

    @FXML
    private TableView<CustomerTM> ctable;

    @FXML
    private Label txtid;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtnic;

    @FXML
    private TextField txtphone;

    @FXML
    private TextField txtmail;

    private ObservableList<CustomerTM> customerList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_id.setCellValueFactory(new PropertyValueFactory<>("custId"));
        col_add.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("custName"));
        col_nic.setCellValueFactory(new PropertyValueFactory<>("custNic"));
        col_mail.setCellValueFactory(new PropertyValueFactory<>("custEmail"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("custPhone"));

        try {
            loadNextCustomerId();
            loadCustomerTable();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load customer data").show();
        }
    }

    public void loadCustomerTable() {
        try {
            ArrayList<CustomerTM> customerTMs = CUSTOMER_MODEL.getAllCustomer();
            customerList.clear();
            customerList.addAll(customerTMs);
            ctable.setItems(customerList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading customer data: " + e.getMessage()).show();
        }
    }

    CustomerModel customerModel = new CustomerModel();

    public void loadNextCustomerId() throws SQLException {
        String nextCustomerId = customerModel.getNextCustomerId();
        txtid.setText(nextCustomerId);
    }

    @FXML
    public void savebutt(ActionEvent actionEvent) throws SQLException {

        String customerId = txtid.getText();
        String name = txtname.getText();
        String nic = txtnic.getText();
        String email = txtmail.getText();
        String phone = txtphone.getText();
        CustomerDto customerDTO = new CustomerDto(
                customerId,
                name,
                nic,
                email,
                phone
        );
        boolean isSaved = customerModel.saveCustomer(customerDTO);
        if (isSaved) {
            loadNextCustomerId();
            txtname.setText("");
            txtnic.setText("");
            txtmail.setText("");
            txtphone.setText("");
            loadCustomerTable();
            new Alert(Alert.AlertType.INFORMATION, "Customer saved...!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to save customer...!").show();
        }
    }
}
