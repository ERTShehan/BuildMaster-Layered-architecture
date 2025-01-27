module com.assignment.buildmaster {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.sql;
    requires static lombok;
    requires java.mail;
    requires net.sf.jasperreports.core;

    opens com.assignment.buildmaster.dto to javafx.base;
    opens com.assignment.buildmaster.controller to javafx.fxml;
    exports com.assignment.buildmaster;
    opens com.assignment.buildmaster.view.tdm to javafx.base;
}