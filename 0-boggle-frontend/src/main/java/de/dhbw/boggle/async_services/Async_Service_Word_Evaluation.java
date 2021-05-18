package de.dhbw.boggle.async_services;

import de.dhbw.boggle.domain_services.Domain_Service_Game;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class Async_Service_Word_Evaluation extends Service<Void> {

    private final Domain_Service_Game gameService;

    public Async_Service_Word_Evaluation(Domain_Service_Game gameService) {
        this.gameService = gameService;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() {
                gameService.evaluateAllGuesses();
                return null;
            }
        };
    }
}
