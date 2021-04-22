package de.dhbw.boggle.ranking_entry;

import de.dhbw.boggle.entities.Entity_Player_Guess;
import de.dhbw.boggle.entities.Entity_Ranking_Entry;
import de.dhbw.boggle.player_guess.Mapper_Player_Guess;
import de.dhbw.boggle.player_guess.Player_Guess;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Mapper_Ranking_Entry_List implements Function<List<Entity_Ranking_Entry>, List<Ranking_Entry>> {

    private List<Ranking_Entry> map(List<Entity_Ranking_Entry> rankingEntryList)

    {
        Ranking_Entry_Mapper entryMapper = new Ranking_Entry_Mapper();

        return rankingEntryList.stream()
                .filter(Objects::nonNull)
                .map(entryMapper)
                .sorted(Comparator.comparingInt(Ranking_Entry::getPoints).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Ranking_Entry> apply(List<Entity_Ranking_Entry> playerGuessList) {
        return map(playerGuessList);
    }

}
