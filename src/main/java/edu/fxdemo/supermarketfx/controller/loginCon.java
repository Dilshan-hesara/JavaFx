package edu.fxdemo.supermarketfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class loginCon {

    @FXML
    private AnchorPane aclog;
    @FXML
    private TextField txtPass;

    @FXML
    private TextField txtUser;

    @FXML
    void logb(ActionEvent event) throws IOException {
        String username = txtUser.getText();
        String password = txtPass.getText();

        if (username.equals("Dilshan") && password.equals("1234")) {
            AnchorPane dashboard = FXMLLoader.load(getClass().getResource("/view/page1.fxml"));
            aclog.getChildren().clear();  // Restored clear() to avoid overlapping
            aclog.getChildren().add(dashboard);
        } else {
            new Alert(Alert.AlertType.ERROR, "Username or Password is incorrect!").show();
        }
    }

}
