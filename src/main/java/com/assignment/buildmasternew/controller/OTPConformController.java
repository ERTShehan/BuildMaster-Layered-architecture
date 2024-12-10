package com.assignment.buildmasternew.controller;

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

import java.io.IOException;

public class OTPConformController {

    @FXML
    private JFXButton btnVerify;

    @FXML
    private JFXTextField txtOtp;

    @FXML
    private Pane otpPane;

    @FXML
    void onVerifyOtp(ActionEvent event) {
        String enteredOtp = txtOtp.getText().trim();

        if (enteredOtp.equals(ForgotPasswordController.OTP)) {
            showAlert("Success", "OTP verified successfully!");
            loadResetPasswordForm();
        } else {
            showAlert("Error", "Incorrect OTP. Please try again.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        PauseTransition delay = new PauseTransition(Duration.seconds(3.0));
        delay.setOnFinished(event -> alert.close());
        delay.play();
        alert.show();
    }

    private void loadResetPasswordForm(){
        try {
            otpPane.getChildren().clear();
            Pane load = FXMLLoader.load(getClass().getResource("/view/CreateNewPassword.fxml"));
            otpPane.getChildren().add(load);
            Stage stage = (Stage) otpPane.getScene().getWindow();
            stage.setTitle("Enter New Password");
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to load Reset Password Form!");
        }
    }

}