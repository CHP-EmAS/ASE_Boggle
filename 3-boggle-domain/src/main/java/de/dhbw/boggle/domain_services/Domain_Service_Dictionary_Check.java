package de.dhbw.boggle.domain_services;

import de.dhbw.boggle.value_objects.VO_Word;

public interface Domain_Service_Dictionary_Check {
    boolean dictionaryServiceIsAvailable();

    boolean lookUpWordInDictionary(VO_Word word);
}
