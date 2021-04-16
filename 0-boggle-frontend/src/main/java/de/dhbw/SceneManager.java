package de.dhbw;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SceneManager extends Application {
    @Override
    public void start(Stage stage) {

        Group root = new Group();

        Label boggleLabel = new Label("Boggle");

        Button startGameButton = new Button("Spiel starten");
        Button showRankingListButton = new Button("Zur Rangliste");
        Button showCreditsButton = new Button("Credits");

        //root.getChildren().addAll(boggleLabel,startGameButton,showRankingListButton,showCreditsButton);

        Scene welcomeScene = new Scene(root, Color.web("#37474f"));

        Image icon = new Image("file:./images/smallIcon.png");
        stage.getIcons().add(icon);

        stage.setTitle("Boggle");
        stage.setScene(welcomeScene);
        stage.show();
    }
}
