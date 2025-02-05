package com.assignment.buildmaster.controller;

import com.assignment.buildmaster.bo.BOFactory;
import com.assignment.buildmaster.bo.custom.UserBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class ForgotPasswordController {
    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);
//    UserDAO userDAO = new UserDAOImpl();
    static String userEmail;
    static String OTP;

    @FXML
    private JFXButton btnGetOtp;

    @FXML
    private Pane forgotPasswordPane;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    void onGetOtpAction(ActionEvent event) {
        String email = txtEmail.getText().trim();

        Pair<Boolean, String> conform = userBO.checkGmail(email);
        userEmail = conform.getValue();

        if (email.isEmpty()) {
            showAlert("Email Error", "Please enter an email. The email field is empty.");
        } else {
            if (conform.getKey()) {
                try {
                    Random random = new Random();
                    int min = 1111;
                    int max = 9999;
                    OTP = String.valueOf(random.nextInt(max - min + 1) + min);

                    String subject = "OTP code for password reset";
                    String body = "Do not share this code.\nYour OTP code is: " + OTP;

                    String from = "ertsshehan@gmail.com";
                    String host = "smtp.gmail.com";
                    String username = "ertsshehan@gmail.com";
                    String password = "adgx krsm chtt kqiw";

                    Properties properties = new Properties();
                    properties.put("mail.smtp.auth", "true");
                    properties.put("mail.smtp.starttls.enable", "true");
                    properties.put("mail.smtp.host", host);
                    properties.put("mail.smtp.port", "587");

                    Session session = Session.getInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
                    message.setSubject(subject);
                    message.setText(body);

                    Transport.send(message);
                    showAlert("OTP Sent", "An OTP has been sent to your email.");
                    enterOtp();
                    System.out.println(OTP);

                } catch (Exception e) {
                    showAlert("Email Error", "Failed to send OTP. Please try again.\n Check your internet connection");
                    e.printStackTrace();
                }
            } else {
                showAlert("Email Error", "Email does not match any registered account.");
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(content);
        PauseTransition delay = new PauseTransition(Duration.seconds(2.0));
        delay.setOnFinished(e -> alert.close());
        delay.play();
        alert.show();
    }

    private void enterOtp(){
        try {
            forgotPasswordPane.getChildren().clear();
            Pane load = FXMLLoader.load(getClass().getResource("/view/OTPConform.fxml"));
            forgotPasswordPane.getChildren().add(load);
            Stage stage = (Stage) forgotPasswordPane.getScene().getWindow();
            stage.setTitle("Conform OTP");
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to load Conform Form!");
        }
    }
}



//String from = "ertsshehan@gmail.com";
//String host = "smtp.gmail.com";
//String username = "ertsshehan@gmail.com";
//String password = "adgx krsm chtt kqiw";