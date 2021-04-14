package de.dhbw.boggle.repositories;

import de.dhbw.boggle.entities.Entity_Ranking_Entry;

import java.util.List;

public interface Repository_Ranking {
    void addRankingEntry(Entity_Ranking_Entry rankingEntry);

    Entity_Ranking_Entry getRankingEntryByID(String uuid);
    List<Entity_Ranking_Entry> getAllRankingEntries();
}
