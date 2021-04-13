package de.dhbw.entities;

import java.util.Objects;
import java.util.UUID;

public class Entity_Player {

    private final String uuid;

    private final String name;
    private int points = 0;

    public Entity_Player(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID().toString();
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return this.points;
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
