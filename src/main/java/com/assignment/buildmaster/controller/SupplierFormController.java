package com.assignment.buildmaster.controller;

import com.assignment.buildmaster.dto.SupplierDto;
import com.assignment.buildmaster.dao.custom.Impl.SupplierDAOImpl;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {
    private final SupplierDAOImpl supplierDAOImpl = new SupplierDAOImpl();

    @FXML
    private JFXButton btnSupplierDelete;

    @FXML
    private JFXButton btnSupplierReset;

    @FXML
    private JFXButton btnSupplierSave;

    @FXML
    private JFXButton btnSupplierUpdate;

    @FXML
    private JFXComboBox<String> cmbSupplierId;

    @FXML
    private Label lblSupplierID;

    @FXML
    private AnchorPane supplierPane;

    @FXML
    private JFXTextField txtSupplierAddress;

    @FXML
    private JFXTextField txtSupplierEmail;

    @FXML
    private JFXTextField txtSupplierName;

    @FXML
    private JFXTextField txtSupplierPhoneNo;

    @FXML
    void cmbSupplierOnAction(ActionEvent event) throws SQLException {
        String selectedSupplierId = cmbSupplierId.getSelectionModel().getSelectedItem();
        if (selectedSupplierId != null) {
            SupplierDto supplierDto = supplierDAOImpl.findById(selectedSupplierId);

            if (supplierDto != null) {
                lblSupplierID.setText(supplierDto.getSupplierId());
                txtSupplierName.setText(supplierDto.getName());
                txtSupplierAddress.setText(supplierDto.getAddress());
                txtSupplierPhoneNo.setText(supplierDto.getPhoneNo());
                txtSupplierEmail.setText(supplierDto.getEmail());

                btnSupplierUpdate.setDisable(false);
                btnSupplierDelete.setDisable(false);
                btnSupplierSave.setDisable(true);
            }
        }
    }

    @FXML
    void onDeleteSupplier(ActionEvent event) {
        try {
            String supplierId = lblSupplierID.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Supplier?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();

            if(buttonType.get() == ButtonType.YES){
                boolean isDeleted = supplierDAOImpl.deleteSupplier(supplierId);

                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Supplier deleted successfully!").show();
                    refreshPage();
                    resetFieldStyles();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete supplier.").show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while deleting supplier.").show();
        }
    }

    @FXML
    void onLoadMachineTable(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AllSupplierTable.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);

            Scene scene = new Scene(root);
            scene.setFill(null);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSaveSupplier(ActionEvent event) throws SQLException {
        String supplierId = lblSupplierID.getText();
        String supplierName = txtSupplierName.getText();
        String supplierAddress = txtSupplierAddress.getText();
        String supplierPhoneNo = txtSupplierPhoneNo.getText();
        String supplierEmail = txtSupplierEmail.getText();

        if(validation(supplierName, supplierAddress, supplierPhoneNo, supplierEmail)) {
            SupplierDto supplierDto = new SupplierDto(supplierId, supplierName, supplierAddress, supplierPhoneNo, supplierEmail);

            boolean isSaved = supplierDAOImpl.saveSupplier(supplierDto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier saved...!").show();
                refreshPage();
                resetFieldStyles();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Supplier...!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fix the highlighted fields.").show();
        }
    }

    @FXML
    void onSupplierResetAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void onUpdateSupplier(ActionEvent event) {
        String supplierId = lblSupplierID.getText();
        String supplierName = txtSupplierName.getText();
        String supplierAddress = txtSupplierAddress.getText();
        String supplierPhoneNo = txtSupplierPhoneNo.getText();
        String supplierEmail = txtSupplierEmail.getText();

        if(validation(supplierName, supplierAddress, supplierPhoneNo, supplierEmail)) {
            try {
                SupplierDto supplierDto = new SupplierDto(supplierId, supplierName, supplierAddress, supplierPhoneNo, supplierEmail);
                boolean isUpdated = supplierDAOImpl.updateSupplier(supplierDto);

                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Supplier updated successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update Supplier.").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while updating Supplier.").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fix the highlighted fields.").show();
        }
    }

    private boolean validation(String supplierName, String supplierAddress, String supplierPhoneNo, String supplierEmail){
        String validPattern = "^(?=.*[a-zA-Z])[a-zA-Z0-9]{1,150}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = supplierName.matches(validPattern);
        boolean isValidAddress = supplierAddress.matches(validPattern);
        boolean isValidPhone = supplierPhoneNo.matches(phonePattern);
        boolean isvalidEmail = supplierEmail.matches(emailPattern);

        txtSupplierName.setStyle(txtSupplierName.getStyle() + ";-fx-border-color: #7367F0;");
        txtSupplierAddress.setStyle(txtSupplierAddress.getStyle() + ";-fx-border-color: #7367F0;");
        txtSupplierPhoneNo.setStyle(txtSupplierPhoneNo.getStyle() + ";-fx-border-color: #7367F0;");
        txtSupplierEmail.setStyle(txtSupplierEmail.getStyle() + ";-fx-border-color: #7367F0;");

        if (!isValidName) {
            txtSupplierName.setStyle(txtSupplierName.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidAddress) {
            txtSupplierAddress.setStyle(txtSupplierAddress.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtSupplierPhoneNo.setStyle(txtSupplierPhoneNo.getStyle() + ";-fx-border-color: red;");
        }

        if (!isvalidEmail) {
            txtSupplierEmail.setStyle(txtSupplierEmail.getStyle() + ";-fx-border-color: red;");
        }

        return isValidName && isValidAddress && isValidPhone && isvalidEmail;
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
        String nextSupplierId = supplierDAOImpl.getNextSupplierId();
        lblSupplierID.setText(nextSupplierId);

        loadSupplierIds();

        cmbSupplierId.getSelectionModel().clearSelection();
        lblSupplierID.setText("");
        txtSupplierName.clear();
        txtSupplierPhoneNo.clear();
        txtSupplierEmail.clear();
        txtSupplierAddress.clear();
        lblSupplierID.setText(nextSupplierId);
        resetFieldStyles();

        btnSupplierSave.setDisable(false);
        btnSupplierUpdate.setDisable(true);
        btnSupplierDelete.setDisable(true);
    }

    private void resetFieldStyles(){
        txtSupplierName.setStyle("-fx-border-color: transparent;");
        txtSupplierAddress.setStyle("-fx-border-color: transparent;");
        txtSupplierPhoneNo.setStyle("-fx-border-color: transparent;");
        txtSupplierEmail.setStyle("-fx-border-color: transparent;");
    }

    private void loadSupplierIds() throws SQLException {
        ArrayList<String> supplierIds = supplierDAOImpl.getAllSupplierIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(supplierIds);
        cmbSupplierId.setItems(observableList);
    }
}