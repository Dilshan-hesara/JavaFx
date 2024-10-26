package edu.fxdemo.supermarketfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.application.Platform;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.regex.Pattern;

public class sendMailController {

    @FXML
    private TextArea txtBody;

    @FXML
    private TextField txtSubject;

    @FXML
    private TextField sendMailText;  // Text field where customer email is entered

    @FXML
    void sendOnAction(ActionEvent event) {

        final String FROM = "hesarsdilshan@gmail.com";
        final String PASSWORD = "bvij kqpd iict uyda"; // Use App-specific password if using Gmail 2FA

        // Get the subject and body from the text fields
        String subject = txtSubject.getText();
        String body = txtBody.getText();
        String recipientEmail = sendMailText.getText();  // Get the email from the text field

        // Validate subject, body, and recipient email
        if (subject.isEmpty() || body.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Subject and body must not be empty!");
            return;
        }

        if (!isValidEmail(recipientEmail)) {
            showAlert(Alert.AlertType.WARNING, "Invalid email address!");
            return;
        }

        // Setup mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a session with an authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, "qzpf avux wzxf kzuk");
            }
        });

        new Thread(() -> {
            try {
                // Create a MimeMessage object
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(FROM));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject(subject);
                message.setText(body);

                // Send the email
                Transport.send(message);

                // Show success alert on the JavaFX Application Thread
                Platform.runLater(() -> showAlert(Alert.AlertType.INFORMATION, "Email sent successfully!"));

            } catch (MessagingException e) {
                e.printStackTrace();
                Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Error sending email: " + e.getMessage()));
            }
        }).start();  // Run email sending in a separate thread to avoid blocking the UI
    }

    // Utility method to show alert on the JavaFX thread
    private void showAlert(Alert.AlertType alertType, String message) {
        Platform.runLater(() -> new Alert(alertType, message).show());
    }

    // Validate email format using regex
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
