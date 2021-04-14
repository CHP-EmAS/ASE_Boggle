package de.dhbw.boggle.domain_services;

import de.dhbw.boggle.entities.Entity_Player;
import de.dhbw.boggle.valueobjects.VO_Points;
import de.dhbw.boggle.valueobjects.VO_Word;

public interface Domain_Service_Points_Calculation {

    VO_Points sumUpPointsForPlayer(Entity_Player player);

    VO_Points calculatePointsForWord(VO_Word word);

}
