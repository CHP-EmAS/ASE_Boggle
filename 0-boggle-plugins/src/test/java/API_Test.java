import de.dhbw.boggle.API_DWDS_Digital_Dictionary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import de.dhbw.boggle.valueobjects.VO_Word;

public class API_Test {
    private API_DWDS_Digital_Dictionary api;

    @BeforeEach
    public void setUp() {
        api = new API_DWDS_Digital_Dictionary();
    }

    @Test
    @DisplayName("Checking correct word 'WORT'")
    public void testCorrectWord() {
        assertTrue(api.lookUpWordInDuden(new VO_Word("WORT")), "Word 'WORT' should be correct");
    }

    @Test
    @DisplayName("Checking wrong word 'DASISTKEINWORT'")
    public void testWrongWord() {
        assertFalse(api.lookUpWordInDuden(new VO_Word("DASISTKEINWORT")), "Word 'DASISTKEINWORT' should be wrong");
    }
}
