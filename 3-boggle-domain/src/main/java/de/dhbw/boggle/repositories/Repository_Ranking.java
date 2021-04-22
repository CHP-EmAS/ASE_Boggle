package de.dhbw.boggle.repositories;

import de.dhbw.boggle.entities.Entity_Ranking_Entry;
import de.dhbw.boggle.valueobjects.VO_Field_Size;

import java.util.List;

public interface Repository_Ranking {
    void addRankingEntry(Entity_Ranking_Entry rankingEntry);

    Entity_Ranking_Entry getRankingEntryByID(String uuid);
    List<Entity_Ranking_Entry> getRankingByFieldSize(VO_Field_Size fieldSize);

    boolean loadAllRankingEntries();
}
