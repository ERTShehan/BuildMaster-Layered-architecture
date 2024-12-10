package com.assignment.buildmasternew.controller;

import com.assignment.buildmasternew.dto.PaymentDto;
import com.assignment.buildmasternew.dto.ProjectDto;
import com.assignment.buildmasternew.dto.tm.PaymentTM;
import com.assignment.buildmasternew.model.PaymentModel;
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

import java.sql.SQLException;
import java.util.List;

public class AllPaymentTableController {

    @FXML
    private JFXButton btnCloseTable;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentAmount;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentDate;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentId;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentType;

    @FXML
    private TableColumn<PaymentTM, String> colProjectId;

    @FXML
    private AnchorPane paymentTablePane;

    @FXML
    private TableView<PaymentDto> tblPayment;

    private final PaymentModel paymentModel = new PaymentModel();

    public void initialize(){
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentID"));
        colProjectId.setCellValueFactory(new PropertyValueFactory<>("projectID"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPaymentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colPaymentAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        loadPaymentData();
    }

    private void loadPaymentData(){
        try {
            List<PaymentDto> payments = paymentModel.getAllPayments();
            ObservableList<PaymentDto> paymentList = FXCollections.observableArrayList(payments);
            tblPayment.setItems(paymentList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClientTableClose(ActionEvent event) {
        Stage stage = (Stage) btnCloseTable.getScene().getWindow();
        stage.close();
    }

}