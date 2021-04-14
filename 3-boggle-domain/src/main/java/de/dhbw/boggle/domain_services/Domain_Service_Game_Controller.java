package de.dhbw.boggle.domain_services;

import de.dhbw.boggle.aggregates.Aggregate_Playing_Field;
import de.dhbw.boggle.entities.Entity_Player;
import de.dhbw.boggle.valueobjects.VO_Field_Size;
import de.dhbw.boggle.entities.Entity_Player_Guess;

public interface Domain_Service_Game_Controller {

    boolean startGame(String playerName, VO_Field_Size fieldSize);
    void stopGame();

    Entity_Player getPlayer();
    Aggregate_Playing_Field getPlayingField();

    void addPlayerGuess(Entity_Player_Guess playerGuess);
    void examineAllPlayerGuesses();
}
