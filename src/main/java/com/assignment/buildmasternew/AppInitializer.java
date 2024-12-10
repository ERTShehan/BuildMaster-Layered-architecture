package com.assignment.buildmasternew;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ProgressBar progressBar = new ProgressBar();
        VBox splashLayout = new VBox(progressBar);
        splashLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-border-color: black; -fx-background-color: yellow");

        Scene splashScene = new Scene(splashLayout, 400, 200);
        Stage splashStage = new Stage(StageStyle.UNDECORATED);
        splashStage.setScene(splashScene);
        splashStage.show();

        Task<Void> loadingTask = new Task<>() {
            @Override
            protected Void call() throws InterruptedException {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(30);
                    updateProgress(i, 100);
                }
                return null;
            }
        };

        // Bind progress bar to task progress
        progressBar.progressProperty().bind(loadingTask.progressProperty());

        loadingTask.setOnSucceeded(event -> {
            splashStage.close();
            openMainPage(stage);
        });

        new Thread(loadingTask).start();
    }

    private void openMainPage(Stage stage) {
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
