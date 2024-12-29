package com.example.cpuscheduler.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    private Stage stage;

    public static String currentStage;

    @FXML
    void handleFCFS(ActionEvent event) throws IOException{
        // thuật toán lựa chọn là FCFS
        currentStage = "FCFS";
        openStageSimulation("FCFS", event);
    }

    @FXML
    void handleSJN(ActionEvent event) throws IOException {
        // thuật toán lựa chọn là SJN
        currentStage = "SJN";
        openStageSimulation("SJN", event);
    }

    @FXML
    void handleRoundRobin(ActionEvent event) throws IOException {
        // thuật toán lựa chọn là RoundRobin
        currentStage = "Round Robin";
        openStageSimulation("Round Robin", event);
    }

    @FXML
    void handleHelp(){
        // giải thích thuật toán bằng alert information
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Giải thích thuật toán");
        alert.setHeaderText(null);
        alert.setResizable(false);
        alert.getDialogPane().setPrefSize(500, 350);
        alert.setContentText("""
                FCFS(First Come First Serve): thực hiện các tiến trình theo thời gian xuất hiện trong CPU, tức là
                tiến trình nào có arrival time nhỏ nhất sẽ được thực hiện trước.
                
                SJN(Short Job Next): mỗi khi có 1 tiến trình mới xuất hiện trong hệ thống, tiến trình hiện tại được
                đưa lại vào ready queue, sau đó tiến trình có burst time nhỏ nhất sẽ được lựa chọn để thực thi, khi
                có nhiều tiến trình có cùng burst time nhỏ nhất, tiến trình có độ ưu tiên cao hơn được lựa chọn.
                
                RR(Round Robin): mỗi tiến trình được cấp một khoảng thời gian cố định quantum time, các tiến trình
                xuất hiện trong hệ thống được xếp vào cuối hàng đợi, khi 1 tiến trình hết quantum time mà chưa
                hoàn thành, nó được xếp vào cuối hàng đợi.
                """);
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.YES){
                System.out.println("yes");
            }else{
                System.out.println("no");
            }
        });
    }

    public void openStageSimulation(String title, ActionEvent event) throws IOException {
        // Đóng Stage main
        Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Mở Stage simulation
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/cpuscheduler/SimulationView.fxml"));
        newStage.setScene(new Scene(root));
        newStage.setTitle(title + " simulation");
        newStage.setResizable(false);
        newStage.show();
        newStage.setOnCloseRequest(e->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Thoát chương trình?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Exit Confirmation");
            alert.setHeaderText(null);

            if (alert.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) {
                e.consume();
            }
        });
    }

    private void setupCloseConfirmation(Stage stage) throws IOException {
        // hộp thoại xác nhận đóng chương trình
        stage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Thoát chương trình?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Exit Confirmation");
            alert.setHeaderText(null);

            if (alert.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) {
                event.consume();
            }
        });
    }

    public void closeStage(Stage stage) throws IOException {
        // truyền method cho application
        this.stage = stage;
        setupCloseConfirmation(stage);
    }

}
