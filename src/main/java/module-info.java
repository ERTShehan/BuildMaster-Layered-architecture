module com.assignment.buildmasternew {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.sql;
    requires static lombok;
    requires java.mail;
    requires net.sf.jasperreports.core;

    opens com.assignment.buildmasternew.dto to javafx.base;
    opens com.assignment.buildmasternew.controller to javafx.fxml;
    exports com.assignment.buildmasternew;
    opens com.assignment.buildmasternew.dto.tm to javafx.base;
}