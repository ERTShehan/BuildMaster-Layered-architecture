package com.assignment.buildmasternew.controller;

import com.assignment.buildmasternew.dto.PaymentDto;
import com.assignment.buildmasternew.model.PaymentModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentFormController implements Initializable {
    private final PaymentModel paymentModel = new PaymentModel();

    @FXML
    private Button btnAllPayment;

    @FXML
    private JFXButton btnPaymentDelete;

    @FXML
    private JFXButton btnPaymentReset;

    @FXML
    private JFXButton btnPaymentSave;

    @FXML
    private JFXButton btnPaymentUpdate;

    @FXML
    private JFXComboBox<String> cmbPaymentId;

    @FXML
    private JFXComboBox<String> cmbProjectIdPayment;

    @FXML
    private Label lblPaymentID;

    @FXML
    private Label lblProjectNamePayment;

    @FXML
    private AnchorPane paymentPane;

    @FXML
    private JFXTextField txtPaymentAmount;

    @FXML
    private JFXTextField txtPaymentDate;

    @FXML
    private JFXTextField txtPaymentType;

    @FXML
    void cmbPaymentIdOnAction(ActionEvent event) throws SQLException {
        String selectedPaymentId = cmbPaymentId.getSelectionModel().getSelectedItem();
        if (selectedPaymentId != null) {
            PaymentDto paymentDto = paymentModel.findByPaymentId(selectedPaymentId);
            if (paymentDto != null) {
                lblProjectNamePayment.setText(paymentModel.findProjectNameById(paymentDto.getProjectID()));

                lblPaymentID.setText(paymentDto.getPaymentID());
                cmbProjectIdPayment.setValue(paymentDto.getProjectID());
                txtPaymentDate.setText(paymentDto.getDate());
                txtPaymentType.setText(paymentDto.getType());
                txtPaymentAmount.setText(paymentDto.getAmount());
            }
        }

        btnPaymentUpdate.setDisable(false);
        btnPaymentDelete.setDisable(false);

        btnPaymentSave.setDisable(true);
    }

    @FXML
    void cmbProjectIdLoadPayment(ActionEvent event) throws SQLException {
        String selectedProjectId = cmbProjectIdPayment.getSelectionModel().getSelectedItem();
        if (selectedProjectId != null) {
            lblProjectNamePayment.setText(paymentModel.findProjectNameById(selectedProjectId));
            loadPaymentIdsByProject(selectedProjectId);
        }
        btnPaymentSave.setDisable(false);
    }

    @FXML
    void onPaymentDeleteAction(ActionEvent event) {
        try {
            String paymentId = lblPaymentID.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this payment?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();

            if(buttonType.get() == ButtonType.YES){
                boolean isDeleted = paymentModel.deletePayment(paymentId);

                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete payment.").show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while deleting payment.").show();
        }
    }

    @FXML
    void onPaymentResetAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void onPaymentSaveAction(ActionEvent event) throws SQLException {
        String paymentID = lblPaymentID.getText();
        String projectID = cmbProjectIdPayment.getValue();
        String date = txtPaymentDate.getText();
        String type = txtPaymentType.getText();
        String amount = txtPaymentAmount.getText();

        String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";
        String typePattern = "^[a-zA-Z0-9\\s]{3,50}$";
        String amountPattern = "^[0-9]+(\\.[0-9]{1,2})?$";

        boolean isValidDate = date.matches(datePattern);
        boolean isValidType = type.matches(typePattern);
        boolean isValidAmount = amount.matches(amountPattern);

        txtPaymentDate.setStyle(txtPaymentDate.getStyle() + ";-fx-border-color: #7367F0;");
        txtPaymentType.setStyle(txtPaymentType.getStyle() + ";-fx-border-color: #7367F0;");
        txtPaymentAmount.setStyle(txtPaymentAmount.getStyle() + ";-fx-border-color: #7367F0;");

        if (!isValidDate) {
            txtPaymentDate.setStyle(txtPaymentDate.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidType) {
            txtPaymentType.setStyle(txtPaymentType.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidAmount) {
            txtPaymentAmount.setStyle(txtPaymentAmount.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidDate && isValidType && isValidAmount) {
            try {
                PaymentDto paymentDto = new PaymentDto(paymentID, projectID, date, type, amount);

                boolean isSaved = paymentModel.savePayment(paymentDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment saved...!").show();
                    resetFieldStyles();
                    refreshPage();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to save Payment Details...!").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while saving payment.").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fix the highlighted fields.").show();
        }
    }

    @FXML
    void onPaymentUpdateAction(ActionEvent event) throws SQLException {
        String paymentID = lblPaymentID.getText();
        String projectID = cmbProjectIdPayment.getValue();
        String date = txtPaymentDate.getText();
        String type = txtPaymentType.getText();
        String amount = txtPaymentAmount.getText();

        String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";
        String typePattern = "^[a-zA-Z0-9\\s]{3,50}$";
        String amountPattern = "^[0-9]+(\\.[0-9]{1,2})?$";

        boolean isValidDate = date.matches(datePattern);
        boolean isValidType = type.matches(typePattern);
        boolean isValidAmount = amount.matches(amountPattern);

        if (!isValidDate) {
            txtPaymentDate.setStyle("-fx-border-color: red;");
        }

        if (!isValidType) {
            txtPaymentType.setStyle("-fx-border-color: red;");
        }

        if (!isValidAmount) {
            txtPaymentAmount.setStyle("-fx-border-color: red;");
        }

        if (isValidDate && isValidType && isValidAmount) {
            try {
                PaymentDto paymentDto = new PaymentDto(paymentID, projectID, date, type, amount);
                boolean isUpdated = paymentModel.updatePayment(paymentDto);

                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment updated successfully!").show();
                    resetFieldStyles();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update payment.").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while updating payment.").show();
            }

        }  else {
            new Alert(Alert.AlertType.ERROR, "Please fix the highlighted fields.").show();
        }
    }

    @FXML
    void onViewAllPayment(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AllPaymentsTable.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);

            Scene scene = new Scene(root);
            scene.setFill(null);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        lblPaymentID.setText(paymentModel.getNextPaymentId());
        loadPaymentIds();
        loadProjectIds();
        cmbPaymentId.getSelectionModel().clearSelection();
        cmbProjectIdPayment.getSelectionModel().clearSelection();
        txtPaymentDate.clear();
        txtPaymentType.clear();
        txtPaymentAmount.clear();
        lblProjectNamePayment.setText("");
        lblPaymentID.setText(paymentModel.getNextPaymentId());
        resetFieldStyles();

        btnPaymentSave.setDisable(true);
        btnPaymentUpdate.setDisable(true);
        btnPaymentDelete.setDisable(true);
    }

    private void resetFieldStyles(){
        txtPaymentDate.setStyle("-fx-border-color: transparent;");
        txtPaymentType.setStyle("-fx-border-color: transparent;");
        txtPaymentAmount.setStyle("-fx-border-color: transparent;");
    }

    private void loadPaymentIds() throws SQLException {
        ArrayList<String> paymentIds = paymentModel.getAllPaymentIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(paymentIds);
        cmbPaymentId.setItems(observableList);
    }

    private void loadProjectIds() throws SQLException {
        ArrayList<String> projectIds = paymentModel.getAllProjectIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(projectIds);
        cmbProjectIdPayment.setItems(observableList);
    }

    private void loadPaymentIdsByProject(String projectId) throws SQLException {
        ArrayList<String> projectIds = paymentModel.getAllPaymentIdsByProject(projectId);
        cmbPaymentId.setItems(FXCollections.observableArrayList(projectIds));
    }
}