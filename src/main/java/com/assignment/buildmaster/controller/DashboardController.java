package com.assignment.buildmaster.controller;

import com.assignment.buildmaster.dao.custom.ClientDAO;
import com.assignment.buildmaster.view.tdm.ProjectExpensesTM;
import com.assignment.buildmaster.dao.custom.impl.ClientDAOImpl;
import com.assignment.buildmaster.dao.custom.impl.DashboardDAOImpl;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    private final DashboardDAOImpl dashboardDAOImpl = new DashboardDAOImpl();
    ClientDAO clientDAO = new ClientDAOImpl();

    @FXML
    private AnchorPane dashboardForm;

    @FXML
    private JFXListView<String> listAllProject;

    @FXML
    private Label label;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblClientCount;

    @FXML
    private Label lblTotalIncome;

    @FXML
    private Label lblTotalExpenses;

    @FXML
    private Label lblTotalProfit;

    @FXML
    private TableColumn<ProjectExpensesTM, String> colProjectID;

    @FXML
    private TableColumn<ProjectExpensesTM, String> colProjectName;

    @FXML
    private TableColumn<ProjectExpensesTM, Double> colProjectTotalCost;

    @FXML
    private TableColumn<ProjectExpensesTM, Double> colTotalExpenses;

    @FXML
    private TableColumn<ProjectExpensesTM, Double> colTotalMaterialCost;

    @FXML
    private TableView<ProjectExpensesTM> projectExpensesTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadProjectName();
        try {
            loadTotalIncome();
            loadTotalExpenses();
            loadTotalProfit();
            loadClientCount();
            loadProjectData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        lblDate.setText(LocalDate.now().toString());

    }

    private void loadProjectName(){
        try {
            ArrayList<String> projectName = dashboardDAOImpl.getAllProjectName();
            ObservableList<String> observableList = FXCollections.observableArrayList();
            observableList.addAll(projectName);
            listAllProject.setItems(observableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadClientCount() throws SQLException {
        int count = clientDAO.getCount();
        lblClientCount.setText(String.valueOf(count));
    }

    private void loadTotalIncome() throws SQLException {
        double totalCash = dashboardDAOImpl.getTotalIncome();
        lblTotalIncome.setText(String.valueOf(totalCash));
    }

    private void loadTotalExpenses() throws SQLException {
        double totalExpenses = dashboardDAOImpl.getAllExpenses();
        lblTotalExpenses.setText(String.valueOf(totalExpenses));
    }

    private void loadTotalProfit() throws SQLException {
        double totalCash = dashboardDAOImpl.getTotalIncome();
        double totalExpenses = dashboardDAOImpl.getAllExpenses();
        lblTotalProfit.setText(String.valueOf(totalCash - totalExpenses));
    }

    private void loadProjectData() throws SQLException {
        ObservableList<ProjectExpensesTM> projectList = FXCollections.observableArrayList(dashboardDAOImpl.getProjectDetails());

        colProjectID.setCellValueFactory(new PropertyValueFactory<>("projectID"));
        colProjectName.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        colTotalExpenses.setCellValueFactory(new PropertyValueFactory<>("totalExpenses"));
        colTotalMaterialCost.setCellValueFactory(new PropertyValueFactory<>("totalMaterialCost"));
        colProjectTotalCost.setCellValueFactory(new PropertyValueFactory<>("projectTotalCost"));

        projectExpensesTable.setItems(projectList);
    }

}
