package de.dhbw.boggle.valueobjects;

import java.util.Objects;

public class VO_Field_Size {
    private final int fieldSize;

    private final int minFieldSize = 3; //included
    private final int maxFieldSize = 7; //included

    public VO_Field_Size(int fieldSize){
        if(isValid(fieldSize)) {
            this.fieldSize = fieldSize;
        } else {
            throw new IllegalArgumentException("Field size must be between " + minFieldSize + " and " + maxFieldSize + "! Given size was " + fieldSize);
        }
    }

    public int getSize() {
        return this.fieldSize;
    }

    private boolean isValid(int fieldSize) {
        return fieldSize >= minFieldSize && fieldSize <= maxFieldSize;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VO_Field_Size vo_field_size) {
            return this.fieldSize == vo_field_size.getSize();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fieldSize);
    }
}
