package de.dhbw.boggle.scenes;

import de.dhbw.boggle.async_services.Async_Service_Word_Evaluation;
import de.dhbw.boggle.async_services.Async_Service_Countdown;
import de.dhbw.boggle.API_DWDS_Digital_Dictionary;
import de.dhbw.boggle.entities.Entity_Ranking_Entry;
import de.dhbw.boggle.ui_elements.Guess_List_View_Cell;
import de.dhbw.boggle.dice_side_matrix.Mapper_Dice_Side_Matrix;
import de.dhbw.boggle.entities.Entity_Player_Guess;
import de.dhbw.boggle.player_guess.Mapper_Player_Guess_List;
import de.dhbw.boggle.player_guess.Player_Guess;
import de.dhbw.boggle.scene_factory.Scene_Creator;
import de.dhbw.boggle.time.Mapper_Time;
import de.dhbw.boggle.entities.Entity_Player;
import de.dhbw.boggle.repositories.Repository_Player_Guess;
import de.dhbw.boggle.repository_bridges.Repository_Bridge_Player_Guess;
import de.dhbw.boggle.services.Service_Game;
import de.dhbw.boggle.valueobjects.VO_Date;
import de.dhbw.boggle.valueobjects.VO_Field_Size;
import de.dhbw.boggle.valueobjects.VO_Matrix_Index_Pair;
import de.dhbw.boggle.valueobjects.VO_Word;
import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Game_Scene extends Advanced_Boggle_Scene {

    private final Service_Game gameService;
    private final VO_Field_Size gridSize;

    private final Entity_Player player;
    private final Repository_Player_Guess playerGuessRepository;

    private final Service<Void> wordEvaluation;

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

        this.player = (Entity_Player) argList.get(0);
        this.gridSize = (VO_Field_Size) argList.get(1);

        //Countdown Service for Game
        //sets every tick the current time to the progressbar and the time label
        //triggers on cancel (if time is over or game is canceled) the game over function
        Async_Service_Countdown gameCountdown = new Async_Service_Countdown();
        gameCountdown.setOnSucceeded(workerStateEvent -> updateTime(gameCountdown.getValue()));
        gameCountdown.setOnCancelled(workerStateEvent -> gameOver());

        playerGuessRepository = new Repository_Bridge_Player_Guess();

        gameService = new Service_Game(new API_DWDS_Digital_Dictionary(), gameCountdown, this.playerGuessRepository);
        letterLabelsGrid = new Label[this.gridSize.getSize()][this.gridSize.getSize()];

        this.wordEvaluation = new Async_Service_Word_Evaluation(this.gameService);
        wordEvaluation.setOnSucceeded(workerStateEvent -> wordsEvaluated());
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

            super.scene = new Scene(root);
            super.scene.setOnKeyPressed(this::wordInput);

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void validateArgList(List<Object> argList) {
        if(argList.size() < 2)
            throw new RuntimeException("When creating the game scene, 2 parameters (player name, game field size) must be passed in argList!");

        if(!argList.get(0).getClass().equals(Entity_Player.class))
            throw new RuntimeException("Argument 0 must be an instance of Entity_Player");

        if(!argList.get(1).getClass().equals(VO_Field_Size.class))
            throw new RuntimeException("Argument 1 must be an instance of VO_Field_Size");
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
                newGridLabel.setTextFill(Color.web("#37474f"));

                letterLabelsGrid[i][j] = newGridLabel;

                Pane background = new Pane();
                background.setStyle("-fx-border-color: #597380");
                background.getChildren().add(letterLabelsGrid[i][j]);

                mainGridPane.add(background,i,j);
            }
        }

        guessListView.setCellFactory(guessListViewCell -> new Guess_List_View_Cell());
        guessListView.getSelectionModel().selectedItemProperty().addListener((observableValue, player_guess, t1) ->
                guessListItemSelected(guessListView.getSelectionModel().getSelectedItem())
        );
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

            eventButton.setVisible(false);
            eventButton.setDisable(true);

            timerProgress.setVisible(true);
            timerProgress.setDisable(false);

            timerProgress.setProgress(-1);

            wordEvaluation.start();

        } else if(gameService.getGameStatus() == Service_Game.GAME_STATUS.EVALUATED) {

            LocalDateTime now = LocalDateTime.now();
            VO_Date currentDate = new VO_Date(now.getDayOfMonth(), now.getMonth().getValue(), now.getYear());

            Entity_Ranking_Entry newRankingEntry = new Entity_Ranking_Entry(player.getName(), gameService.getTotalScore(), this.gridSize, currentDate);

            List<Object> argList = new ArrayList<>();
            argList.add(newRankingEntry);

            sceneManager.changeScene(Scene_Creator.SCENE.RANKING_LIST_SCENE, argList);
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

    public void wordsEvaluated() {
        if(gameService.getGameStatus() != Service_Game.GAME_STATUS.EVALUATED)
            return;

        updateGuessesListView();

        int totalScore = gameService.getTotalScore().getPoints();

        String str_points = Integer.toString(totalScore);

        if(totalScore != 1) {
            str_points += " Punkte";
        } else {
            str_points += " Punkt";
        }

        textInputLabel.setText("Du hast " + str_points + " erreicht!");

        timerProgress.setVisible(false);
        timerProgress.setDisable(true);

        eventButton.setText("Meine Punktzahl zur Rangliste hinzufügen <3!");
        eventButton.setVisible(true);
        eventButton.setDisable(false);
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

    private void guessListItemSelected(Player_Guess player_guess) {
        String backgroundColor = "#FFA1A1"; //light red (pink)

        if(player_guess.flag == Entity_Player_Guess.Guess_Flag.EXAMINED_CORRECT)
            backgroundColor = "#D7FF91"; //light green

        for(int i = 0; i < this.gridSize.getSize(); i++) {
            for (int j = 0; j < this.gridSize.getSize(); j++) {

                Parent gridCell = letterLabelsGrid[i][j].getParent();

                if(player_guess.flag != Entity_Player_Guess.Guess_Flag.EXAMINED_IMPOSSIBLE) {
                    VO_Matrix_Index_Pair gridCoordinates = new VO_Matrix_Index_Pair(i,j);

                    if(player_guess.usedLetters.contains(gridCoordinates))
                        gridCell.setStyle("-fx-background-color:" + backgroundColor + "; -fx-border-color: #597380;");
                    else
                        gridCell.setStyle("-fx-border-color: #597380;");

                } else {
                    gridCell.setStyle("-fx-background-color:FF5447" + "; -fx-border-color: #597380;");
                }
            }
        }
    }
}
