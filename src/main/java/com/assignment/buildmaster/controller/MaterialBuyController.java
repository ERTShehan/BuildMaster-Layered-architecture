package com.assignment.buildmaster.controller;

import com.assignment.buildmaster.dao.custom.MaterialBuyDAO;
import com.assignment.buildmaster.dao.custom.MaterialDAO;
import com.assignment.buildmaster.dto.MaterialBuyDto;
import com.assignment.buildmaster.view.tdm.MaterialBuyTM;
import com.assignment.buildmaster.dao.custom.impl.MaterialBuyDAOImpl;
import com.assignment.buildmaster.dao.custom.impl.MaterialDAOImpl;
import com.assignment.buildmaster.dao.custom.impl.SupplierDAOImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MaterialBuyController implements Initializable {

    @FXML
    private JFXButton btnBackBuy;

    @FXML
    private JFXButton btnBuyDelete;

    @FXML
    private JFXButton btnBuyReset;

    @FXML
    private JFXButton btnBuySave;

    @FXML
    private JFXButton btnBuyUpdate;

    @FXML
    private Button btnCalculateTotal;

    @FXML
    private AnchorPane buyMaterialPane;

    @FXML
    private JFXComboBox<String> cmbMaterialIdBuy;

    @FXML
    private JFXComboBox<String> cmbSupplierIdBuy;

    @FXML
    private TableColumn<MaterialBuyTM, String> colBuyDate;

    @FXML
    private TableColumn<MaterialBuyTM, String> colBuyMaterialId;

    @FXML
    private TableColumn<MaterialBuyTM, String> colBuySupplierId;

    @FXML
    private TableColumn<MaterialBuyTM, String> colPaymentId;

    @FXML
    private TableColumn<MaterialBuyTM, String> colQuantityBuy;

    @FXML
    private TableColumn<MaterialBuyTM, Double> colTotalPrice;

    @FXML
    private TableColumn<MaterialBuyTM, Double> colUnitPrice;

    @FXML
    private Label lblBuyDate;

    @FXML
    private Label lblMaterialNameBuy;

    @FXML
    private Label lblPaymentID;

    @FXML
    private Label lblSupplierNameBuy;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private TableView<MaterialBuyTM> tblMaterialBuy;

    @FXML
    private JFXTextField txtBuyQuantity;

    @FXML
    private JFXTextField txtBuyUnitAmount;

    @FXML
    private Label lblUnitLoadBuy;

    MaterialBuyDAO materialBuyDAO = new MaterialBuyDAOImpl();
    MaterialDAO materialDAO = new MaterialDAOImpl();
    private final SupplierDAOImpl supplierDAOImpl = new SupplierDAOImpl();

    @FXML
    void cmbMaterialIdLoadBuy(ActionEvent event) throws SQLException {
        String selectedMaterialId = cmbMaterialIdBuy.getSelectionModel().getSelectedItem();
        if (selectedMaterialId != null) {
            lblUnitLoadBuy.setText(materialDAO.getUnit(selectedMaterialId));
            lblMaterialNameBuy.setText(materialDAO.getName(selectedMaterialId));
        }
        checkComboBoxSelection();
    }

    @FXML
    void cmbSupplierIdLoadBuy(ActionEvent event) throws SQLException {
        String selectedSupplierId = cmbSupplierIdBuy.getSelectionModel().getSelectedItem();
        if (selectedSupplierId != null) {
            lblSupplierNameBuy.setText(supplierDAOImpl.findNameById(selectedSupplierId));
        }
        checkComboBoxSelection();
    }

    private void checkComboBoxSelection() {
        boolean isBothSelected = cmbMaterialIdBuy.getSelectionModel().getSelectedItem() != null && cmbSupplierIdBuy.getSelectionModel().getSelectedItem() != null && lblTotalPrice.getText() != null;
        btnBuySave.setDisable(!isBothSelected);
    }

    @FXML
    void onBackUsageAction(ActionEvent event) {
        Stage stage = (Stage) btnBackBuy.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onBuyDeleteAction(ActionEvent event) {
        try {
            String paymentId = lblPaymentID.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Material Purchase?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if(buttonType.get() == ButtonType.YES){
                boolean isDeleted = materialBuyDAO.delete(paymentId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Material Purchase deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete Material Purchase.").show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while deleting Material Purchase.").show();
        }
    }

    @FXML
    void onBuyResetAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void onBuySaveAction(ActionEvent event) {
        String paymentId = lblPaymentID.getText();
        String materialId = cmbMaterialIdBuy.getSelectionModel().getSelectedItem();
        String supplierId = cmbSupplierIdBuy.getSelectionModel().getSelectedItem();
        Date date = Date.valueOf(lblBuyDate.getText());
        String unitAmountText = txtBuyUnitAmount.getText();
        String quantityText = txtBuyQuantity.getText();

        if (unitAmountText.isEmpty() || quantityText.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Unit amount and quantity must not be empty!").show();
            return;
        }

        if (validation(unitAmountText, quantityText)) {
            try {
                double unitAmount = Double.parseDouble(unitAmountText);
                String quantity = String.valueOf(Integer.parseInt(quantityText));
                double totalPrice = Double.parseDouble(lblTotalPrice.getText());

                MaterialBuyDto materialBuyDto = new MaterialBuyDto(paymentId, materialId, supplierId, date, unitAmount, quantity, totalPrice);
                boolean isSaved = materialBuyDAO.save(materialBuyDto);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Material Purchase saved...!").show();
                    refreshPage();
                    resetFieldStyles();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to save Material Purchase...!").show();
                }
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid input format. Please enter valid numbers.").show();
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while saving Material Purchase.").show();
            }
        }
    }


    private boolean validation(String unitPrice, String quantity){
        String qtyValidPattern = "^\\d+(\\.\\d{1,2})?$";

        boolean isValidAmount = unitPrice.matches(qtyValidPattern);
        boolean isValidQuantity = quantity.matches(qtyValidPattern);

        txtBuyUnitAmount.setStyle(txtBuyUnitAmount.getStyle() + ";-fx-border-color: #7367F0;");
        txtBuyQuantity.setStyle(txtBuyQuantity.getStyle() + ";-fx-border-color: #7367F0;");

        if (!isValidAmount) {
            txtBuyUnitAmount.setStyle(txtBuyUnitAmount.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidQuantity) {
            txtBuyQuantity.setStyle(txtBuyQuantity.getStyle() + ";-fx-border-color: red;");
        }
        return isValidAmount && isValidQuantity;
    }

    @FXML
    void onBuyUpdateAction(ActionEvent event) {
        String paymentId = lblPaymentID.getText();
        String materialId = cmbMaterialIdBuy.getSelectionModel().getSelectedItem();
        String supplierId = cmbSupplierIdBuy.getSelectionModel().getSelectedItem();
        Date date = Date.valueOf(lblBuyDate.getText());
        double unitAmount = Double.parseDouble(txtBuyUnitAmount.getText());
        String quantity = txtBuyQuantity.getText();
        double totalPrice = Double.parseDouble(lblTotalPrice.getText());

        if(validation(String.valueOf(unitAmount), quantity)){
            try{
                MaterialBuyDto materialBuyDto = new MaterialBuyDto(paymentId, materialId, supplierId, date, unitAmount, quantity, totalPrice);
                boolean isUpdated = materialBuyDAO.update(materialBuyDto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Material Purchase saved...!").show();
                    refreshPage();
                    resetFieldStyles();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to save Material Purchase...!").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        MaterialBuyTM selectedItem = tblMaterialBuy.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblPaymentID.setText(selectedItem.getPaymentId());
            cmbMaterialIdBuy.setValue(selectedItem.getMaterialId());
            cmbSupplierIdBuy.setValue(selectedItem.getSupplierId());
            lblBuyDate.setText(String.valueOf(selectedItem.getDate()));
            txtBuyUnitAmount.setText(String.valueOf(selectedItem.getUnitAmount()));
            txtBuyQuantity.setText(selectedItem.getQuantity());
            lblTotalPrice.setText(String.valueOf(selectedItem.getTotalPrice()));

            btnBuySave.setDisable(true);
            btnBuyUpdate.setDisable(false);
            btnBuyDelete.setDisable(false);
        }
    }

    @FXML
    void onTotalPriceAction(ActionEvent event) {
        double unitPrice = Double.parseDouble(txtBuyUnitAmount.getText());
        double quantity = Double.parseDouble(txtBuyQuantity.getText());
        double total = unitPrice * quantity;
        lblTotalPrice.setText(String.valueOf(total));
        checkComboBoxSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colBuyMaterialId.setCellValueFactory(new PropertyValueFactory<>("materialId"));
        colBuySupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colBuyDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitAmount"));
        colQuantityBuy.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();
        lblPaymentID.setText(materialBuyDAO.getNextId());
        loadMaterialIds();
        loadSupplierIds();
        lblBuyDate.setText(LocalDate.now().toString());
        cmbMaterialIdBuy.getSelectionModel().clearSelection();
        cmbSupplierIdBuy.getSelectionModel().clearSelection();
        lblMaterialNameBuy.setText("");
        lblSupplierNameBuy.setText("");
        txtBuyUnitAmount.clear();
        txtBuyUnitAmount.clear();
        txtBuyQuantity.clear();
        lblUnitLoadBuy.setText("");
        lblTotalPrice.setText("");
        lblPaymentID.setText(materialBuyDAO.getNextId());
        resetFieldStyles();

        btnBuySave.setDisable(true);
        btnBuyUpdate.setDisable(true);
        btnBuyDelete.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<MaterialBuyDto> buyDTOS = (ArrayList<MaterialBuyDto>) materialBuyDAO.getAll();
        ObservableList<MaterialBuyTM> buyTMS = FXCollections.observableArrayList();
        for (MaterialBuyDto materialBuyDto : buyDTOS){
            MaterialBuyTM materialBuyTM = new MaterialBuyTM(
                    materialBuyDto.getPaymentId(),
                    materialBuyDto.getMaterialId(),
                    materialBuyDto.getSupplierId(),
                    materialBuyDto.getDate(),
                    materialBuyDto.getUnitAmount(),
                    materialBuyDto.getQuantity(),
                    materialBuyDto.getTotalPrice()
            );
            buyTMS.add(materialBuyTM);
        }
        tblMaterialBuy.setItems(buyTMS);
    }

    private void loadMaterialIds() throws SQLException {
        ArrayList<String> materialIds = materialDAO.getAllIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(materialIds);
        cmbMaterialIdBuy.setItems(observableList);
    }

    private void loadSupplierIds() throws SQLException {
        ArrayList<String> supplierIds = supplierDAOImpl.getAllIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(supplierIds);
        cmbSupplierIdBuy.setItems(observableList);
    }

    private void resetFieldStyles(){
        txtBuyUnitAmount.setStyle("-fx-border-color: transparent");
        txtBuyQuantity.setStyle("-fx-border-color: transparent");
    }
}
