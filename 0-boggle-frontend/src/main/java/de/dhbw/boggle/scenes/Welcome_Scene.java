package de.dhbw.boggle.scenes;

import de.dhbw.boggle.API_DWDS_Digital_Dictionary;
import de.dhbw.boggle.scene_factory.Scene_Creator;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Welcome_Scene extends Boggle_Scene {

    @FXML private Label apiCheckLabel;
    @FXML private Button playWithoutPoints;

    @Override
    public void init() {

    }

    @Override
    public void build() {

        System.out.println("Building Welcome Scene...");

        try {
            AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Welcome_Scene.fxml")));
            super.scene = new Scene(root, Color.web("#37474f"));
        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }

    @FXML
    public void initialize() {

        API_DWDS_Digital_Dictionary api = new API_DWDS_Digital_Dictionary();

        Service<Boolean> apiAvailabilityCheck = new Service<>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<>() {
                    @Override
                    protected Boolean call() throws Exception {
                        Thread.sleep(1000);
                        return api.dudenServiceIsAvailable();
                    }
                };
            }
        };

        apiAvailabilityCheck.start();

        apiAvailabilityCheck.setOnSucceeded(workerStateEvent -> {
            if(apiAvailabilityCheck.getValue()) {
                apiCheckLabel.setText("Online Wörterbuch steht zur Verfügung!");
                playWithoutPoints.setText("Spielen!");
            } else {
                apiCheckLabel.setText("Online Wörterbuch nicht erreichbar :(!");
            }

            playWithoutPoints.setVisible(true);
            playWithoutPoints.setDisable(false);
        });
    }

    public void goToMenu() {
        System.out.println("goToMenu");
        sceneManager.changeScene(Scene_Creator.SCENE.MAIN_MENU);
    }
}
