package de.dhbw.boggle.ui_elements;

import de.dhbw.boggle.ranking_entry.Ranking_Entry;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.text.Font;

public class Ranking_List_View_Cell extends ListCell<Ranking_Entry> {
    @Override
    protected void updateItem(Ranking_Entry entry, boolean empty) {
        super.updateItem(entry, empty);

        if(empty || entry == null) {

            setText(null);
            setGraphic(null);

        } else {

            Font font = new Font("Arial",20);
            Label newGuessLabel = new Label( entry.playerName + ": " + entry.points + " Punkte, am " + entry.dateString);

            newGuessLabel.setStyle("-fx-font-weight: bold");
            newGuessLabel.setFont(font);

            setText(null);
            setGraphic(newGuessLabel);

        }

    }
}
