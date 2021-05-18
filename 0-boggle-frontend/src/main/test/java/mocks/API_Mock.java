package mocks;

import de.dhbw.boggle.domain_services.Domain_Service_Dictionary_Check;
import de.dhbw.boggle.value_objects.VO_Word;

public class API_Mock implements Domain_Service_Dictionary_Check {
    @Override
    public boolean dictionaryServiceIsAvailable() {
        return true;
    }

    @Override
    public boolean lookUpWordInDictionary(VO_Word word) {

        if(word.equals(new VO_Word("LIED")))
            return true;

        return false;
    }
}
