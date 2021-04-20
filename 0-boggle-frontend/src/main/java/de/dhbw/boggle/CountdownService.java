package de.dhbw.boggle;

import de.dhbw.boggle.domain_services.Domain_Service_Timer;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

public class CountdownService extends ScheduledService<Duration> implements Domain_Service_Timer {

    private Duration counterDuration;
    private boolean isRunning = false;

    private final Duration tickerSpeed = Duration.millis(100);

    @Override
    public void startTimer(java.time.Duration countdownDuration) {
        if(isRunning)
            throw new RuntimeException("A running Timer cannot be started again! Try to cancel the timer first.");

        counterDuration = Resource_Mapper_Time.convert(countdownDuration);
        isRunning = true;

        this.setDelay(tickerSpeed);
        this.setPeriod(tickerSpeed);

        this.start();
    }

    @Override
    public void cancelTimer() {
        this.cancel();
        isRunning = false;
    }

    @Override
    protected Task<Duration> createTask() {
        return new Task<Duration>() {
            @Override
            protected Duration call() throws Exception {

                counterDuration = counterDuration.subtract(tickerSpeed);
                return counterDuration;

            }
        };
    }
}
