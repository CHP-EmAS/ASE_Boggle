package de.dhbw.boggle.domain_services;

import de.dhbw.boggle.entities.Entity_Ranking_Entry;

public interface Domain_Service_Ranking_Controller {

    void saveRankingEntry(Entity_Ranking_Entry rankingEntry);

    Entity_Ranking_Entry[] loadAllRankings();
}
