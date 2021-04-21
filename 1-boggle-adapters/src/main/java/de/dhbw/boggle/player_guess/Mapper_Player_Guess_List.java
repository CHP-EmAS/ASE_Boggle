package de.dhbw.boggle.player_guess;

import de.dhbw.boggle.entities.Entity_Player_Guess;
import de.dhbw.boggle.valueobjects.VO_Matrix_Index_Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Mapper_Player_Guess_List implements Function<List<Entity_Player_Guess>, List<Player_Guess>> {

    private List<Player_Guess> map(List<Entity_Player_Guess> playerGuessList) {
        Mapper_Player_Guess guessMapper = new Mapper_Player_Guess();

        return playerGuessList.stream()
                .filter(Objects::nonNull)
                .map(guess -> guessMapper.apply(guess))
                .collect(Collectors.toList());
    }

    @Override
    public List<Player_Guess> apply(List<Entity_Player_Guess> playerGuessList) {
        return map(playerGuessList);
    }
}
