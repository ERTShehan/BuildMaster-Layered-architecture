package com.assignment.buildmaster.controller;

import com.assignment.buildmaster.AppInitializer;
import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainLayoutController implements Initializable {

    @FXML
    private JFXButton btnClient;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnPayment;

    @FXML
    private JFXButton btnProject;

    @FXML
    private AnchorPane content;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnMachine;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnExpenses;

    @FXML
    private JFXButton btnMaterials;

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private JFXButton btnReport;

    @FXML
    void onClientAction(ActionEvent event) {
        navigateTo("/view/ClientForm.fxml");
    }

    @FXML
    void onDashboardAction(ActionEvent event) {
        navigateTo("/view/Dashboard.fxml");
    }

    @FXML
    void onPaymentAction(ActionEvent event) {
        navigateTo("/view/PaymentForm.fxml");
    }

    @FXML
    void onProjectAction(ActionEvent event) {
        navigateTo("/view/ProjectForm.fxml");
    }

    @FXML
    void onEmployeeAction(ActionEvent event) {
        navigateTo("/view/EmployeeForm.fxml");
    }

    @FXML
    void onMachineAction(ActionEvent event) {
        navigateTo("/view/MachineForm.fxml");
    }

    @FXML
    void onExpensesAction(ActionEvent event) {
        navigateTo("/view/ExpensesForm.fxml");
    }

    @FXML
    void onMaterialAction(ActionEvent event) {
        navigateTo("/view/MaterialForm.fxml");
    }

    @FXML
    void onSupplierAction(ActionEvent event) {
        navigateTo("/view/SupplierForm.fxml");
    }

    @FXML
    void onReportAction(ActionEvent event) {
        navigateTo("/view/Report.fxml");
    }

    @FXML
    void onLogoutAction(ActionEvent event) throws IOException {
        try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure Logout the System?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.get() == ButtonType.YES){
                Stage stage = (Stage) btnLogout.getScene().getWindow();
                stage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/OpenPage.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage1 = new Stage();
                stage1.setScene(scene);
                stage1.setMinWidth(1300);
                stage1.setMinHeight(600);
//                stage1.setMaximized(true);
                stage1.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error Logout").show();
        }

    }

    public void navigateTo(String fxmlPath) {
        try {
            content.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            load.prefWidthProperty().bind(content.widthProperty());
            load.prefHeightProperty().bind(content.heightProperty());

//            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), load);
//            fadeIn.setFromValue(0.0);
//            fadeIn.setToValue(1.0);

            TranslateTransition slideIn = new TranslateTransition(Duration.seconds(0.3), load);
            slideIn.setFromX(-100); // Start from outside the left
            slideIn.setToX(5);

            content.getChildren().add(load);
            slideIn.play();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/Dashboard.fxml");
    }
}
