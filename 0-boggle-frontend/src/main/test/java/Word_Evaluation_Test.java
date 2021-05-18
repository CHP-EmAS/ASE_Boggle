import de.dhbw.boggle.aggregates.Aggregate_Playing_Field;
import de.dhbw.boggle.domain_services.Domain_Service_Word_Verification;
import de.dhbw.boggle.entities.Entity_Letter_Salad;
import de.dhbw.boggle.entities.Entity_Player;
import de.dhbw.boggle.entities.Entity_Player_Guess;
import de.dhbw.boggle.repositories.Repository_Player_Guess;
import de.dhbw.boggle.repository_bridges.Repository_Bridge_Player_Guess;
import de.dhbw.boggle.services.Service_Dice_Builder;
import de.dhbw.boggle.services.Service_Player_Guess_Verification;
import de.dhbw.boggle.value_objects.VO_Field_Size;
import de.dhbw.boggle.value_objects.VO_Word;
import mocks.API_Mock;
import org.junit.jupiter.api.*;

import java.util.List;

public class Word_Evaluation_Test {
    private Domain_Service_Word_Verification wordVerification;
    private Repository_Player_Guess playerGuessRepository;
    private Aggregate_Playing_Field playingField;

    @BeforeEach
    public void setUp() {
        //set fixed seed
        Entity_Letter_Salad.seed = 123456789;

        Service_Dice_Builder diceBuilder = new Service_Dice_Builder();
        VO_Field_Size fieldSize = new VO_Field_Size((short) 4);

        playingField = new Aggregate_Playing_Field(diceBuilder.buildDiceArray(fieldSize),fieldSize,new Entity_Player("Test"));
    }

    @Test
    @DisplayName("Checking fixed letter salad with initialized word")
    public void testInitializedWordEvaluation() {
        playerGuessRepository = new Repository_Bridge_Player_Guess();
        wordVerification = new Service_Player_Guess_Verification(playerGuessRepository, new API_Mock());

        Entity_Player_Guess playerGuess = playerGuessRepository.addPlayerGuess(new VO_Word("LIED"), this.playingField);

        Assertions.assertTrue(playerGuess.getGuessFlag() == Entity_Player_Guess.Guess_Flag.NOT_EXAMINED, "Examined Guesses should has wrong Flag. Flag should be NOT_EXAMINED! Flag was: " + playerGuess.getGuessFlag());
    }

    @Test
    @DisplayName("Checking fixed letter salad with correct word")
    public void testCorrectWordEvaluation() {
        playerGuessRepository = new Repository_Bridge_Player_Guess();
        wordVerification = new Service_Player_Guess_Verification(playerGuessRepository, new API_Mock());

        Entity_Player_Guess playerGuess = playerGuessRepository.addPlayerGuess(new VO_Word("LIED"), this.playingField);
        wordVerification.examineAllGuesses(this.playingField);

        Assertions.assertTrue(playerGuess.getGuessFlag() == Entity_Player_Guess.Guess_Flag.EXAMINED_CORRECT, "Examined Guesses should has wrong Flag. Flag should be EXAMINED_CORRECT! Flag was: " + playerGuess.getGuessFlag());
    }

    @Test
    @DisplayName("Checking fixed letter salad with wrong word")
    public void testWrongWordEvaluation() {
        playerGuessRepository = new Repository_Bridge_Player_Guess();
        wordVerification = new Service_Player_Guess_Verification(playerGuessRepository, new API_Mock());

        Entity_Player_Guess playerGuess =  playerGuessRepository.addPlayerGuess(new VO_Word("DECO"), this.playingField);
        wordVerification.examineAllGuesses(this.playingField);

        Assertions.assertTrue(playerGuess.getGuessFlag() == Entity_Player_Guess.Guess_Flag.EXAMINED_WRONG, "Examined Guesses should has wrong Flag. Flag should be EXAMINED_WRONG! Flag was: " + playerGuess.getGuessFlag());
    }

    @Test
    @DisplayName("Checking fixed letter salad with impossible word")
    public void testImpossibleWordEvaluation() {
        playerGuessRepository = new Repository_Bridge_Player_Guess();
        wordVerification = new Service_Player_Guess_Verification(playerGuessRepository, new API_Mock());

        Entity_Player_Guess playerGuess = playerGuessRepository.addPlayerGuess(new VO_Word("IMPOSSIBLE"), this.playingField);
        wordVerification.examineAllGuesses(this.playingField);

        Assertions.assertTrue(playerGuess.getGuessFlag() == Entity_Player_Guess.Guess_Flag.EXAMINED_IMPOSSIBLE, "Examined Guesses should has wrong Flag. Flag should be EXAMINED_IMPOSSIBLE! Flag was: " + playerGuess.getGuessFlag());
    }
}
