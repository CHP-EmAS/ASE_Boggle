package de.dhbw.boggle.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Main_Menu_Scene extends Boggle_Scene{


    @FXML private Button buttonTest;

    @Override
    public void init() {

    }

    @Override
    public void build() {
        System.out.println("Building Main Menu Scene...");

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Main_Menu_Scene.fxml")));
            super.scene = new Scene(root, Color.web("#37474f"));
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        this.buttonTest.setText("WOW");
    }

    public void changeText(ActionEvent event) {
        this.buttonTest.setText("lol");
    }
}
