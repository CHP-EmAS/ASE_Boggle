package de.dhbw.boggle.services;

import de.dhbw.boggle.value_objects.VO_Dice;
import de.dhbw.boggle.value_objects.VO_Field_Size;

import java.util.Arrays;

public class Service_Dice_Builder {
    public VO_Dice[] buildDiceArray(VO_Field_Size playingFieldSize) {

        switch(playingFieldSize.getSize()) {
            case 4:
                return generate4x4Dices();
            case 5:
                return generate5x5Dices();
            default:
                throw new RuntimeException("Unknown field size when trying to generate the corresponding number of dices!");
        }
    }

    private VO_Dice[] generate4x4Dices() {
        VO_Dice[] generatedDices = new VO_Dice[16];

        generatedDices[0] =  new VO_Dice("TDSNOE");
        generatedDices[1] =  new VO_Dice("LRBTAI");
        generatedDices[2] =  new VO_Dice("LARESC");
        generatedDices[3] =  new VO_Dice("IOAATE");
        generatedDices[4] =  new VO_Dice("ABOJQM");
        generatedDices[5] =  new VO_Dice("XRIFOA");
        generatedDices[6] =  new VO_Dice("OIASMR");
        generatedDices[7] =  new VO_Dice("INERHS");
        generatedDices[8] =  new VO_Dice("INEGVT");
        generatedDices[9] =  new VO_Dice("SUTEPL");
        generatedDices[10] = new VO_Dice("ECAPMD");
        generatedDices[11] = new VO_Dice("RUEILW");
        generatedDices[12] = new VO_Dice("UTOKEN");
        generatedDices[13] = new VO_Dice("LGNYEU");
        generatedDices[14] = new VO_Dice("HEFSIE");
        generatedDices[15] = new VO_Dice("AZEVND");

        return generatedDices;
    }

    private VO_Dice[] generate5x5Dices() {
        VO_Dice[] generatedDices =  Arrays.copyOf(generate4x4Dices(),25);

        generatedDices[16] =  new VO_Dice("PAIBTN");
        generatedDices[17] =  new VO_Dice("SHLUDE");
        generatedDices[18] =  new VO_Dice("MRECZA");
        generatedDices[19] =  new VO_Dice("VJIEGR");
        generatedDices[20] =  new VO_Dice("TNZDEL");
        generatedDices[21] =  new VO_Dice("LOWUIE");
        generatedDices[22] =  new VO_Dice("MIRTAK");
        generatedDices[23] =  new VO_Dice("FONUAS");
        generatedDices[24] =  new VO_Dice("AISEEO");

        return generatedDices;
    }
}
