package de.dhbw.entities;

import de.dhbw.valueobjects.VO_Points;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Entity_Player {

    private final String uuid;

    private final String name;
    private ArrayList<VO_Points> pointsList;

    public Entity_Player(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID().toString();

        pointsList = new ArrayList<>();
    }

    public void addPoints(VO_Points points) {
        this.pointsList.add(points);
    }

    public VO_Points sumUpPoints() {
        int result = 0;

        for (VO_Points points : pointsList) {
            result += points.getPoints();
        }

        return new VO_Points(result);
    }

    public ArrayList<VO_Points> getPointsList() {
        return new ArrayList<>(pointsList);
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.uuid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Entity_Player player) {
            return this.getId().equals(player.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uuid);
    }
}
