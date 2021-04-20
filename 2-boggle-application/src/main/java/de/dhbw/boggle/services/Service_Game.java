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

    private final Domain_Service_Word_Verification wordVerificationService;

    private final Repository_Player_Guess playerGuessRepository;
    private final Repository_Playing_Field playingFieldRepository;

    private boolean isStarted = false;
    private String currentPlayingFieldId;

    public final static java.time.Duration initialGameTime = Duration.ofMinutes(3);
    private Domain_Service_Timer gameTimer;

    public Service_Game(Domain_Service_Duden_Check dudenCheckService,Domain_Service_Timer gameTimer) {
        this.playingFieldRepository = new Repository_Bridge_Playing_Field();
        this.playerGuessRepository = new Repository_Bridge_Player_Guess();

        wordVerificationService = new Service_Word_Verification(this.playerGuessRepository, dudenCheckService);

        this.gameTimer = gameTimer;
    }

    @Override
    public void initGame(String playerName, VO_Field_Size fieldSize) {

        Service_Dice_Builder diceBuilder = new Service_Dice_Builder();

        Aggregate_Playing_Field newPlayingField = new Aggregate_Playing_Field(
                diceBuilder.buildDiceArray(fieldSize),
                fieldSize,
                new Entity_Player(playerName)
        );

        this.playingFieldRepository.addPlayingField(newPlayingField);
        this.currentPlayingFieldId = newPlayingField.getId();
    }

    @Override
    public void startGame() {
        isStarted = true;
        gameTimer.startTimer(initialGameTime);
    }

    @Override
    public void stopGame() {
        wordVerificationService.examineAllGuesses(this.getPlayingField());

        isStarted = false;
    }

    @Override
    public Entity_Player getPlayer() {
        if(!this.isStarted)
            throw new RuntimeException("Game has not been started yet");

        return this.getPlayingField().getAssignedPlayer();
    }

    @Override
    public Aggregate_Playing_Field getPlayingField() {
        if(!this.isStarted)
            throw new RuntimeException("Game has not been started yet");

        return playingFieldRepository.getPlayingFieldByID(this.currentPlayingFieldId);
    }

    @Override
    public void guessWord(VO_Word newWord) {
        if(!this.isStarted)
            throw new RuntimeException("Game has not been started yet");

        Aggregate_Playing_Field currentPlayingField = this.getPlayingField();

        if(!wordVerificationService.wordIsDuplicateGuess(newWord, currentPlayingField)) {
            playerGuessRepository.addPlayerGuess(
                    new Entity_Player_Guess(
                            newWord,
                            currentPlayingField,
                            currentPlayingField.getAssignedPlayer()
            ));
        }
    }

    public boolean isStarted() {
        return isStarted;
    }
}
