package de.dhbw.boggle.domain_services;

import de.dhbw.boggle.valueobjects.VO_Word;

public interface Domain_Service_Duden_Check {
    boolean checkIfDudenServiceIsAvailable();

    boolean lookUpWordInDuden(VO_Word word);
}
