package de.dhbw.boggle;

import de.dhbw.boggle.aggregates.Aggregate_Playing_Field;
import de.dhbw.boggle.valueobjects.VO_Dice_Side;

public class Resource_Mapper_Dice_Side_Matrix {

    public String[][] map(Aggregate_Playing_Field playingField) {
        short matrixSize = playingField.getPlayingFieldSize().getSize();
        VO_Dice_Side diceSideMatrix[][] = playingField.getLetterSalad().getDiceSideMatrix();

        String stringMatrix[][] = new String[matrixSize][matrixSize];

        for(int i = 0; i < matrixSize; i++) {
            for(int j = 0; j < matrixSize; j++) {
                stringMatrix[i][j] = Character.toString(diceSideMatrix[i][j].getLetter());
            }
        }

        return stringMatrix;
    }

}
