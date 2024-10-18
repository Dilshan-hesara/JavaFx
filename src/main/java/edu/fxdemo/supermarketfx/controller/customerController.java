package edu.fxdemo.supermarketfx.controller;

import edu.fxdemo.supermarketfx.dto.CustomerDto;
import edu.fxdemo.supermarketfx.dto.TM.CustomerTM;
import edu.fxdemo.supermarketfx.model.CustomerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class customerController implements Initializable {

    @FXML
    private TableColumn<?, ?> col_id;

    @FXML
    private TableColumn<?, ?> col_mail;

    @FXML
    private TableColumn<?, ?> col_name;

    @FXML
    private TableColumn<?, ?> col_nic;

    @FXML
    private TableColumn<?, ?> col_phone;

    @FXML
    private TableView<CustomerTM> ctable;

    @FXML
    private Button saveButt;

    @FXML
    private Button deleteButt;

    @FXML
    private Button resetButt;

    @FXML
    private Label txtid;

    @FXML
    private Button updateButt;

    @FXML
    private TextField txtmail;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtnic;

    @FXML
    private TextField txtphone;



    @FXML
    void savebutt(ActionEvent event) throws SQLException {


        String customerId = txtid.getText();
        String name = txtname.getText();
        String nic = txtnic.getText();
        String email = txtmail.getText();
        String phone = txtphone.getText();


        txtname.setStyle(txtname.getStyle()+";-fx-border-color: #7367F0;");
        txtnic.setStyle(txtnic.getStyle()+";-fx-border-color: #7367F0;");
        txtmail.setStyle(txtmail.getStyle()+";-fx-border-color: #7367F0;");
        txtphone.setStyle(txtphone.getStyle()+";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);

        if (!isValidName){
            System.out.println(txtname.getStyle());
            txtname.setStyle(txtname.getStyle()+";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }

        if (!isValidNic){
            txtnic.setStyle(txtnic.getStyle()+";-fx-border-color: red;");
//            return;
        }

        if (!isValidEmail){
            txtmail.setStyle(txtmail.getStyle()+";-fx-border-color: red;");
        }

        if (!isValidPhone){
            txtphone.setStyle(txtphone.getStyle()+";-fx-border-color: red;");
        }
        if (isValidName && isValidNic && isValidEmail && isValidPhone){
            CustomerDto customerDTO = new CustomerDto(
                    customerId,
                    name,
                    nic,
                    email,
                    phone
            );

            boolean isSaved = customerModel.saveCustomer(customerDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save customer...!").show();
            }
        }

    }


    @FXML
    void tableClick(MouseEvent event) {

        CustomerTM customerTM = ctable.getSelectionModel().getSelectedItem();
        if (customerTM != null) {
            txtid.setText(customerTM.getCustId());
            txtname.setText(customerTM.getCustName());
            txtnic.setText(customerTM.getCustNic());
            txtmail.setText(customerTM.getCustEmail());
            txtphone.setText(customerTM.getCustPhone());

            saveButt.setDisable(true);

               deleteButt.setDisable(false);
               updateButt.setDisable(false);
        }

    }


    public void initialize(URL url, ResourceBundle resourceBundle) {

        col_id.setCellValueFactory(new PropertyValueFactory<>("custId"));
        //  col_add.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
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


    CustomerModel customerModel = new CustomerModel();

    public void loadNextCustomerId() throws SQLException {
        String nextCustomerId = customerModel.getNextCustomerId();
        txtid.setText(nextCustomerId);
    }


    private void loadCustomerTable() throws SQLException {
        ArrayList<CustomerDto> customerDTOS = customerModel.getAllCustomers();

        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

//        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();
//        customerTMS.addAll(customerModel.getAllCustomer().stream().map(customerDTO->{
//            return new CustomerTM(
//                    customerDTO.getId(),
//                    customerDTO.getName(),
//                    customerDTO.getNic(),
//                    customerDTO.getEmail(),
//                    customerDTO.getPhone()
//            );
//        }).collect(Collectors.toList()));
//        tblCustomer.setItems(customerTMS);

        for (CustomerDto customerDto : customerDTOS) {
            CustomerTM customerTM = new CustomerTM(
                    customerDto.getCustomerId(),
                    customerDto.getName(),
                    customerDto.getNic(),
                    customerDto.getEmail(),
                    customerDto.getPhone()
            );
            customerTMS.add(customerTM);
        }

        ctable.setItems(customerTMS);
    }

    @FXML
    void deleteButt(ActionEvent event) throws SQLException {

        String customerId = txtid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = customerModel.deleteCustomer(customerId);
            if (isDeleted) {
             refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete customer...!").show();
            }
        }
    }




    @FXML
    void updateButtn(ActionEvent event) throws SQLException {

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

        boolean isUpdate = customerModel.updateCustomer(customerDTO);
        if (isUpdate) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Customer update...!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to update customer...!").show();
        }
    }

    @FXML
    void resetButt(ActionEvent event) throws SQLException {

        refreshPage();

    }

    private void refreshPage() throws SQLException {
        loadNextCustomerId();
        loadCustomerTable();

        saveButt.setDisable(false);

        updateButt.setDisable(true);
        deleteButt.setDisable(true);

        txtname.setText("");
        txtnic.setText("");
        txtmail.setText("");
        txtphone.setText("");
    }

}
