package de.dhbw.valueobjects;

import java.util.Objects;

public final class VO_Points {
    private final int points;

    public VO_Points(int points){
        if(isValid(points)) {
            this.points = points;
        } else {
            throw new IllegalArgumentException("Points must be greater than 0! Given integer was " + points);
        }
    }

    public int getPoints() {
        return this.points;
    }

    private boolean isValid(int points) {
        return points > 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VO_Points vo_points) {
            return this.points == vo_points.getPoints();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.points);
    }
}
