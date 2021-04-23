package de.dhbw.boggle.scene_factory;

import de.dhbw.boggle.scenes.Boggle_Scene;
import de.dhbw.boggle.scenes.Main_Menu_Scene;
import de.dhbw.boggle.scenes.Manual_Credits_Scene;
import de.dhbw.boggle.scenes.Welcome_Scene;

import java.util.List;

public class Simple_Scene_Factory extends Scene_Creator{

    @Override
    protected Boggle_Scene createBoggleScene(SCENE sceneName, List<Object> argList) {

        return switch (sceneName) {
            case WELCOME_SCENE -> new Welcome_Scene();
            case MAIN_MENU -> new Main_Menu_Scene();
            case CREDITS_SCENE -> new Manual_Credits_Scene();
            default -> throw new RuntimeException(sceneName + " cannot be created with a simple scene factory!");
        };
    }
}
