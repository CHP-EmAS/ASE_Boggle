package de.dhbw.boggle.scenes;

import de.dhbw.boggle.CountdownService;
import de.dhbw.boggle.API_DWDS_Digital_Dictionary;
import de.dhbw.boggle.Guess_List_View_Cell;
import de.dhbw.boggle.dice_side_matrix.Mapper_Dice_Side_Matrix;
import de.dhbw.boggle.player_guess.Mapper_Player_Guess_List;
import de.dhbw.boggle.player_guess.Player_Guess;
import de.dhbw.boggle.time.Mapper_Time;
import de.dhbw.boggle.entities.Entity_Player;
import de.dhbw.boggle.repositories.Repository_Player_Guess;
import de.dhbw.boggle.repository_bridges.Repository_Bridge_Player_Guess;
import de.dhbw.boggle.services.Service_Game;
import de.dhbw.boggle.valueobjects.VO_Field_Size;
import de.dhbw.boggle.valueobjects.VO_Word;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.List;

public class Game_Scene extends Boggle_Scene {

    private final Service_Game gameService;
    private final VO_Field_Size gridSize;

    private final Entity_Player player;
    private final Repository_Player_Guess playerGuessRepository;

    @FXML private Button eventButton;
    @FXML private Label textInputLabel;
    @FXML private Label timerLabel;
    @FXML private ProgressBar timerProgress;
    @FXML private Pane gamePane;
    @FXML private ListView<Player_Guess> guessListView;

    private String currentGuessInput = "";

    private GridPane mainGridPane;
    private final Label[][] letterLabelsGrid;

    public Game_Scene(List<Object> argList) {

        validateArgList(argList);

        this.player = (Entity_Player) argList.get(0);
        this.gridSize = (VO_Field_Size) argList.get(1);

        CountdownService countdownService = new CountdownService();

        countdownService.setOnSucceeded(workerStateEvent -> {
            updateTime(countdownService.getValue());
        });

        countdownService.setOnCancelled(workerStateEvent -> {
            gameOver();
        });

        playerGuessRepository = new Repository_Bridge_Player_Guess();

        gameService = new Service_Game(new API_DWDS_Digital_Dictionary(), countdownService, this.playerGuessRepository);
        letterLabelsGrid = new Label[this.gridSize.getSize()][this.gridSize.getSize()];
    }

    @Override
    public void init() {
        gameService.initGame(player, gridSize);
        mainGridPane = new GridPane();
    }

    @Override
    public void build() {
        System.out.println("Building Game Scene " + gridSize.getSize() + "x" + gridSize.getSize() + " ...");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Game_Scene.fxml"));

            loader.setController(this);
            AnchorPane root = loader.load();

            gamePane.getChildren().add(mainGridPane);

            super.scene = new Scene(root, Color.web("#37474f"));

            super.scene.setOnKeyPressed(this::wordInput);

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {

        mainGridPane.setLayoutX(0);
        mainGridPane.setLayoutY(0);

        mainGridPane.setGridLinesVisible(true);

        Font font = new Font("Arial",50);
        int elementSize = 320 / this.gridSize.getSize();

        for(int i = 0; i < this.gridSize.getSize(); i++) {

            for (int j = 0; j < this.gridSize.getSize(); j++) {
                Label newGridLabel = new Label("?");

                newGridLabel.setMaxSize(elementSize,elementSize);
                newGridLabel.setPrefSize(elementSize,elementSize);
                newGridLabel.setAlignment(Pos.CENTER);

                newGridLabel.setFont(font);

                letterLabelsGrid[i][j] = newGridLabel;
                mainGridPane.add(letterLabelsGrid[i][j],i,j);
            }
        }

        guessListView.setCellFactory(guessListViewCell -> new Guess_List_View_Cell());
    }

    @FXML
    public void eventButtonClicked() {
        if(gameService.getGameStatus() == Service_Game.GAME_STATUS.INITIALIZED) {

            eventButton.setVisible(false);
            eventButton.setDisable(true);

            timerLabel.setVisible(true);
            timerLabel.setDisable(false);
            timerProgress.setVisible(true);
            timerProgress.setDisable(false);

            this.startGame();
        } else if(gameService.getGameStatus() == Service_Game.GAME_STATUS.STOPPED) {
            gameService.evaluatesAllGuesses();
            updateGuessesListView();
        }
    }

    private void startGame() {
        if(gameService.getGameStatus() != Service_Game.GAME_STATUS.INITIALIZED)
            return;

        gameService.startGame();

        Mapper_Dice_Side_Matrix diceMapper = new Mapper_Dice_Side_Matrix();
        String[][] dices = diceMapper.apply(gameService.getPlayingField());

        for(int i = 0; i < gridSize.getSize(); i++) {
            for(int j = 0; j < gridSize.getSize(); j++) {
                letterLabelsGrid[i][j].setText(dices[i][j]);
            }
        }
    }

    public void gameOver() {
        if(gameService.getGameStatus() != Service_Game.GAME_STATUS.RUNNING)
            return;

        eventButton.setText("Auswerten!");
        eventButton.setVisible(true);
        eventButton.setDisable(false);

        timerLabel.setVisible(false);
        timerLabel.setDisable(true);
        timerProgress.setVisible(false);
        timerProgress.setDisable(true);

        gameService.stopGame();
    }

    private void wordInput(KeyEvent event) {

        if(gameService.getGameStatus() != Service_Game.GAME_STATUS.RUNNING)
            return;

        if(event.getCode().isLetterKey()) {
            if(currentGuessInput.length() < (gridSize.getSize() * gridSize.getSize())) {
                currentGuessInput = currentGuessInput + event.getText().toUpperCase();
                textInputLabel.setText(currentGuessInput);
            }
        } else {
            switch(event.getCode()) {
                case ENTER:
                    if(currentGuessInput.length() >= 3) {
                        if(gameService.guessWord(new VO_Word(currentGuessInput))) {
                            updateGuessesListView();
                        }

                        currentGuessInput = "";
                        textInputLabel.setText(currentGuessInput);
                    }
                    break;
                case BACK_SPACE:
                    if(currentGuessInput.length() > 0) {
                        currentGuessInput = currentGuessInput.substring(0, currentGuessInput.length() - 1);
                        textInputLabel.setText(currentGuessInput);
                    }
                    break;
            }
        }
    }

    private void updateGuessesListView() {

        Mapper_Player_Guess_List guessListMapper = new Mapper_Player_Guess_List();

        List<Player_Guess> guessList = guessListMapper.apply(
                playerGuessRepository.getAllPlayerGuessesByPlayingFieldId(gameService.getPlayingField().getId())
        );

        guessListView.getItems().clear();
        guessListView.getItems().addAll(guessList);
    }

    private void updateTime(Duration time) {
        timerLabel.setText(
                Mapper_Time.durationToTimeString(time)
        );

        double currentMillis = time.toMillis();
        double maxMillis = Service_Game.initialGameTime.toMillis();

        timerProgress.setProgress(currentMillis / maxMillis);
    }

    private void validateArgList(List<Object> argList) {
        if(argList.size() < 2)
            throw new RuntimeException("When creating the game scene, 2 parameters (player name, game field size) must be passed in argList!");

        if(!argList.get(0).getClass().equals(Entity_Player.class))
            throw new RuntimeException("Argument 0 must be an instance of Entity_Player");

        if(!argList.get(1).getClass().equals(VO_Field_Size.class))
            throw new RuntimeException("Argument 1 must be an instance of VO_Field_Size");
    }
}
