package com.assignment.buildmaster.controller;

import com.assignment.buildmaster.dto.SupplierDto;
import com.assignment.buildmaster.view.tdm.SupplierTM;
import com.assignment.buildmaster.dao.custom.Impl.SupplierDAOImpl;
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

public class AllSupplierTableController {

    @FXML
    private JFXButton btnCloseTable;

    @FXML
    private TableColumn<SupplierTM, String> colSupplierAddress;

    @FXML
    private TableColumn<SupplierTM, String> colSupplierEmail;

    @FXML
    private TableColumn<SupplierTM, String> colSupplierId;

    @FXML
    private TableColumn<SupplierTM, String> colSupplierName;

    @FXML
    private TableColumn<SupplierTM, String> colSupplierPhone;

    @FXML
    private AnchorPane supplierTablePane;

    @FXML
    private TableView<SupplierDto> tblSuppliers;

    private final SupplierDAOImpl supplierDAOImpl = new SupplierDAOImpl();

    public void initialize(){
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSupplierAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSupplierPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        colSupplierEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadSupplierData();
    }

    private void loadSupplierData(){
        try {
            List<SupplierDto> suppliers = supplierDAOImpl.getAll();
            ObservableList<SupplierDto> supplierList = FXCollections.observableArrayList(suppliers);
            tblSuppliers.setItems(supplierList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSupplierTableClose(ActionEvent event) {
        Stage stage = (Stage) btnCloseTable.getScene().getWindow();
        stage.close();
    }

}
