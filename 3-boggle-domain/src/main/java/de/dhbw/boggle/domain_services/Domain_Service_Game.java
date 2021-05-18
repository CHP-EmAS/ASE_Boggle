package de.dhbw.boggle.domain_services;

import de.dhbw.boggle.aggregates.Aggregate_Playing_Field;
import de.dhbw.boggle.entities.Entity_Player;
import de.dhbw.boggle.value_objects.VO_Field_Size;
import de.dhbw.boggle.value_objects.VO_Points;
import de.dhbw.boggle.value_objects.VO_Word;

public interface Domain_Service_Game {

    enum GAME_STATUS {
        CREATED,
        INITIALIZED,
        RUNNING,
        CANCELLED,
        STOPPED,
        EVALUATED
    }

    void initGame(Entity_Player player, VO_Field_Size fieldSize);

    void startGame();
    void cancelGame();
    void stopGame();

    Entity_Player getPlayer();
    Aggregate_Playing_Field getPlayingField();

    VO_Points getTotalScore();
    GAME_STATUS getGameStatus();

    boolean guessWord(VO_Word newWord);
    void evaluateAllGuesses();
}
