package de.dhbw.boggle.valueobjects;

import java.util.Objects;

public final class VO_Word {
    private final String uppercaseWord;

    public VO_Word(String uppercaseWord){
        if(isValid(uppercaseWord)) {
            this.uppercaseWord = uppercaseWord;
        } else {
            throw new IllegalArgumentException("String '" + uppercaseWord + "' is invalid for a Word! All letters must be uppercase letters and the Word must have at lest on letter");
        }
    }

    public String getWord() {
        return this.uppercaseWord;
    }

    private boolean isValid(String uppercaseWord) {
        for(int i = 0; i < uppercaseWord.length(); i++) {
            if(uppercaseWord.charAt(i) < 'A' || uppercaseWord.charAt(i) > 'Z') {
                return false;
            }
        }

        return uppercaseWord.length() > 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VO_Word vo_word) {
            return this.uppercaseWord.equals(vo_word.getWord());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uppercaseWord);
    }
}
