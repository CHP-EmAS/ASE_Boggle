package de.dhbw.boggle.repositories;

import de.dhbw.boggle.entities.Entity_Ranking_Entry;
import de.dhbw.boggle.value_objects.VO_Date;
import de.dhbw.boggle.value_objects.VO_Field_Size;
import de.dhbw.boggle.value_objects.VO_Points;

import java.util.List;

public interface Repository_Ranking {

    Entity_Ranking_Entry addRankingEntry(String playerName, VO_Points scored_points, VO_Field_Size fieldSize, VO_Date date);
    void addRankingEntry(Entity_Ranking_Entry rankedEntry);

    Entity_Ranking_Entry getRankingEntryByID(String uuid);
    List<Entity_Ranking_Entry> getRankingByFieldSize(VO_Field_Size fieldSize);

    boolean loadAllRankingEntries();
}
