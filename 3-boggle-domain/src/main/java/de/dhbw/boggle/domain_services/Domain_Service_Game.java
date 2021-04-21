package de.dhbw.boggle.domain_services;

import de.dhbw.boggle.aggregates.Aggregate_Playing_Field;
import de.dhbw.boggle.entities.Entity_Player;
import de.dhbw.boggle.repositories.Repository_Player_Guess;
import de.dhbw.boggle.valueobjects.VO_Field_Size;
import de.dhbw.boggle.valueobjects.VO_Word;

public interface Domain_Service_Game {

    void initGame(Entity_Player player, VO_Field_Size fieldSize);

    void startGame();
    void cancelGame();
    void stopGame();

    Entity_Player getPlayer();
    Aggregate_Playing_Field getPlayingField();

    boolean guessWord(VO_Word newWord);
    void evaluatesAllGuesses();
}
