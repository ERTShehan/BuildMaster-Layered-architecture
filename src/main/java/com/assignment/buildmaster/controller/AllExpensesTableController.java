package com.assignment.buildmaster.controller;

import com.assignment.buildmaster.dto.ExpensesDto;
import com.assignment.buildmaster.view.tdm.ExpensesTM;
import com.assignment.buildmaster.dao.custom.Impl.ExpensesDAOImpl;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;

public class AllExpensesTableController {

    @FXML
    private JFXButton btnCloseTable;

    @FXML
    private TableColumn<ExpensesTM, String> colExpensesAmount;

    @FXML
    private TableColumn<ExpensesTM, String> colExpensesDate;

    @FXML
    private TableColumn<ExpensesTM, String> colExpensesEmployeeId;

    @FXML
    private TableColumn<ExpensesTM, String> colExpensesId;

    @FXML
    private TableColumn<ExpensesTM, String> colExpensesProjectId;

    @FXML
    private TableColumn<ExpensesTM, String> colExpensesType;

    @FXML
    private AnchorPane expensesTablePane;

    @FXML
    private TableView<ExpensesDto> tblExpenses;

    private final ExpensesDAOImpl expensesDAOImpl = new ExpensesDAOImpl();

    public void initialize(){
        colExpensesId.setCellValueFactory(new PropertyValueFactory<>("expenseId"));
        colExpensesType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colExpensesAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colExpensesDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colExpensesProjectId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        colExpensesEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));

        loadExpensesData();
    }

    private void loadExpensesData(){
        try {
            List<ExpensesDto> expenses = expensesDAOImpl.getAllExpenses();
            ObservableList<ExpensesDto> expensesList = FXCollections.observableArrayList(expenses);
            tblExpenses.setItems(expensesList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onExpensesTableClose(ActionEvent event) {
        Stage stage = (Stage) btnCloseTable.getScene().getWindow();
        stage.close();
    }

}
