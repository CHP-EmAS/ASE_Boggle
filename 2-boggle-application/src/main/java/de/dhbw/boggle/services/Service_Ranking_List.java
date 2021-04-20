package de.dhbw.boggle.services;

import de.dhbw.boggle.domain_services.Domain_Service_Ranking;
import de.dhbw.boggle.entities.Entity_Ranking_Entry;
import de.dhbw.boggle.repositories.Repository_Ranking;

public class Service_Ranking_List implements Domain_Service_Ranking {

    Repository_Ranking rankingRepository;

    Service_Ranking_List() {

    }

    @Override
    public void saveRankingEntry(Entity_Ranking_Entry rankingEntry) {

    }

    @Override
    public Entity_Ranking_Entry[] loadAllRankings() {
        return new Entity_Ranking_Entry[0];
    }
}
