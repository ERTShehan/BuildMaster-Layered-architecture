package com.assignment.buildmasternew.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class OpenPageController implements Initializable {

    @FXML
    private AnchorPane firstPage;

    @FXML
    private AnchorPane dataLoadPane;

    @FXML
    void onLoadLogin(MouseEvent event) {
        navigateTo("/view/LoginForm.fxml");
    }

    public void navigateTo(String fxmlPath){
        try {
            dataLoadPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            dataLoadPane.getChildren().add(load);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load page!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/Logo.fxml");
    }
}
