
import de.dhbw.boggle.domain_services.Domain_Service_Dictionary_Check;
import de.dhbw.boggle.domain_services.Domain_Service_Game;
import de.dhbw.boggle.domain_services.Domain_Service_Timer;
import de.dhbw.boggle.entities.Entity_Letter_Salad;
import de.dhbw.boggle.entities.Entity_Player;
import de.dhbw.boggle.entities.Entity_Player_Guess;
import de.dhbw.boggle.repositories.Repository_Player_Guess;
import de.dhbw.boggle.repository_bridges.Repository_Bridge_Player_Guess;
import de.dhbw.boggle.services.Service_Game;
import de.dhbw.boggle.value_objects.VO_Field_Size;

import de.dhbw.boggle.value_objects.VO_Word;
import mocks.API_Mock;
import mocks.Game_Timer_Mock;
import org.junit.jupiter.api.*;

import java.util.List;

public class Game_Test {
    private Domain_Service_Dictionary_Check dictionaryCheck;
    private Domain_Service_Timer gameTimer;
    private Entity_Player testPlayer;
    private VO_Field_Size initialPlayingFieldSize;

    @BeforeEach
    public void setUp() {
        //set fixed seed
        Entity_Letter_Salad.seed = 123456789;

        dictionaryCheck = new API_Mock();
        gameTimer  = new Game_Timer_Mock();
        testPlayer = new Entity_Player("Test");
        initialPlayingFieldSize = new VO_Field_Size((short) 4);
    }

    @Test
    @DisplayName("Creating Game...")
    public void checkCreationStatus() {
        Repository_Player_Guess playerGuessRepository = new Repository_Bridge_Player_Guess();
        Domain_Service_Game gameService = new Service_Game(dictionaryCheck,gameTimer,playerGuessRepository);

        Assertions.assertTrue(gameService.getGameStatus() == Domain_Service_Game.GAME_STATUS.CREATED, "Wrong GAME_STATUS when creating a game");
    }

    @Test
    @DisplayName("Initialize Game...")
    public void initGameTest() {
        //init game
        Repository_Player_Guess playerGuessRepository = new Repository_Bridge_Player_Guess();
        Domain_Service_Game gameService = new Service_Game(dictionaryCheck,gameTimer,playerGuessRepository);
        gameService.initGame(testPlayer, initialPlayingFieldSize);

        //Check if Status correct
        Assertions.assertTrue(gameService.getGameStatus() == Domain_Service_Game.GAME_STATUS.INITIALIZED, "Wrong GAME_STATUS when initialize a game");

        //Check if player stays the same
        Assertions.assertTrue(gameService.getPlayer().equals(testPlayer), "Player is not the passed player when initializing a game");

        //Check if field size stays the same
        VO_Field_Size playingFieldSize = gameService.getPlayingField().getPlayingFieldSize();
        Assertions.assertTrue(playingFieldSize.equals(initialPlayingFieldSize), "Created playing field size is not the passed size when initializing a game");
    }

    @Test
    @DisplayName("Starting Game...")
    public void startGameTest() {
        //init game
        Repository_Player_Guess playerGuessRepository = new Repository_Bridge_Player_Guess();
        Domain_Service_Game gameService = new Service_Game(dictionaryCheck,gameTimer,playerGuessRepository);

        gameService.initGame(testPlayer, initialPlayingFieldSize);

        //start game
        gameService.startGame();

        Assertions.assertTrue(gameService.getGameStatus() == Domain_Service_Game.GAME_STATUS.RUNNING, "Wrong GAME_STATUS when start a game");
        Assertions.assertTrue(playerGuessRepository.getAllGuesses().size() == 0, "Player guess repository should be empty");
    }

    @Test
    @DisplayName("Guessing Words...")
    public void guessWordsTest() {
        //init & start game
        Repository_Player_Guess playerGuessRepository = new Repository_Bridge_Player_Guess();
        Domain_Service_Game gameService = new Service_Game(dictionaryCheck,gameTimer,playerGuessRepository);

        gameService.initGame(testPlayer, initialPlayingFieldSize);
        gameService.startGame();

        //guess word
        gameService.guessWord(new VO_Word("TEST"));
        Assertions.assertTrue(playerGuessRepository.getAllGuesses().size() == 1, "Player guess repository should have the size of 1");

        //guess another word
        gameService.guessWord(new VO_Word("CORRECT"));
        Assertions.assertTrue(playerGuessRepository.getAllGuesses().size() == 2, "Player guess repository should have the size of 2");

        //guess duplicate word
        gameService.guessWord(new VO_Word("CORRECT"));
        Assertions.assertTrue(playerGuessRepository.getAllGuesses().size() == 2, "Player guess repository should have the size of 2");

        //check if their status is unexamined
        List<Entity_Player_Guess> guessList = playerGuessRepository.getAllGuesses();
        Assertions.assertTrue(playerGuessRepository.getAllUnexaminedGuessesFromGuessList(guessList).size() == 2, "Unexamined Guesses should have the size of 2");
    }

    @Test
    @DisplayName("Stopping Game...")
    public void stopGameTest() {
        //init & start game
        Repository_Player_Guess playerGuessRepository = new Repository_Bridge_Player_Guess();
        Domain_Service_Game gameService = new Service_Game(dictionaryCheck,gameTimer,playerGuessRepository);

        gameService.initGame(testPlayer, initialPlayingFieldSize);
        gameService.startGame();

        gameService.stopGame();
        Assertions.assertTrue(gameService.getGameStatus() == Domain_Service_Game.GAME_STATUS.STOPPED, "Wrong GAME_STATUS when stopping a Game");
    }

    @Test
    @DisplayName("Canceling Game...")
    public void cancelGameTest() {
        //init & start game
        Repository_Player_Guess playerGuessRepository = new Repository_Bridge_Player_Guess();
        Domain_Service_Game gameService = new Service_Game(dictionaryCheck,gameTimer,playerGuessRepository);

        gameService.initGame(testPlayer, initialPlayingFieldSize);
        gameService.startGame();

        gameService.cancelGame();
        Assertions.assertTrue(gameService.getGameStatus() == Domain_Service_Game.GAME_STATUS.CANCELLED, "Wrong GAME_STATUS when canceling a Game");
    }

    @Test
    @DisplayName("Evaluating Guesses...")
    public void evaluateGuessesTest() {
        //init & start & guess & stop game
        Repository_Player_Guess playerGuessRepository = new Repository_Bridge_Player_Guess();
        Domain_Service_Game gameService = new Service_Game(dictionaryCheck,gameTimer,playerGuessRepository);

        gameService.initGame(testPlayer, initialPlayingFieldSize);

        gameService.startGame();
        gameService.guessWord(new VO_Word("TEST"));
        gameService.stopGame();

        //evaluate guesses
        gameService.evaluateAllGuesses();
        Assertions.assertTrue(gameService.getGameStatus() == Domain_Service_Game.GAME_STATUS.EVALUATED, "Wrong GAME_STATUS when evaluating a Game");

        //check if guess status are not unexamined
        List<Entity_Player_Guess> guessList = playerGuessRepository.getAllGuesses();

        //should be size of one
        int examinedGuessListSize = playerGuessRepository.getAllExaminedGuessesFromGuessList(guessList).size();
        Assertions.assertTrue(examinedGuessListSize == 1, "Examined Guesses should have the size of 1 after evaluation! Size was: " + examinedGuessListSize);
    }

    @Test
    @DisplayName("Calculating total score...")
    public void totalScoreTest() {
        //init & start & guess & stop game
        Repository_Player_Guess playerGuessRepository = new Repository_Bridge_Player_Guess();
        Domain_Service_Game gameService = new Service_Game(dictionaryCheck,gameTimer,playerGuessRepository);

        gameService.initGame(testPlayer, initialPlayingFieldSize);

        gameService.startGame();
        gameService.guessWord(new VO_Word("LIED")); //correct points: 1
        gameService.guessWord(new VO_Word("LIED")); //correct but duplicate points: 0
        gameService.guessWord(new VO_Word("DECO")); //wrong points: 0
        gameService.guessWord(new VO_Word("IMPOSSIBLE")); //impossible points: 0
        gameService.stopGame();

        gameService.evaluateAllGuesses();

        //should be size of one
        Assertions.assertTrue(gameService.getTotalScore().getPoints() == 1, "Total score should be 1 after evaluation! Size was: " + gameService.getTotalScore().getPoints());
    }
}
