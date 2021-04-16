package de.dhbw.boggle;

import de.dhbw.boggle.entities.Entity_Ranking_Entry;

public class Ranking_Entry_Resource_Mapper {
    public Resource_Ranking_Entry map(Entity_Ranking_Entry ranking_entry) {
        return new Resource_Ranking_Entry(ranking_entry.getPlayerName(), ranking_entry.getScoredPoints().getPoints(), ranking_entry.getFieldSize().getSize(), ranking_entry.getDate().getDateSting());
    }
}
