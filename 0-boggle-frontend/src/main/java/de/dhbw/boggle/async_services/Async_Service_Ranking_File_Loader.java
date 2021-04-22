package de.dhbw.boggle.async_services;

import de.dhbw.boggle.repositories.Repository_Ranking;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class Async_Service_Ranking_File_Loader extends Service<Boolean> {
    private final Repository_Ranking rankingRepository;

    public Async_Service_Ranking_File_Loader(Repository_Ranking rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    @Override
    protected Task<Boolean> createTask() {
        return new Task<>() {
            @Override
            protected Boolean call() {
                return rankingRepository.loadAllRankingEntries();
            }
        };
    }
}
