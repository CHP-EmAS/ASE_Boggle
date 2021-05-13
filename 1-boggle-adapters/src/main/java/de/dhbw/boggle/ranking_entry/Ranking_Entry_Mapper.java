package de.dhbw.boggle.ranking_entry;

import de.dhbw.boggle.entities.Entity_Ranking_Entry;

import java.util.function.Function;

public class Ranking_Entry_Mapper implements Function<Entity_Ranking_Entry, Ranking_Entry> {
    private Ranking_Entry map(Entity_Ranking_Entry ranking_entry) {
        return new Ranking_Entry(ranking_entry.getPlayerName(), ranking_entry.getScoredPoints().getPoints(), ranking_entry.getFieldSize().getSize(), ranking_entry.getDate().getDateSting());
    }

    @Override
    public Ranking_Entry apply(Entity_Ranking_Entry ranking_entry) {
        return map(ranking_entry);
    }
}
