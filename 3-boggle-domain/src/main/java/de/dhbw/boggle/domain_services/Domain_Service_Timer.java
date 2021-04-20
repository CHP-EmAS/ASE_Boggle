package de.dhbw.boggle.domain_services;

import java.time.Duration;

public interface Domain_Service_Timer {

    void startTimer(Duration countdownDuration);
    void cancelTimer();

}
