package de.dhbw.boggle.player_guess;

import de.dhbw.boggle.entities.Entity_Player_Guess;
import de.dhbw.boggle.valueobjects.VO_Matrix_Index_Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Mapper_Player_Guess implements Function<Entity_Player_Guess, Player_Guess> {
    private Player_Guess map(Entity_Player_Guess guess) {

        int convertedPoints = 0;
        List<VO_Matrix_Index_Pair> letterList = new ArrayList<>();

        if(guess.getGuessFlag() != Entity_Player_Guess.Guess_Flag.NOT_EXAMINED) {
            letterList = guess.getUsedLetterList();
            convertedPoints = guess.getPoints().getPoints();
        }


        return new Player_Guess(guess.getWord().getWord(), convertedPoints, guess.getGuessFlag(), letterList);
    }

    @Override
    public Player_Guess apply(Entity_Player_Guess entity_player_guess) {
        return map(entity_player_guess);
    }
}
