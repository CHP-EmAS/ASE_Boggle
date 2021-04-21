package de.dhbw.boggle.scene_factory;

import de.dhbw.boggle.scenes.Boggle_Scene;
import de.dhbw.boggle.scenes.Game_Scene;
import de.dhbw.boggle.scenes.Main_Menu_Scene;
import de.dhbw.boggle.scenes.Welcome_Scene;

import java.util.List;

public class Advanced_Scene_Factory extends Scene_Creator{
    @Override
    protected Boggle_Scene createBoggleScene(SCENE sceneName, List<Object> argList) {

        switch(sceneName) {
            case GAME_SCENE:
                return new Game_Scene(argList);
            default:
                throw new RuntimeException(sceneName + " cannot be created with an advanced scene factory!");
        }
    }
}
