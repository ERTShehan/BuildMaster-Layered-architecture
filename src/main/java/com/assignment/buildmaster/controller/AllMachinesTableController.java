package com.assignment.buildmaster.controller;

import com.assignment.buildmaster.dao.custom.MachineDAO;
import com.assignment.buildmaster.dto.MachineDto;
import com.assignment.buildmaster.view.tdm.MachineTM;
import com.assignment.buildmaster.dao.custom.impl.MachineDAOImpl;
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

public class AllMachinesTableController {

    @FXML
    private JFXButton btnCloseTable;

    @FXML
    private TableColumn<MachineTM, String> colMachineDescription;

    @FXML
    private TableColumn<MachineTM, String> colMachineId;

    @FXML
    private TableColumn<MachineTM, String> colMachineName;

    @FXML
    private TableColumn<MachineTM, String> colMachineStatus;

    @FXML
    private AnchorPane machineTablePane;

    @FXML
    private TableView<MachineDto> tblMachines;

    MachineDAO machineDAO = new MachineDAOImpl();

    public void initialize(){
        colMachineId.setCellValueFactory(new PropertyValueFactory<>("machineId"));
        colMachineName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMachineStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colMachineDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        loadMachineData();
    }

    private void loadMachineData(){
        try {
            List<MachineDto> machines = machineDAO.getAll();
            ObservableList<MachineDto> machineList = FXCollections.observableArrayList(machines);
            tblMachines.setItems(machineList);
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
