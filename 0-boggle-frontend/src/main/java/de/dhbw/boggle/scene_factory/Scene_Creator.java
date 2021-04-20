package de.dhbw.boggle.scene_factory;

import de.dhbw.boggle.Scene_Manager;
import de.dhbw.boggle.scenes.Boggle_Scene;

public abstract class Scene_Creator {

    public enum SCENE {
        WELCOME_SCENE,
        MAIN_MENU,
        START_GAME_SCENE,
        GAME_SCENE,
        RANKING_LIST_SCENE,
        CREDITS_SCENE
    }

    public Boggle_Scene getScene(SCENE sceneName, Scene_Manager sceneManager) {
        Boggle_Scene newScene = createBoggleScene(sceneName);

        newScene.setSceneManager(sceneManager);

        newScene.init();
        newScene.build();

        return newScene;
    }

    protected abstract Boggle_Scene createBoggleScene(SCENE sceneName);

}
