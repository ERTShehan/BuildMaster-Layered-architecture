package com.assignment.buildmaster.controller;

import com.assignment.buildmaster.bo.BOFactory;
import com.assignment.buildmaster.bo.custom.ClientBO;
import com.assignment.buildmaster.bo.custom.impl.ClientBOImpl;
import com.assignment.buildmaster.dto.ClientDto;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {
    ClientBO clientBO = (ClientBO) BOFactory.getInstance().getBO(BOFactory.BOType.CLIENT);

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSendEmail;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnClientReset;

    @FXML
    private JFXComboBox<String> cmbClientId;

    @FXML
    private JFXButton btnClientRegister;

    @FXML
    private JFXTextField txtClientAddress;

    @FXML
    private JFXTextField txtClientEmail;

    @FXML
    private Label lblClientID;

    @FXML
    private JFXTextField txtClientName;

    @FXML
    private JFXTextField txtClientPhone;

    @FXML
    void onClientResetAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void onRegisterClient(ActionEvent event) throws SQLException {
        String clientID = lblClientID.getText();
        String clientName = txtClientName.getText();
        String clientAddress = txtClientAddress.getText();
        String clientPhone = txtClientPhone.getText();
        String clientEmail = txtClientEmail.getText();

        String namePattern = "^[A-Za-z ]+$";
        String addressPattern = "^(?![a-zA-Z0-9]$)(?![\\d]$)(?![^a-zA-Z0-9\\s]*$)([a-zA-Z0-9]+(?:[\\W_]+[a-zA-Z0-9]+)*|[\\W_]+(?:[a-zA-Z0-9]+[\\W_]+)*[a-zA-Z0-9]+)$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        boolean isValidName = clientName.matches(namePattern);
        boolean isValidAddress = clientAddress.matches(addressPattern);
        boolean isValidPhone = clientPhone.matches(phonePattern);
        boolean isValidEmail = clientEmail.matches(emailPattern);

        txtClientName.setStyle(txtClientName.getStyle() + ";-fx-border-color: #7367F0;");
        txtClientAddress.setStyle(txtClientAddress.getStyle() + ";-fx-border-color: #7367F0;");
        txtClientPhone.setStyle(txtClientPhone.getStyle() + ";-fx-border-color: #7367F0;");
        txtClientEmail.setStyle(txtClientEmail.getStyle() + ";-fx-border-color: #7367F0;");

        if (!isValidName) {
            txtClientName.setStyle(txtClientName.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidAddress) {
            txtClientAddress.setStyle(txtClientAddress.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtClientPhone.setStyle(txtClientPhone.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidEmail) {
            txtClientEmail.setStyle(txtClientEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidAddress && isValidPhone && isValidEmail){
            ClientDto clientDto = new ClientDto(clientID, clientName, clientAddress, clientPhone, clientEmail);

            boolean isRegister = clientBO.saveClient(clientDto);

            if (isRegister) {
                new Alert(Alert.AlertType.INFORMATION, "Client saved...!").show();
                refreshPage();
            
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Client...!").show();
            }
        }
    }

    private void refreshPage() throws SQLException {
        String nextClientID = clientBO.getNextClientId();
        lblClientID.setText(nextClientID);

        loadClientIds();

        cmbClientId.getSelectionModel().clearSelection();
        txtClientName.clear();
        txtClientAddress.clear();
        txtClientPhone.clear();
        txtClientEmail.clear();

        lblClientID.setText(nextClientID);

        resetFieldStyles();

        btnClientRegister.setDisable(false);
        btnSendEmail.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadClientIds() throws SQLException {
        ArrayList<String> clientIds = clientBO.getAllClientIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(clientIds);
        cmbClientId.setItems(observableList);
    }

    @FXML
    private void cmbClientOnAction(ActionEvent event) throws SQLException {
        String selectedClientId = cmbClientId.getSelectionModel().getSelectedItem();
        if (selectedClientId != null) {
            ClientDto clientDto = clientBO.findByClientId(selectedClientId);

            if (clientDto != null) {
                lblClientID.setText(clientDto.getId());
                txtClientName.setText(clientDto.getName());
                txtClientAddress.setText(clientDto.getAddress());
                txtClientPhone.setText(clientDto.getPhoneNo());
                txtClientEmail.setText(clientDto.getEmail());

                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
                btnSendEmail.setDisable(false);

                btnClientRegister.setDisable(true);
            }
        }
    }


    @FXML
    void onDeleteClient(ActionEvent event) {
        try {
            String clientID = lblClientID.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this client?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();


            if(buttonType.get() == ButtonType.YES){
                boolean isDeleted = clientBO.deleteClient(clientID);

                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Client deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete client.").show();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while deleting client.").show();
        }
    }

    @FXML
    void onLoadClientTable(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AllClientsTable.fxml"));
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
    public void onSendEmail(ActionEvent event) {
        String selectedItem = cmbClientId.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "Please select Client..!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SendMailView.fxml"));
            Parent root = loader.load();

            SendMailViewController sendMailController = loader.getController();
            sendMailController.setRecipientEmail(txtClientEmail.getText());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Send Mail view.").show();
        }
    }

    @FXML
    void onUpdateClient(ActionEvent event) {
        String clientID = lblClientID.getText();
        String clientName = txtClientName.getText();
        String clientAddress = txtClientAddress.getText();
        String clientPhone = txtClientPhone.getText();
        String clientEmail = txtClientEmail.getText();

        String namePattern = "^[A-Za-z ]+$";
        String addressPattern = "^[A-Za-z0-9 ,.-]+$";
        String phonePattern = "^\\d{10}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        boolean isValidName = clientName.matches(namePattern);
        boolean isValidAddress = clientAddress.matches(addressPattern);
        boolean isValidPhone = clientPhone.matches(phonePattern);
        boolean isValidEmail = clientEmail.matches(emailPattern);

        resetFieldStyles();

        if (!isValidName) {
            txtClientName.setStyle("-fx-border-color: red;");
        }
        if (!isValidAddress) {
            txtClientAddress.setStyle("-fx-border-color: red;");
        }
        if (!isValidPhone) {
            txtClientPhone.setStyle("-fx-border-color: red;");
        }
        if (!isValidEmail) {
            txtClientEmail.setStyle("-fx-border-color: red;");
        }

        if (isValidName && isValidAddress && isValidPhone && isValidEmail) {
            try {
                ClientDto clientDto = new ClientDto(clientID, clientName, clientAddress, clientPhone, clientEmail);
                boolean isUpdated = clientBO.updateClient(clientDto);

                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Client updated successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update client.").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while updating client.").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fix the highlighted fields.").show();
        }
    }

    private void resetFieldStyles() {
        txtClientName.setStyle("-fx-border-color: transparent;");
        txtClientAddress.setStyle("-fx-border-color: transparent;");
        txtClientPhone.setStyle("-fx-border-color: transparent;");
        txtClientEmail.setStyle("-fx-border-color: transparent;");
    }

}
