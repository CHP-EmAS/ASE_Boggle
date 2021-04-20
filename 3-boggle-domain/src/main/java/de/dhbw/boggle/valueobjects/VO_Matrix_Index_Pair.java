package de.dhbw.boggle.valueobjects;

import java.util.Objects;

public final class VO_Matrix_Index_Pair {
    private final int i;
    private final int j;

    public VO_Matrix_Index_Pair(int i, int j){
        if(isValid(i) && isValid(j)) {
            this.i = i;
            this.j = j;
        } else {
            throw new IllegalArgumentException("Matrix index can not be negative! Given index pair was " + i + "," + j);
        }
    }

    public int getI() {
        return this.i;
    }

    public int getJ() {
        return this.j;
    }

    private boolean isValid(int index) {
        return index >= 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VO_Matrix_Index_Pair vo_index_pair) {
            return (this.i == vo_index_pair.getI() &&
                    this.j == vo_index_pair.getJ());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.i + this.j);
    }
}
