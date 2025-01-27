package com.assignment.buildmaster;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/OpenPage.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle("BUILDMASTER");
            stage.setScene(scene);
            stage.setMinWidth(1300);
            stage.setMinHeight(600);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
