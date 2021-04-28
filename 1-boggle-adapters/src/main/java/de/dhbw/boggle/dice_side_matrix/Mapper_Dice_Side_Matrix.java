package de.dhbw.boggle.dice_side_matrix;

import de.dhbw.boggle.aggregates.Aggregate_Playing_Field;
import de.dhbw.boggle.value_objects.VO_Dice_Side;

import java.util.function.Function;

public class Mapper_Dice_Side_Matrix implements Function<Aggregate_Playing_Field, String[][]> {

    private String[][] map(Aggregate_Playing_Field playingField) {
        short matrixSize = playingField.getPlayingFieldSize().getSize();
        VO_Dice_Side[][] diceSideMatrix = playingField.getLetterSalad().getDiceSideMatrix();

        String[][] stringMatrix = new String[matrixSize][matrixSize];

        for(int i = 0; i < matrixSize; i++) {
            for(int j = 0; j < matrixSize; j++) {
                stringMatrix[i][j] = Character.toString(diceSideMatrix[i][j].getLetter());
            }
        }

        return stringMatrix;
    }

    @Override
    public String[][] apply(Aggregate_Playing_Field playingField) {
        return map(playingField);
    }
}
