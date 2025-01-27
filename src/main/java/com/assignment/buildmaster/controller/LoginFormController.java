package com.assignment.buildmaster.controller;

import com.assignment.buildmaster.AppInitializer;
import com.assignment.buildmaster.dao.custom.Impl.UserDAOImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private JFXButton btnSignin;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    void onForgotPasswordAction(ActionEvent event) {
        try {
            loginAnchorPane.getChildren().clear();
            Pane load = FXMLLoader.load(getClass().getResource("/view/ForgotPassword.fxml"));
            loginAnchorPane.getChildren().add(load);
            Stage stage = (Stage) loginAnchorPane.getScene().getWindow();
            stage.setTitle("Forgot Password");
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to load ForgotPasswordForm!");
        }
    }
    UserDAOImpl userDAOImpl = new UserDAOImpl();

    @FXML
    void onSignAction(ActionEvent event) throws IOException {
        String email = txtEmail.getText();
        String password = passwordField.getText();

        if(email.isEmpty() || password.isEmpty()){
            if(email.isEmpty() && password.isEmpty()){
                alert("Logging Error!","Please fill all the fields");
            }else if (email.isEmpty()) {
                alert("Logging Error!","Please fill the email field.");
            }else {
                alert("Logging Error!","Please fill the password field.");
            }
        } else {
            boolean logging = userDAOImpl.verifyUser(email, password);
            logging = true;
            if (!logging) {
                alert("Logging Error!", "Your username and password don't match.");
            } else {
                try {
                    alert("Success", "Login Successfully");
                    FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/MainLayout.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = (Stage) loginAnchorPane.getScene().getWindow();
                    stage.setTitle("BUILDMASTER");
                    stage.setScene(scene);
                    stage.show();
//                    loginAnchorPane.getChildren().clear();
//                    Pane load = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
//                    loginAnchorPane.getChildren().add(load);
//                    Stage stage = (Stage) loginAnchorPane.getScene().getWindow();
//                    stage.setTitle("Dashboard");
                } catch (IOException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to load DashBoard!");
//                    DialogPane dialogPane = alert.getDialogPane();
//                    dialogPane.getStylesheets().add(getClass().getResource("/style/Style2.css").toExternalForm());
                    alert.showAndWait();
                }
            }
        }
    }

    public void alert(String setTitle, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(setTitle);
        alert.setHeaderText(null);
        alert.setContentText(content);
        PauseTransition delay = new PauseTransition(Duration.seconds(2.5));
        delay.setOnFinished(event -> alert.close());
        delay.play();
        alert.show();
    }   
}
