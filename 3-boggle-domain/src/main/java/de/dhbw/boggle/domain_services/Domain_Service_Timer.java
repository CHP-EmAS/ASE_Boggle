package de.dhbw.boggle.domain_services;

import java.util.function.Function;

public interface Domain_Service_Timer {
    void startTimer(Function callback);
    void cancelTimer();
}
