package de.dhbw.boggle.scenes;

import de.dhbw.boggle.manager.Scene_Manager;
import javafx.scene.Scene;

public abstract class Boggle_Scene {
    protected static Scene_Manager sceneManager;
    protected Scene scene;

    public void setSceneManager(Scene_Manager sceneManager) {
        if(Boggle_Scene.sceneManager == null)
            Boggle_Scene.sceneManager = sceneManager;
    }

    abstract public void init();
    abstract public void build();

    public javafx.scene.Scene getScene() {
        return this.scene;
    }
}
