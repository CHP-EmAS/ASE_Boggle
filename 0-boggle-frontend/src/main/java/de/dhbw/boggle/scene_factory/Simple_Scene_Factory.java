package de.dhbw.boggle.scene_factory;

import de.dhbw.boggle.Scene_Manager;
import de.dhbw.boggle.scenes.Boggle_Scene;
import de.dhbw.boggle.scenes.Main_Menu_Scene;
import de.dhbw.boggle.scenes.Welcome_Scene;

public class Simple_Scene_Factory extends Scene_Creator{

    @Override
    protected Boggle_Scene createBoggleScene(SCENE sceneName) {

        switch(sceneName) {
            case WELCOME_SCENE:
            default:
                return new Welcome_Scene();
            case MAIN_MENU:
                return new Main_Menu_Scene();
            case START_GAME_SCENE:
            case GAME_SCENE:
            case RANKING_LIST_SCENE:
            case CREDITS_SCENE:
                return null;
        }
    }
}
