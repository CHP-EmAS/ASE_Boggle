package mocks;

import de.dhbw.boggle.domain_services.Domain_Service_Timer;

import java.time.Duration;

public class Game_Timer_Mock implements Domain_Service_Timer {
    @Override
    public void startTimer(Duration countdownDuration) {
        System.out.println("Test timer started");
    }

    @Override
    public void cancelTimer() {
        System.out.println("Test timer canceled");
    }
}
