package de.dhbw.boggle.entities;

import java.util.Objects;
import java.util.UUID;

public class Entity_Player {

    private final String uuid;

    private final String name;

    public final static short minPlayerNameLength = 1; //included
    public final static short maxPlayerNameLength = 20; //included

    public Entity_Player(String name) {
        if(!isValid(name))
            throw new IllegalArgumentException("Name '" + name + "' is invalid for a player name! The name must be at least " + minPlayerNameLength + " and no more than " + maxPlayerNameLength + " letters long.");


        this.name = name;
        this.uuid = UUID.randomUUID().toString();
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.uuid;
    }

    private boolean isValid(String name) {
        return (name.length() >= minPlayerNameLength && name.length() <= maxPlayerNameLength);
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
