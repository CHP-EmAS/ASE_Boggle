package de.dhbw.boggle.domain_services;

import de.dhbw.boggle.aggregates.Aggregate_Playing_Field;
import de.dhbw.boggle.value_objects.VO_Points;
import de.dhbw.boggle.value_objects.VO_Word;

public interface Domain_Service_Points_Calculation {

    VO_Points sumUpPoints(Aggregate_Playing_Field playingField);

    VO_Points calculatePointsForWord(VO_Word word);

}
