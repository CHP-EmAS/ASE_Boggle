package de.dhbw.boggle.repository_bridges;

import de.dhbw.boggle.domain_services.Domain_Service_Duden_Check;
import de.dhbw.boggle.entities.Entity_Player_Guess;
import de.dhbw.boggle.repositories.Repository_Player_Guess;
import de.dhbw.boggle.valueobjects.VO_Word;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Repository_Bridge_Player_Guess implements Repository_Player_Guess {
    private final List<Entity_Player_Guess> playerGuesses = new ArrayList<Entity_Player_Guess>();

    @Override
    public void addPlayerGuess(Entity_Player_Guess playerGuess) {
        playerGuesses.add(playerGuess);
    }

    @Override
    public Entity_Player_Guess getPlayerGuessById(long id) {
        return playerGuesses.stream()
                .filter(guess -> guess.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Entity_Player_Guess> getAllPlayerGuessesByPlayingFieldId(String playingFieldId) {
        return playerGuesses.stream()
                .filter(guess -> guess.getAssignedPlayingField().getId().equals(playingFieldId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Entity_Player_Guess> getAllPlayerGuessesByPlayerId(String playerId) {
        return playerGuesses.stream()
                .filter(guess -> guess.getAssignedPlayer().getId().equals(playerId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Entity_Player_Guess> getAllUnexaminedGuessesFromGuessList(List<Entity_Player_Guess> guessList) {
        return guessList.stream()
                .filter(guess -> guess.getGuessFlag() == Entity_Player_Guess.Guess_Flag.NOT_EXAMINED)
                .collect(Collectors.toList());
    }

    @Override
    public List<Entity_Player_Guess> getAllExaminedGuessesFromGuessList(List<Entity_Player_Guess> guessList) {
        return guessList.stream()
                .filter(guess -> guess.getGuessFlag() != Entity_Player_Guess.Guess_Flag.NOT_EXAMINED)
                .collect(Collectors.toList());
    }

    @Override
    public List<Entity_Player_Guess> getAllWrongGuessesFromGuessList(List<Entity_Player_Guess> guessList) {
        return guessList.stream()
                .filter(guess -> guess.getGuessFlag() == Entity_Player_Guess.Guess_Flag.EXAMINED_WRONG)
                .collect(Collectors.toList());
    }

    @Override
    public List<Entity_Player_Guess> getAllCorrectGuessesFromGuessList(List<Entity_Player_Guess> guessList) {
        return guessList.stream()
                .filter(guess -> guess.getGuessFlag() == Entity_Player_Guess.Guess_Flag.EXAMINED_CORRECT)
                .collect(Collectors.toList());
    }

    @Override
    public List<Entity_Player_Guess> getAllGuesses() {
        return playerGuesses;
    }
}
