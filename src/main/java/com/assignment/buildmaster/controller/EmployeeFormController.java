package com.assignment.buildmaster.controller;

import com.assignment.buildmaster.bo.BOFactory;
import com.assignment.buildmaster.bo.custom.EmployeeBO;
import com.assignment.buildmaster.dto.EmployeeDto;
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

public class EmployeeFormController implements Initializable {
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);

    @FXML
    private JFXButton btnEmployeeDelete;

    @FXML
    private JFXButton btnEmployeeRegister;

    @FXML
    private JFXButton btnEmployeeReset;

    @FXML
    private JFXButton btnEmployeeUpdate;

    @FXML
    private JFXComboBox<String> cmbEmployeeId;

    @FXML
    private AnchorPane employeePane;

    @FXML
    private Label lblEmployeeID;

    @FXML
    private JFXTextField txtEmployeeAddress;

    @FXML
    private JFXTextField txtEmployeeName;

    @FXML
    private JFXTextField txtEmployeePhone;

    @FXML
    private JFXTextField txtEmployeeRole;

    @FXML
    void cmbClientOnAction(ActionEvent event) throws SQLException {
        String selectedEmployeeId = cmbEmployeeId.getSelectionModel().getSelectedItem();
        if (selectedEmployeeId != null) {
            EmployeeDto employeeDto = employeeBO.findByEmployeeId(selectedEmployeeId);

            if (employeeDto != null) {
                lblEmployeeID.setText(employeeDto.getEmployeeId());
                txtEmployeeName.setText(employeeDto.getName());
                txtEmployeePhone.setText(employeeDto.getPhoneNo());
                txtEmployeeAddress.setText(employeeDto.getAddress());
                txtEmployeeRole.setText(employeeDto.getRole());

                btnEmployeeUpdate.setDisable(false);
                btnEmployeeDelete.setDisable(false);

                btnEmployeeRegister.setDisable(true);
            }
        }
    }

    @FXML
    void onDeleteEmployee(ActionEvent event) {
        try {
            String employeeId = lblEmployeeID.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Employee?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();


            if(buttonType.get() == ButtonType.YES){
                boolean isDeleted = employeeBO.deleteEmployee(employeeId);

                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete employee.").show();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while deleting employee.").show();
        }
    }

    @FXML
    void onEmployeeResetAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void onLoadEmployeeTable(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AllEmployeeTable.fxml"));
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
    void onRegisterEmployee(ActionEvent event) throws SQLException {
        String employeeId = lblEmployeeID.getText();
        String name = txtEmployeeName.getText();
        String phoneNo = txtEmployeePhone.getText();
        String address = txtEmployeeAddress.getText();
        String role = txtEmployeeRole.getText();

        String namePattern = "^[a-zA-Z\\s]{3,100}$";
        String phonePattern = "^\\+?\\(?\\d{1,4}\\)?[\\s\\-]?\\d{1,4}[\\s\\-]?\\d{1,4}[\\s\\-]?\\d{1,4}$";
        String addressPattern = "^[a-zA-Z0-9\\s,.-]{5,255}$";
        String rolePattern = "^[a-zA-Z0-9\\s]{3,50}$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidPhone = phoneNo.matches(phonePattern);
        boolean isValidAddress = address.matches(addressPattern);
        boolean isValidRole = role.matches(rolePattern);

        txtEmployeeName.setStyle(txtEmployeeName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmployeePhone.setStyle(txtEmployeePhone.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmployeeAddress.setStyle(txtEmployeeAddress.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmployeeRole.setStyle(txtEmployeeRole.getStyle() + ";-fx-border-color: #7367F0;");

        if (!isValidName) {
            txtEmployeeName.setStyle(txtEmployeeName.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtEmployeePhone.setStyle(txtEmployeePhone.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidAddress) {
            txtEmployeeAddress.setStyle(txtEmployeeAddress.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidRole) {
            txtEmployeeRole.setStyle(txtEmployeeRole.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidPhone && isValidAddress && isValidRole){
            EmployeeDto employeeDto = new EmployeeDto(employeeId, name, phoneNo, address, role);

            boolean isRegister = employeeBO.saveEmployee(employeeDto);

            if (isRegister) {
                new Alert(Alert.AlertType.INFORMATION, "Employee saved...!").show();
                resetFieldStyles();
                refreshPage();

            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save employee...!").show();
            }
        }
    }

    @FXML
    void onUpdateEmployee(ActionEvent event) {
        String employeeId = lblEmployeeID.getText();
        String name = txtEmployeeName.getText();
        String phoneNo = txtEmployeePhone.getText();
        String address = txtEmployeeAddress.getText();
        String role = txtEmployeeRole.getText();

        String namePattern = "^[a-zA-Z\\s]{3,100}$";
        String phonePattern = "^\\+?\\(?\\d{1,4}\\)?[\\s\\-]?\\d{1,4}[\\s\\-]?\\d{1,4}[\\s\\-]?\\d{1,4}$";
        String addressPattern = "^[a-zA-Z0-9\\s,.-]{5,255}$";
        String rolePattern = "^[a-zA-Z0-9\\s]{3,50}$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidPhone = phoneNo.matches(phonePattern);
        boolean isValidAddress = address.matches(addressPattern);
        boolean isValidRole = role.matches(rolePattern);

        if (!isValidName) {
            txtEmployeeName.setStyle("-fx-border-color: red;");
        }
        if (!isValidPhone) {
            txtEmployeePhone.setStyle("-fx-border-color: red;");
        }
        if (!isValidAddress) {
            txtEmployeeAddress.setStyle("-fx-border-color: red;");
        }
        if (!isValidRole) {
            txtEmployeeRole.setStyle("-fx-border-color: red;");
        }

        if (isValidName && isValidPhone && isValidAddress && isValidRole) {
            try {
                EmployeeDto employeeDto = new EmployeeDto(employeeId, name, phoneNo, address, role);
                boolean isUpdated = employeeBO.updateEmployee(employeeDto);

                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee updated successfully!").show();
                    refreshPage();
                    resetFieldStyles();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update employee.").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while updating employee.").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fix the highlighted fields.").show();
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
        String nextEmployeeID = employeeBO.getNextEmployeeId();
        lblEmployeeID.setText(nextEmployeeID);

        loadEmployeeIds();

        cmbEmployeeId.getSelectionModel().clearSelection();
        txtEmployeeName.clear();
        txtEmployeePhone.clear();
        txtEmployeeAddress.clear();
        txtEmployeeRole.clear();

        lblEmployeeID.setText(nextEmployeeID);

        resetFieldStyles();

        btnEmployeeRegister.setDisable(false);
        btnEmployeeUpdate.setDisable(true);
        btnEmployeeDelete.setDisable(true);
    }

    private void resetFieldStyles() {
        txtEmployeeName.setStyle("-fx-border-color: transparent;");
        txtEmployeePhone.setStyle("-fx-border-color: transparent;");
        txtEmployeeAddress.setStyle("-fx-border-color: transparent;");
        txtEmployeeRole.setStyle("-fx-border-color: transparent;");
    }

    private void loadEmployeeIds() throws SQLException {
        ArrayList<String> employeeIds = employeeBO.getAllEmployeeIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(employeeIds);
        cmbEmployeeId.setItems(observableList);
    }
}
