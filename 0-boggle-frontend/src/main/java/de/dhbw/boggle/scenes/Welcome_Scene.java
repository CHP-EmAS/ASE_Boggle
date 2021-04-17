package de.dhbw.boggle.scenes;

import de.dhbw.boggle.DWDS_Digital_Dictionary_API;
import de.dhbw.boggle.scene_factory.Scene_Creator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Welcome_Scene extends Boggle_Scene {


    @FXML private Label apiCheckLabel;
    @FXML private Button playWithoutPoints;

    @Override
    public void init() {
        return;
    }

    @Override
    public void build() {

        System.out.println("Building Welcome Scene...");

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Welcome_Scene.fxml")));
            super.scene = new Scene(root, Color.web("#37474f"));
        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }

    @FXML
    public void initialize() throws InterruptedException {

        DWDS_Digital_Dictionary_API api = new DWDS_Digital_Dictionary_API();

        if(api.checkIfDudenServiceIsAvailable()) {
            apiCheckLabel.setText("Online Wörterbuch steht zur Verfügung!");
            playWithoutPoints.setText("Spielen!");
        } else {
            apiCheckLabel.setText("Online Wörterbuch nicht erreichbar :(!");
        }

        playWithoutPoints.setVisible(true);
        playWithoutPoints.setDisable(false);

    }

    public void goToMenu(ActionEvent event) {
        System.out.println("goToMenu");
        super.sceneManager.changeScene(Scene_Creator.SCENE.MAIN_MENU);
    }
}
