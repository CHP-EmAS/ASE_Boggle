package de.dhbw.boggle.scene_factory;

import de.dhbw.boggle.scenes.Advanced_Boggle_Scene;
import de.dhbw.boggle.scenes.Boggle_Scene;
import de.dhbw.boggle.scenes.Game_Scene;
import de.dhbw.boggle.scenes.Ranking_Scene;

import java.util.List;

public class Advanced_Scene_Factory extends Scene_Creator{
    @Override
    protected Boggle_Scene createBoggleScene(SCENE sceneName, List<Object> argList) {

        Advanced_Boggle_Scene newScene = switch (sceneName) {
            case GAME_SCENE -> new Game_Scene(argList);
            case RANKING_LIST_SCENE -> new Ranking_Scene(argList);
            default -> throw new RuntimeException(sceneName + " cannot be created with an advanced scene factory!");
        };

        newScene.validateArgList(argList);
        return newScene;
    }
}
