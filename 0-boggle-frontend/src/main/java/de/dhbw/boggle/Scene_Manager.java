package de.dhbw.boggle;

import de.dhbw.boggle.scene_factory.Advanced_Scene_Factory;
import de.dhbw.boggle.scene_factory.Scene_Creator;
import de.dhbw.boggle.scene_factory.Simple_Scene_Factory;
import de.dhbw.boggle.scenes.Boggle_Scene;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.List;

public class Scene_Manager extends Application {

    private final Scene_Creator simpleSceneFactory = new Simple_Scene_Factory();
    private final Scene_Creator advancedSceneFactory = new Advanced_Scene_Factory();

    private Boggle_Scene currentScene = simpleSceneFactory.getScene(Scene_Creator.SCENE.WELCOME_SCENE, this);

    private Stage mainStage;

    @Override
    public void start(Stage stage) {
        mainStage = stage;

        Image icon = new Image("/smallIcon.png");
        mainStage.getIcons().add(icon);

        mainStage.setTitle("Boggle");
        mainStage.setResizable(false);

        mainStage.setScene(currentScene.getScene());
        mainStage.show();
    }

    public void changeScene(Scene_Creator.SCENE newScene) {
        currentScene = simpleSceneFactory.getScene(newScene, this);
        mainStage.setScene(currentScene.getScene());
    }

    public void changeScene(Scene_Creator.SCENE newScene, List<Object> argList) {
        currentScene = advancedSceneFactory.getScene(newScene, this, argList);
        mainStage.setScene(currentScene.getScene());
    }
}
