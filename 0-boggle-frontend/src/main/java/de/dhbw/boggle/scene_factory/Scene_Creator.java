package de.dhbw.boggle.scene_factory;

import de.dhbw.boggle.manager.Scene_Manager;
import de.dhbw.boggle.scenes.Boggle_Scene;

import java.util.List;

public abstract class Scene_Creator {

    public enum SCENE {
        WELCOME_SCENE,
        MAIN_MENU,
        GAME_SCENE,
        RANKING_LIST_SCENE,
        CREDITS_SCENE
    }

    public Boggle_Scene getScene(SCENE sceneName, Scene_Manager sceneManager, List<Object> argList) {
        Boggle_Scene newScene = createBoggleScene(sceneName, argList);
        return finalizeScene(newScene, sceneManager);
    }

    public Boggle_Scene getScene(SCENE sceneName, Scene_Manager sceneManager) {
        Boggle_Scene newScene = createBoggleScene(sceneName, null );
        return finalizeScene(newScene, sceneManager);
    }

    protected abstract Boggle_Scene createBoggleScene(SCENE sceneName, List<Object> argList);

    private Boggle_Scene finalizeScene(Boggle_Scene scene, Scene_Manager sceneManager) {
        scene.setSceneManager(sceneManager);

        scene.init();
        scene.build();

        return scene;
    }
}
