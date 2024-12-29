module com.example.cpuscheduler {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.example.cpuscheduler.application;
    opens com.example.cpuscheduler.application to javafx.fxml;
    exports com.example.cpuscheduler.controller;
    opens com.example.cpuscheduler.controller to javafx.fxml;
    exports com.example.cpuscheduler.model;
    opens com.example.cpuscheduler.model to javafx.fxml;
}