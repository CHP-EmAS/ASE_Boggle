package de.dhbw.boggle.player_guess;

import de.dhbw.boggle.entities.Entity_Player_Guess;
import de.dhbw.boggle.valueobjects.VO_Matrix_Index_Pair;

import java.util.List;

public class Player_Guess {
    public final String guessedWord;
    public final int points;
    public final Entity_Player_Guess.Guess_Flag flag;
    public final List<VO_Matrix_Index_Pair> usedLetters;

    Player_Guess(String guessedWord, int points, Entity_Player_Guess.Guess_Flag flag, List<VO_Matrix_Index_Pair> usedLetters) {
        this.guessedWord = guessedWord;
        this.points = points;
        this.flag = flag;
        this.usedLetters = usedLetters;
    }
}
