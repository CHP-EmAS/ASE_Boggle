package de.dhbw.boggle.repositories;

import de.dhbw.boggle.entities.Entity_Player_Guess;

import java.util.List;

public interface Repository_Player_Guess {

    void addPlayerGuess(Entity_Player_Guess playerGuess);

    Entity_Player_Guess getPlayerGuessById(long id);

    List<Entity_Player_Guess> getAllPlayerGuessesByPlayingFieldId(String playingFieldId);

    List<Entity_Player_Guess> getAllUnexaminedGuessesFromGuessList(List<Entity_Player_Guess> guessList);

    List<Entity_Player_Guess> getAllExaminedGuessesFromGuessList(List<Entity_Player_Guess> guessList);

    List<Entity_Player_Guess> getAllWrongGuessesFromGuessList(List<Entity_Player_Guess> guessList);

    List<Entity_Player_Guess> getAllCorrectGuessesFromGuessList(List<Entity_Player_Guess> guessList);

    List<Entity_Player_Guess> getAllGuesses();

}
