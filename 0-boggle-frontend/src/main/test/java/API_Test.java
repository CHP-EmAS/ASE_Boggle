import de.dhbw.boggle.API_DWDS_Digital_Dictionary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.dhbw.boggle.value_objects.VO_Word;

public class API_Test {
    private API_DWDS_Digital_Dictionary api;

    @BeforeEach
    public void setUp() {
        api = new API_DWDS_Digital_Dictionary();
    }

    @Test
    @DisplayName("Availability Check")
    public void availabilityCheck() {
        Assertions.assertTrue(api.dictionaryServiceIsAvailable(), "API not available");
    }

    @Test
    @DisplayName("Checking correct word 'WORT'")
    public void testCorrectWord() {
        api.dictionaryServiceIsAvailable();
        Assertions.assertTrue(api.lookUpWordInDictionary(new VO_Word("WORT")), "Word 'WORT' should be correct");
    }

    @Test
    @DisplayName("Checking wrong word 'DASISTKEINWORT'")
    public void testWrongWord() {
        api.dictionaryServiceIsAvailable();
        Assertions.assertFalse(api.lookUpWordInDictionary(new VO_Word("DASISTKEINWORT")), "Word 'DASISTKEINWORT' should be wrong");
    }
}
