package de.dhbw.boggle.scenes;

import de.dhbw.boggle.entities.Entity_Player;
import de.dhbw.boggle.scene_factory.Scene_Creator;
import de.dhbw.boggle.valueobjects.VO_Field_Size;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.List;

public class Main_Menu_Scene extends Boggle_Scene {

    @FXML private Button boggle4Button;
    @FXML private Button boggle5Button;
    @FXML private Label playerNameLabel;

    private String currentPlayerName = "";

    @Override
    public void init() {

    }

    @Override
    public void build() {
        System.out.println("Building Main Menu Scene...");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main_Menu_Scene.fxml"));
            loader.setController(this);

            AnchorPane root = loader.load();

            super.scene = new Scene(root);
            super.scene.setOnKeyPressed(this::nameInput);

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public void startGame4() {
        Entity_Player newPlayer = new Entity_Player(currentPlayerName);
        VO_Field_Size newFieldSize = new VO_Field_Size((short)4);

        sceneManager.changeScene(Scene_Creator.SCENE.GAME_SCENE, List.of(newPlayer, newFieldSize));
    }

    public void startGame5(){
        Entity_Player newPlayer = new Entity_Player(currentPlayerName);
        VO_Field_Size newFieldSize = new VO_Field_Size((short)5);

        sceneManager.changeScene(Scene_Creator.SCENE.GAME_SCENE, List.of(newPlayer, newFieldSize));
    }

    public void goToRanking(){
        sceneManager.changeScene(Scene_Creator.SCENE.RANKING_LIST_SCENE,null);
    }

    public void goToCredits(){

    }

    private void nameInput(KeyEvent event) {

        if(event.getCode().isLetterKey()) {
            if(currentPlayerName.length() < Entity_Player.maxPlayerNameLength) {


                currentPlayerName = currentPlayerName + (event.isShiftDown() ? event.getText().toUpperCase() : event.getText());
                playerNameLabel.setText(currentPlayerName);

                if(playerNameLabel.isDisabled()) {
                    playerNameLabel.setDisable(false);
                    boggle4Button.setDisable(false);
                    boggle5Button.setDisable(false);
                }
            }
        } else {
            if(event.getCode() == KeyCode.BACK_SPACE) {

                if(currentPlayerName.length() > 0) {

                    currentPlayerName = currentPlayerName.substring(0, currentPlayerName.length() - 1);
                    playerNameLabel.setText(currentPlayerName);

                    if(currentPlayerName.isEmpty()) {
                        playerNameLabel.setText("Tippe deinen Namen ein...");
                        playerNameLabel.setDisable(true);
                        boggle4Button.setDisable(true);
                        boggle5Button.setDisable(true);
                    }
                }

            }
        }
    }
}
