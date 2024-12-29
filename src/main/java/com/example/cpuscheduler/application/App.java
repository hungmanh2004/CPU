package com.example.cpuscheduler.application;

import com.example.cpuscheduler.controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // load mainmenu view
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cpuscheduler/MainMenuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainMenuController controller = fxmlLoader.getController();
        controller.closeStage(stage);
        stage.setResizable(false);
        stage.setTitle("CPUScheduler");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
