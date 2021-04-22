package de.dhbw.boggle.scene_factory;

import de.dhbw.boggle.manager.Scene_Manager;
import de.dhbw.boggle.scenes.Boggle_Scene;

import java.util.List;

public abstract class Scene_Creator {

    public enum SCENE {
        WELCOME_SCENE,
        MAIN_MENU,
        START_GAME_SCENE,
        GAME_SCENE,
        RANKING_LIST_SCENE,
        CREDITS_SCENE
    }

    public Boggle_Scene getScene(SCENE sceneName, Scene_Manager sceneManager, List<Object> argList) {
        Boggle_Scene newScene = createBoggleScene(sceneName, argList);

        newScene.setSceneManager(sceneManager);

        newScene.init();
        newScene.build();

        return newScene;
    }

    public Boggle_Scene getScene(SCENE sceneName, Scene_Manager sceneManager) {
        Boggle_Scene newScene = createBoggleScene(sceneName, null );

        newScene.setSceneManager(sceneManager);

        newScene.init();
        newScene.build();

        return newScene;
    }

    protected abstract Boggle_Scene createBoggleScene(SCENE sceneName, List<Object> argList);

}
