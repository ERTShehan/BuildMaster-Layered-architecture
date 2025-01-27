package com.assignment.buildmaster.controller;

import com.assignment.buildmaster.db.DBConnection;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ReportController {

    @FXML
    private JFXButton btnTotalCost;

    @FXML
    private JFXButton btnClientDetails;

    @FXML
    void onTotalCostReportAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("P_Date", LocalDate.now().toString());

//            Map<String, Object> parameters = new HashMap<>();
//            parameters.put(JRTextElement.MARKUP_KEY, JRTextElement.MARKUP_HTML);
//            JasperFillManager.fillReport(report, parameters);

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/AllExpensesReport.jrxml"));

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );
            JasperViewer.viewReport(jasperPrint, false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onClientDetailsReportAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("P_Date", LocalDate.now().toString());

//            Map<String, Object> parameters = new HashMap<>();
//            parameters.put(JRTextElement.MARKUP_KEY, JRTextElement.MARKUP_HTML);
//            JasperFillManager.fillReport(report, parameters);

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/ClientReport.jrxml"));

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );
            JasperViewer.viewReport(jasperPrint, false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

}
