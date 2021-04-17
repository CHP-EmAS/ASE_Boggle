package de.dhbw.boggle;

import de.dhbw.boggle.scene_factory.Scene_Creator;
import de.dhbw.boggle.scene_factory.Simple_Scene_Factory;
import de.dhbw.boggle.scenes.Boggle_Scene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Scene_Manager extends Application {

    private Scene_Creator sceneFactory = new Simple_Scene_Factory();

    private Boggle_Scene currentScene = sceneFactory.getScene(Scene_Creator.SCENE.WELCOME_SCENE, this);

    private Stage mainStage;

    @Override
    public void start(Stage stage) {
        mainStage = stage;

        Image icon = new Image("/smallIcon.png");
        mainStage.getIcons().add(icon);

        mainStage.setTitle("Boggle");
        mainStage.setScene(currentScene.getScene());
        mainStage.show();
    }

    public void changeScene(Scene_Creator.SCENE newScene) {
        currentScene = sceneFactory.getScene(newScene, this);
        mainStage.setScene(currentScene.getScene());
    }
}
