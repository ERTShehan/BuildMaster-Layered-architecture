package com.assignment.buildmaster.controller;

import com.assignment.buildmaster.dao.custom.EmployeeDAO;
import com.assignment.buildmaster.dao.custom.ExpensesDAO;
import com.assignment.buildmaster.dao.custom.ProjectDAO;
import com.assignment.buildmaster.dto.ExpensesDto;
import com.assignment.buildmaster.dao.custom.Impl.EmployeeDAOImpl;
import com.assignment.buildmaster.dao.custom.Impl.ExpensesDAOImpl;
import com.assignment.buildmaster.dao.custom.Impl.ProjectDAOImpl;
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

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ExpensesFormController implements Initializable {

    @FXML
    private Button btnAllExpenses;

    @FXML
    private JFXButton btnExpensesDelete;

    @FXML
    private JFXButton btnExpensesReset;

    @FXML
    private JFXButton btnExpensesSave;

    @FXML
    private JFXButton btnExpensesUpdate;

    @FXML
    private JFXComboBox<String> cmbEmployeeIdExpense;

    @FXML
    private JFXComboBox<String> cmbExpenseId;

    @FXML
    private JFXComboBox<String> cmbProjectIdExpense;

    @FXML
    private AnchorPane expensesPane;

    @FXML
    private Label lblExpenseEmployeeName;

    @FXML
    private Label lblExpenseID;

    @FXML
    private Label lblExpenseProjectName;

    @FXML
    private Label lblExpenseEmployeeRole;

    @FXML
    private JFXTextField txtExpenseType;

    @FXML
    private JFXTextField txtExpensesAmount;

    @FXML
    private JFXTextField txtExpensesDate;

    ExpensesDAO expensesDAO = new ExpensesDAOImpl();
    EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    ProjectDAO projectDAO = new ProjectDAOImpl();

    @FXML
    void cmbEmployeeIdLoadExpenses(ActionEvent event) throws SQLException {
        String selectedEmployeeId = cmbEmployeeIdExpense.getSelectionModel().getSelectedItem();
        if (selectedEmployeeId != null) {
            lblExpenseEmployeeName.setText(employeeDAO.getName(selectedEmployeeId));
            lblExpenseEmployeeRole.setText(employeeDAO.getInfo(selectedEmployeeId));
        }
        btnExpensesSave.setDisable(true);

        checkComboBoxSelection();
    }

    @FXML
    void cmbExpenseIdOnAction(ActionEvent event) throws SQLException {
        String selectedExpenseId = cmbExpenseId.getSelectionModel().getSelectedItem();
        if (selectedExpenseId != null) {
            ExpensesDto expensesDto = expensesDAO.findById(selectedExpenseId);

            if (expensesDto != null) {
                lblExpenseID.setText(expensesDto.getExpenseId());
                txtExpenseType.setText(expensesDto.getType());
                txtExpensesAmount.setText(String.valueOf(expensesDto.getAmount()));
                txtExpensesDate.setText(expensesDto.getDate());
                cmbProjectIdExpense.setValue(expensesDto.getProjectId());
                cmbEmployeeIdExpense.setValue(expensesDto.getEmployeeId());

                btnExpensesUpdate.setDisable(false);
                btnExpensesDelete.setDisable(false);
                btnExpensesSave.setDisable(true);
            }
        }
    }

    @FXML
    void cmbProjectIdLoadExpenses(ActionEvent event) throws SQLException {
        String selectedProjectId = cmbProjectIdExpense.getSelectionModel().getSelectedItem();
        if (selectedProjectId != null) {
            lblExpenseProjectName.setText(projectDAO.getName(selectedProjectId));
        }
        checkComboBoxSelection();
    }

    private void checkComboBoxSelection() {
        boolean isBothSelected = cmbEmployeeIdExpense.getSelectionModel().getSelectedItem() != null && cmbProjectIdExpense.getSelectionModel().getSelectedItem() != null;
        btnExpensesSave.setDisable(!isBothSelected);
    }

    @FXML
    void onExpensesDeleteAction(ActionEvent event) {
        try{
            String expenseId = lblExpenseID.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this expense?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();

            if(buttonType.get() == ButtonType.YES){
                boolean isDeleted = expensesDAO.delete(expenseId);

                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Expense deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete expense.").show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while deleting material.").show();
        }
    }

    @FXML
    void onExpensesResetAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void onExpensesSaveAction(ActionEvent event) throws SQLException {
        String expenseId = lblExpenseID.getText();
        String type = txtExpenseType.getText();
        String amountText = txtExpensesAmount.getText();
        String date = txtExpensesDate.getText();
        String projectId = cmbProjectIdExpense.getSelectionModel().getSelectedItem();
        String employeeId = cmbEmployeeIdExpense.getSelectionModel().getSelectedItem();

        if (amountText.isEmpty() || date.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Amount and Type must not be empty!").show();
            return;
        }

        if (validation(type, amountText, date)) {
            try {
                double amount = Double.parseDouble(amountText);
                ExpensesDto expensesDto = new ExpensesDto(expenseId, type, amount, date, projectId, employeeId);
                boolean isSaved = expensesDAO.save(expensesDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Expense saved...!").show();
                    refreshPage();
                    resetFieldStyles();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to save Expense...!").show();
                }
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid amount format. Please enter a valid number.").show();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fix the highlighted fields.").show();
        }
    }

    @FXML
    void onExpensesUpdateAction(ActionEvent event) {
        String expenseId = lblExpenseID.getText();
        String type = txtExpenseType.getText();
        String amountText = txtExpensesAmount.getText();
        String date = txtExpensesDate.getText();
        String projectId = cmbProjectIdExpense.getSelectionModel().getSelectedItem();
        String employeeId = cmbEmployeeIdExpense.getSelectionModel().getSelectedItem();

        if (amountText.isEmpty() || date.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Amount and Type must not be empty!").show();
            return;
        }

        if (validation(type, amountText, date)) {
            try {
                double amount = Double.parseDouble(amountText);
                ExpensesDto expensesDto = new ExpensesDto(expenseId, type, amount, date, projectId, employeeId);
                boolean isUpdated = expensesDAO.update(expensesDto);
                if(isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Expense update...!").show();
                    refreshPage();
                    resetFieldStyles();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to update Expense...!").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fix the highlighted fields.").show();
        }
    }

    private boolean validation(String type, String amount, String date){
        String validType = "^[A-Za-z0-9!@#$%^&*()_+={}|\\\\[\\\\]:\";\\'<>,.?/-]{1,50}$";
        String validAmount = "^[0-9]{1,8}(\\.[0-9]{1,2})?$";
        String validDate = "^(19|20)\\d\\d-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

        boolean isValidType = type.matches(validType);
        boolean isValidAmount = amount.matches(validAmount);
        boolean isValidDate = date.matches(validDate);

        txtExpenseType.setStyle(txtExpenseType.getStyle() + ";-fx-border-color: #7367F0;");
        txtExpensesAmount.setStyle(txtExpensesAmount.getStyle() + ";-fx-border-color: #7367F0;");
        txtExpensesDate.setStyle(txtExpensesDate.getStyle() + ";-fx-border-color: #7367F0;");

        if (!isValidType) {
            txtExpenseType.setStyle(txtExpenseType.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidAmount) {
            txtExpensesAmount.setStyle(txtExpensesAmount.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidDate) {
            txtExpensesDate.setStyle(txtExpensesDate.getStyle() + ";-fx-border-color: red;");
        }

        return isValidType && isValidAmount && isValidDate;
    }

    @FXML
    void onViewAllExpenses(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AllExpensesTable.fxml"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        String nextExpensesId = expensesDAO.getNextId();
        lblExpenseID.setText(nextExpensesId);

        loadExpensesIds();
        loadEmployeeIds();
        loadProjectIds();

        cmbEmployeeIdExpense.getSelectionModel().clearSelection();
        cmbProjectIdExpense.getSelectionModel().clearSelection();
        cmbExpenseId.getSelectionModel().clearSelection();
        lblExpenseEmployeeName.setText("");
        lblExpenseProjectName.setText("");
        lblExpenseEmployeeRole.setText("");
        lblExpenseID.setText("");
        txtExpenseType.clear();
        txtExpensesAmount.clear();
        txtExpensesDate.clear();
        resetFieldStyles();
        lblExpenseID.setText(nextExpensesId);
        txtExpensesDate.setText(LocalDate.now().toString());

        btnExpensesSave.setDisable(true);
        btnExpensesUpdate.setDisable(true);
        btnExpensesDelete.setDisable(true);
    }

    private void loadExpensesIds() throws SQLException {
        ArrayList<String> expensesIds = expensesDAO.getAllIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(expensesIds);
        cmbExpenseId.setItems(observableList);
    }

    private void loadEmployeeIds() throws SQLException {
        ArrayList<String> employeeIds = employeeDAO.getAllIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(employeeIds);
        cmbEmployeeIdExpense.setItems(observableList);
    }

    private void loadProjectIds() throws SQLException {
        ArrayList<String> projectIds = projectDAO.getAllIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(projectIds);
        cmbProjectIdExpense.setItems(observableList);
    }

    private void resetFieldStyles(){
        txtExpenseType.setStyle("-fx-border-color: transparent");
        txtExpensesAmount.setStyle("-fx-border-color: transparent");
        txtExpensesDate.setStyle("-fx-border-color: transparent");
    }
}