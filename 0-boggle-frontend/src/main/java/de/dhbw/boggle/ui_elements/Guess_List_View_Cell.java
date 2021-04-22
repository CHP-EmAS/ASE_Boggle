package de.dhbw.boggle.ui_elements;

import de.dhbw.boggle.entities.Entity_Player_Guess;
import de.dhbw.boggle.player_guess.Player_Guess;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Guess_List_View_Cell extends ListCell<Player_Guess> {
    @Override
    protected void updateItem(Player_Guess guess, boolean empty) {
        super.updateItem(guess, empty);

        if(empty || guess == null) {

            setText(null);
            setGraphic(null);

        } else {

            Font font = new Font("Arial",20);
            Label newGuessLabel = new Label(guess.guessedWord);

            newGuessLabel.setStyle("-fx-font-weight: bold");
            newGuessLabel.setFont(font);

            switch (guess.flag) {
                default -> newGuessLabel.setTextFill(Color.BLUEVIOLET);
                case EXAMINED_IMPOSSIBLE -> newGuessLabel.setTextFill(Color.DARKRED);
                case EXAMINED_CORRECT -> newGuessLabel.setTextFill(Color.GREENYELLOW);
                case EXAMINED_WRONG -> newGuessLabel.setTextFill(Color.RED);
            }

            if(guess.flag != Entity_Player_Guess.Guess_Flag.NOT_EXAMINED)
                newGuessLabel.setText(guess.points + " " + guess.guessedWord);
            else
                newGuessLabel.setText(guess.guessedWord);

            setText(null);
            setGraphic(newGuessLabel);
        }

    }
}
