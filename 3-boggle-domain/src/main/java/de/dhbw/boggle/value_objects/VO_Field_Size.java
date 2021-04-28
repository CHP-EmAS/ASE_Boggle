package de.dhbw.boggle.value_objects;

import java.util.Objects;

public final class VO_Field_Size {
    private final short fieldSize;

    private final short minFieldSize = 4; //included
    private final short maxFieldSize = 5; //included

    public VO_Field_Size(short fieldSize){
        if(isValid(fieldSize)) {
            this.fieldSize = fieldSize;
        } else {
            throw new IllegalArgumentException("Field size must be between " + minFieldSize + " and " + maxFieldSize + "! Given size was " + fieldSize);
        }
    }

    public short getSize() {
        return this.fieldSize;
    }

    private boolean isValid(short fieldSize) {
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
