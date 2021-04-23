package de.dhbw.boggle.scenes;

import de.dhbw.boggle.scene_factory.Scene_Creator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class Manual_Credits_Scene extends Boggle_Scene {

    @Override
    public void init() {
    }

    @Override
    public void build() {
        System.out.println("Building Manual & Credits Scene...");

        try {
            AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Manual_Credits_Scene.fxml")));
            super.scene = new Scene(root);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backToMainMenu() {
        sceneManager.changeScene(Scene_Creator.SCENE.MAIN_MENU);
    }
}
