package com.assignment.buildmaster.controller;

import com.assignment.buildmaster.dao.custom.impl.UserDAOImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class CreateNewPasswordController {

    private final UserDAOImpl userDAOImpl = new UserDAOImpl();

    @FXML
    private JFXButton btnSavePassword;

    @FXML
    private Pane newPasswordPane;

    @FXML
    private JFXPasswordField txtConfirmPassword;

    @FXML
    private JFXPasswordField txtNewPassword;

    @FXML
    void onSavePassword(ActionEvent event) {
        String newPassword = txtNewPassword.getText().trim();
        String confirmPassword = txtConfirmPassword.getText().trim();

        if (!newPassword.equals(confirmPassword)){
            showAlert("Error", "Passwords do not match.");
        } else if (userDAOImpl.updatePassword(ForgotPasswordController.userEmail, newPassword)) {
            showAlert("Success", "Password updated successfully!");
            loadResetPasswordForm();
        } else {
            showAlert("Error", "Failed to update password.");
        }
    }

    public void showAlert(String title, String message) {
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
            newPasswordPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/PasswordChanged.fxml"));
            newPasswordPane.getChildren().add(load);
            Stage stage = (Stage) newPasswordPane.getScene().getWindow();
            stage.setTitle("Password Changed");
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to load Reset Password Form!");
        }
    }

}
