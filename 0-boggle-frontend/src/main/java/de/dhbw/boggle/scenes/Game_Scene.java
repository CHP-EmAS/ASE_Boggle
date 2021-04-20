package de.dhbw.boggle.scenes;

import de.dhbw.boggle.CountdownService;
import de.dhbw.boggle.DWDS_Digital_Dictionary_API;
import de.dhbw.boggle.Resource_Mapper_Dice_Side_Matrix;
import de.dhbw.boggle.Resource_Mapper_Time;
import de.dhbw.boggle.services.Service_Game;
import de.dhbw.boggle.valueobjects.VO_Field_Size;
import de.dhbw.boggle.valueobjects.VO_Word;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Game_Scene extends Boggle_Scene {

    private final Service_Game gameService;
    private final VO_Field_Size gridSize;

    @FXML private Button eventButton;
    @FXML private Label textInputLabel;
    @FXML private Label timerLabel;
    @FXML private ProgressBar timerProgress;
    @FXML private Pane gamePane;

    private String currentInput = "";

    private GridPane mainGridPane;
    private Label[][] letterLabelsGrid;

    public Game_Scene(VO_Field_Size gridSize) {

        CountdownService countdownService = new CountdownService();

        countdownService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                Duration currentCountdownTime = countdownService.getValue();

                timerLabel.setText(
                        Resource_Mapper_Time.durationToTimeString(currentCountdownTime)
                );

                double currentMillis = currentCountdownTime.toMillis();
                double maxMillis = Service_Game.initialGameTime.toMillis();

                timerProgress.setProgress(currentMillis / maxMillis);
            }
        });


        gameService = new Service_Game(new DWDS_Digital_Dictionary_API(), countdownService);
        this.gridSize = gridSize;

        letterLabelsGrid = new Label[this.gridSize.getSize()][this.gridSize.getSize()];
    }

    @Override
    public void init() {
        gameService.initGame("Test", gridSize);

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

            super.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    wordInput(keyEvent);
                }
            });

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
    }

    @FXML
    public void eventButtonClicked(ActionEvent event) {
        if(!this.gameService.isStarted()) {

            eventButton.setVisible(false);
            eventButton.setDisable(true);

            timerLabel.setVisible(true);
            timerLabel.setDisable(false);
            timerProgress.setVisible(true);
            timerProgress.setDisable(false);

            this.startGame();
        } else {

        }
    }

    private void startGame() {
        gameService.startGame();

        Resource_Mapper_Dice_Side_Matrix diceMapper = new Resource_Mapper_Dice_Side_Matrix();
        String dices[][] = diceMapper.map(gameService.getPlayingField());

        for(int i = 0; i < gridSize.getSize(); i++) {
            for(int j = 0; j < gridSize.getSize(); j++) {
                letterLabelsGrid[i][j].setText(dices[i][j]);;
            }
        }
    }

    public void gameOver() {
        System.out.println("Game Over");
        eventButton.setDisable(false);
        eventButton.setVisible(true);
    }

    private void wordInput(KeyEvent event) {

        if(!gameService.isStarted())
            return;

        if(event.getCode().isLetterKey()) {
            if(currentInput.length() < (gridSize.getSize() * gridSize.getSize())) {
                currentInput = currentInput + event.getText().toUpperCase();
                textInputLabel.setText(currentInput);
            }
        } else {
            switch(event.getCode()) {
                case ENTER:
                    if(currentInput.length() >= 3) {
                        gameService.guessWord(new VO_Word(currentInput));

                        currentInput = "";
                        textInputLabel.setText(currentInput);
                    }
                    break;
                case BACK_SPACE:
                    if(currentInput.length() > 0) {
                        currentInput = currentInput.substring(0, currentInput.length() - 1);
                        textInputLabel.setText(currentInput);
                    }
                    break;
            }
        }
    }

    public void  setTime(int timeInSeconds) {
        Integer minutes = timeInSeconds / 60;
        Integer seconds = timeInSeconds % 60;

        timerLabel.setText(minutes + ":" + seconds);
    }
}
