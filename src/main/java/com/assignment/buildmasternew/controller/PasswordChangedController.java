package com.assignment.buildmasternew.controller;

import com.assignment.buildmasternew.AppInitializer;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class PasswordChangedController {

    @FXML
    private AnchorPane changeSucessPane;

    @FXML
    void onContinue(ActionEvent event) {
//        try {
//            changeSucessPane.getChildren().clear();
//            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
//            changeSucessPane.getChildren().add(load);
//            Stage stage = (Stage) changeSucessPane.getScene().getWindow();
////            stage.setTitle("Enter New Password");
//        } catch (IOException e) {
//            e.printStackTrace();
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setContentText("Failed to load Login Form!");
//        }

//        showAlert("Success", "Password reset complete. Returning to login.");
//
//        // Code to navigate back to LoginForm.fxml
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/LoginForm.fxml"));
//            Scene loginScene = new Scene(fxmlLoader.load());
//
//            // Get current stage and set new scene
//            Stage currentStage = (Stage) changeSucessPane.getScene().getWindow();
//            currentStage.setTitle("Login - BuildMaster");
//            currentStage.setScene(loginScene);
//            currentStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            showAlert("Error", "Failed to load the login screen.");
//        }

        try {
            changeSucessPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
            changeSucessPane.getChildren().add(load);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load page!");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
