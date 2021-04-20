package de.dhbw.boggle.services;

import de.dhbw.boggle.aggregates.Aggregate_Playing_Field;
import de.dhbw.boggle.domain_services.Domain_Service_Duden_Check;
import de.dhbw.boggle.domain_services.Domain_Service_Points_Calculation;
import de.dhbw.boggle.domain_services.Domain_Service_Word_Verification;
import de.dhbw.boggle.entities.Entity_Letter_Salad;
import de.dhbw.boggle.entities.Entity_Player;
import de.dhbw.boggle.entities.Entity_Player_Guess;
import de.dhbw.boggle.repositories.Repository_Player_Guess;
import de.dhbw.boggle.repositories.Repository_Playing_Field;
import de.dhbw.boggle.valueobjects.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Service_Word_Verification implements Domain_Service_Word_Verification, Domain_Service_Points_Calculation {

    private final Repository_Player_Guess playerGuessRepository;
    private final Domain_Service_Duden_Check dudenCheckService;

    Service_Word_Verification(Repository_Player_Guess playerGuessRepository,Domain_Service_Duden_Check dudenCheckService) {
        this.playerGuessRepository = playerGuessRepository;
        this.dudenCheckService = dudenCheckService;
    }

    @Override
    public VO_Points sumUpPoints(Aggregate_Playing_Field playingField) {
       List<Entity_Player_Guess> playerGuesses = playerGuessRepository.getAllCorrectGuessesFromGuessList(
               playerGuessRepository.getAllPlayerGuessesByPlayingFieldId(playingField.getId()));

       int sumPoints = 0;

       for(Entity_Player_Guess playerGuess : playerGuesses) {
           sumPoints += playerGuess.getPoints().getPoints();
       }

       return new VO_Points(sumPoints);
    }

    @Override
    public VO_Points calculatePointsForWord(VO_Word word) {
        switch (word.getWord().length()) {
            case 3:
            case 4:
                return new VO_Points(1);
            case 5:
                return new VO_Points(2);
            case 6:
                return new VO_Points(3);
            case 7:
                return new VO_Points(5);
            case 8:
            default:
                return new VO_Points(11);
        }
    }

    @Override
    public boolean wordIsDuplicateGuess(VO_Word word, Aggregate_Playing_Field playingField) {
        List<Entity_Player_Guess> playerGuesses = playerGuessRepository.getAllPlayerGuessesByPlayingFieldId(playingField.getId());

        for(Entity_Player_Guess playerGuess : playerGuesses) {
            if(playerGuess.getWord().equals(word))
                return true;
        }

        return false;
    }

    @Override
    public void examineAllGuesses(Aggregate_Playing_Field playingField) {
        List<Entity_Player_Guess> playerGuesses = playerGuessRepository.getAllUnexaminedGuessesFromGuessList(
                playerGuessRepository.getAllPlayerGuessesByPlayingFieldId(playingField.getId()));

        for(Entity_Player_Guess playerGuess : playerGuesses) {
            VO_Word guessedWord = playerGuess.getWord();

            if(checkIfWordIsBuildableWithLetterSalad( guessedWord, playingField.getLetterSalad() )) {
                if(dudenCheckService.lookUpWordInDuden(guessedWord)) {
                    playerGuess.setCorrect(calculatePointsForWord( guessedWord ));
                    continue;
                }
            }

            playerGuess.setWrong();
        }
    }

    private boolean checkIfWordIsBuildableWithLetterSalad(VO_Word word, Entity_Letter_Salad letterSalad) {

        String str_word = word.getWord();

        VO_Dice_Side[][] diceSideMatrix = letterSalad.getDiceSideMatrix();
        int matrixSize = letterSalad.getMatrixSize().getSize();

        //Search all possible start positions of the first letter of the word
        List<VO_Matrix_Index_Pair> startingPositions = getAllIndexPairsForALetterInDiceMatrix(diceSideMatrix, matrixSize, str_word.charAt(0));

        if(startingPositions.size() <= 0)
            return false;

        boolean wordIsPossible = false;

        for(VO_Matrix_Index_Pair startPosition : startingPositions) {

            //This array marks the already used letters of the matrix, because a letter may not be used twice
            boolean[][] letterUseMatrix = new boolean[matrixSize][matrixSize];
            Arrays.fill(letterUseMatrix, false);

            //Set first letter to used
            letterUseMatrix[startPosition.getI()][startPosition.getJ()] = true;

            if(checkIfWordIsPossibleFromGivenStartingPoint(str_word.substring(1), startPosition, diceSideMatrix, matrixSize, letterUseMatrix)) {
                wordIsPossible = true;
            }
        };

        return false;
    }

    private List<VO_Matrix_Index_Pair> getAllIndexPairsForALetterInDiceMatrix(VO_Dice_Side[][] diceMatrix, int matrixSize, char searchLetter) {

        List<VO_Matrix_Index_Pair> indexPairList = new ArrayList<VO_Matrix_Index_Pair>();

        for(int i = 0; i < matrixSize; i++) {
            for(int j = 0; j < matrixSize; j++) {
                if(diceMatrix[i][j].getLetter() == searchLetter) {
                    indexPairList.add(new VO_Matrix_Index_Pair(i,j));
                }
            }
        }

        return indexPairList;
    };

    private boolean checkIfWordIsPossibleFromGivenStartingPoint(String remainingWord, VO_Matrix_Index_Pair startingPoint, VO_Dice_Side[][] diceSideMatrix, int matrixSize, boolean[][] letterUseMatrix) {

        //If there are no letters left in the word, the word can be created correctly using the given letter matrix.
        if(remainingWord.length() == 0)
            return true;

        char firstLetter = remainingWord.charAt(0);
        String remainingString = remainingWord.substring(1);

        List<VO_Matrix_Index_Pair> foundPositions = new ArrayList<>();

        //go through all adjacent letters if possible und check if the letters are equal
        for(int dI = -1; dI < 2; dI++) {
            for(int dJ = -1; dJ < 2; dJ++) {

                //continue if index pair is on startingPoint
                if(dI == 0 && dJ == 0) continue;

                int newI = startingPoint.getI() + dI;
                int newJ = startingPoint.getJ() + dJ;

                //continue if index pair out of bound
                if(newI < 0 || newI >= matrixSize || newJ <0 ||newJ >= matrixSize)
                    continue;

                //Add index pair to list if letter is the searched one and this letter was not used before
                //set use flag for this position to true
                if(diceSideMatrix[newI][newJ].getLetter() == firstLetter && !letterUseMatrix[newI][newJ]) {
                    foundPositions.add(new VO_Matrix_Index_Pair(newI, newJ));
                    letterUseMatrix[newI][newJ] = true;
                }
            }
        }

        //Word not possible for this branch, because letter was not found in the adjacent dices
        if(foundPositions.size() == 0)
            return false;

        boolean wordCorrect = false;

        //start from each new starting point and check if the remaining word can be created with the help of a recursive loop
        for(VO_Matrix_Index_Pair newStartPosition : foundPositions) {
            if(checkIfWordIsPossibleFromGivenStartingPoint(remainingString, newStartPosition, diceSideMatrix, matrixSize, letterUseMatrix)){
                wordCorrect = true;
            }
        }

        return wordCorrect;
    }
}
