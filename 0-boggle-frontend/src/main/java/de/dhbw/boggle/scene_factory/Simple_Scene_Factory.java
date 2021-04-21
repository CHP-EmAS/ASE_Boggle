package de.dhbw.boggle.scene_factory;

import de.dhbw.boggle.scenes.Boggle_Scene;
import de.dhbw.boggle.scenes.Game_Scene;
import de.dhbw.boggle.scenes.Main_Menu_Scene;
import de.dhbw.boggle.scenes.Welcome_Scene;
import de.dhbw.boggle.valueobjects.VO_Field_Size;

import java.util.List;

public class Simple_Scene_Factory extends Scene_Creator{

    @Override
    protected Boggle_Scene createBoggleScene(SCENE sceneName, List<Object> argList) {

        switch(sceneName) {
            case WELCOME_SCENE:
                return new Welcome_Scene();
            case MAIN_MENU:
                return new Main_Menu_Scene();
            case CREDITS_SCENE:
                return null;
            default:
                throw new RuntimeException(sceneName + " cannot be created with a simple scene factory!");
        }
    }
}
