package de.dhbw.boggle.async_services;

import de.dhbw.boggle.domain_services.Domain_Service_Timer;
import de.dhbw.boggle.time.Mapper_Time;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

public class Async_Service_Countdown extends ScheduledService<Duration> implements Domain_Service_Timer {

    private Duration counterDuration;
    private boolean isRunning = false;

    private final Duration tickerSpeed = Duration.millis(100);

    @Override
    public void startTimer(java.time.Duration countdownDuration) {
        if(isRunning)
            throw new RuntimeException("A running Timer cannot be started again! Try to cancel the timer first.");

        counterDuration = Mapper_Time.convert(countdownDuration);
        isRunning = true;

        this.setDelay(tickerSpeed);
        this.setPeriod(tickerSpeed);

        this.start();
    }

    @Override
    public void cancelTimer() {
        if(this.isRunning())
            this.cancel();

        isRunning = false;
    }

    @Override
    protected Task<Duration> createTask() {
        return new Task<>() {
            @Override
            protected Duration call() {

                if(counterDuration.toMillis() <= 0) {
                    this.cancel();
                }

                counterDuration = counterDuration.subtract(tickerSpeed);
                return counterDuration;

            }
        };
    }
}
