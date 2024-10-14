package edu.fxdemo.supermarketfx.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class dashController {

    @FXML
    private AnchorPane page1pane;


    @FXML
    void cust_b(ActionEvent event) throws IOException {

        page1pane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/customer.fxml"));
        page1pane.getChildren().add(load);
    }

    @FXML
    void item_b(ActionEvent event) throws IOException {
        page1pane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/item.fxml"));
        page1pane.getChildren().add(load);
    }

    @FXML
    void or_b(ActionEvent event) throws IOException {
        page1pane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/orders.fxml"));
        page1pane.getChildren().add(load);

    }

}
