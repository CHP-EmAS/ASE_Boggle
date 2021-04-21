package de.dhbw.boggle.services;

import de.dhbw.boggle.aggregates.Aggregate_Playing_Field;
import de.dhbw.boggle.domain_services.Domain_Service_Duden_Check;
import de.dhbw.boggle.domain_services.Domain_Service_Game;
import de.dhbw.boggle.domain_services.Domain_Service_Timer;
import de.dhbw.boggle.domain_services.Domain_Service_Word_Verification;
import de.dhbw.boggle.entities.Entity_Player;
import de.dhbw.boggle.entities.Entity_Player_Guess;
import de.dhbw.boggle.repositories.Repository_Player_Guess;
import de.dhbw.boggle.repositories.Repository_Playing_Field;
import de.dhbw.boggle.repository_bridges.Repository_Bridge_Player_Guess;
import de.dhbw.boggle.repository_bridges.Repository_Bridge_Playing_Field;
import de.dhbw.boggle.valueobjects.VO_Field_Size;
import de.dhbw.boggle.valueobjects.VO_Word;

import java.time.Duration;

public class Service_Game implements Domain_Service_Game {

    public enum GAME_STATUS {
        CREATED,
        INITIALIZED,
        RUNNING,
        CANCELLED,
        STOPPED,
        EVALUATED
    }

    private final Domain_Service_Word_Verification wordVerificationService;

    private final Repository_Player_Guess playerGuessRepository;
    private final Repository_Playing_Field playingFieldRepository;

    private GAME_STATUS gameStatus = GAME_STATUS.CREATED;
    private String currentPlayingFieldId;

    public final static java.time.Duration initialGameTime = Duration.ofMinutes(3);
    private final Domain_Service_Timer gameTimer;

    public Service_Game(Domain_Service_Duden_Check dudenCheckService,Domain_Service_Timer gameTimer, Repository_Player_Guess playerGuessRepository) {
        this.playingFieldRepository = new Repository_Bridge_Playing_Field();
        this.playerGuessRepository = playerGuessRepository;

        wordVerificationService = new Service_Word_Verification(this.playerGuessRepository, dudenCheckService);

        this.gameTimer = gameTimer;
    }

    @Override
    public void initGame(Entity_Player player, VO_Field_Size fieldSize) {
        if(gameStatus == GAME_STATUS.RUNNING || gameStatus == GAME_STATUS.INITIALIZED)
            throw new RuntimeException("Game can only be initialized when the game is not running or already is initialized!");

        Service_Dice_Builder diceBuilder = new Service_Dice_Builder();

        Aggregate_Playing_Field newPlayingField = new Aggregate_Playing_Field(
                diceBuilder.buildDiceArray(fieldSize),
                fieldSize,
                player
        );

        this.playingFieldRepository.addPlayingField(newPlayingField);
        this.currentPlayingFieldId = newPlayingField.getId();

        gameStatus = GAME_STATUS.INITIALIZED;
    }

    @Override
    public void startGame() {
        if(gameStatus != GAME_STATUS.INITIALIZED)
            throw new RuntimeException("Game can only be started when the game is initialized!");

        gameStatus = GAME_STATUS.RUNNING;
        gameTimer.startTimer(initialGameTime);
    }

    @Override
    public void cancelGame() {
        if(gameStatus != GAME_STATUS.RUNNING)
            throw new RuntimeException("Game can only be canceled when the game is running!");

        gameStatus = GAME_STATUS.CANCELLED;
        gameTimer.cancelTimer();
    }

    @Override
    public void stopGame() {
        if(gameStatus != GAME_STATUS.RUNNING)
            throw new RuntimeException("Game can only be stopped when the game is running!");

        gameStatus = GAME_STATUS.STOPPED;
        gameTimer.cancelTimer();
    }


    @Override
    public Entity_Player getPlayer() {
        if(gameStatus == GAME_STATUS.CREATED)
            throw new RuntimeException("Game has not been initialized yet");

        return this.getPlayingField().getAssignedPlayer();
    }

    @Override
    public Aggregate_Playing_Field getPlayingField() {
        if(gameStatus == GAME_STATUS.CREATED)
            throw new RuntimeException("Game has not been initialized yet");

        return playingFieldRepository.getPlayingFieldByID(this.currentPlayingFieldId);
    }

    @Override
    public boolean guessWord(VO_Word newWord) {
        if(gameStatus != GAME_STATUS.RUNNING)
            throw new RuntimeException("Guesses are only possible when the game is running!");

        Aggregate_Playing_Field currentPlayingField = this.getPlayingField();

        if(!wordVerificationService.wordIsDuplicateGuess(newWord, currentPlayingField)) {
            playerGuessRepository.addPlayerGuess(new Entity_Player_Guess(newWord, currentPlayingField));
            return true;
        }

        return false;
    }

    @Override
    public void evaluatesAllGuesses() {
        if(gameStatus != GAME_STATUS.STOPPED)
            throw new RuntimeException("Guesses can only be evaluated when the game is stopped!");

        wordVerificationService.examineAllGuesses(this.getPlayingField());
    }

    public GAME_STATUS getGameStatus() {
        return gameStatus;
    }
}
