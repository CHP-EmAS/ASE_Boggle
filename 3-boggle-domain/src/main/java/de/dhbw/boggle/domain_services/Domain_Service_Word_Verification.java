package de.dhbw.boggle.domain_services;

import de.dhbw.boggle.valueobjects.VO_Word;
import de.dhbw.boggle.entities.Entity_Letter_Salad;

public interface Domain_Service_Word_Verification {
    boolean checkIfWordIsBuildableWithLetterSalad(VO_Word word, Entity_Letter_Salad letterSalad);
}
