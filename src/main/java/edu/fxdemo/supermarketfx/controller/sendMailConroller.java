package edu.fxdemo.supermarketfx.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class sendMailConroller {

        @FXML
        private TextArea txtBody;

        @FXML
        private TextField txtSubject;

        public String customerEmail;


        @FXML
        void sendOnAction(ActionEvent event) {

            final String FROM = "replace-your-email";

            // Get the subject and body from the text fields
            String subject = txtSubject.getText();
            String body = txtBody.getText();

            // Check if subject or body is empty; show a warning if they are
            if (subject.isEmpty() || body.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Subject and body must not be empty!").show();
                return;
            }

        }

    }



