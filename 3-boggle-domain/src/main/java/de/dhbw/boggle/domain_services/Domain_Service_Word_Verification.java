package de.dhbw.boggle.domain_services;

import de.dhbw.boggle.aggregates.Aggregate_Playing_Field;
import de.dhbw.boggle.value_objects.VO_Word;

public interface Domain_Service_Word_Verification {
    boolean wordIsDuplicateGuess(VO_Word word, Aggregate_Playing_Field playingField);

    void examineAllGuesses(Aggregate_Playing_Field playingField);
}
