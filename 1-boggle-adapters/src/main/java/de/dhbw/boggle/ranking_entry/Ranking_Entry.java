package de.dhbw.boggle.ranking_entry;

import org.json.simple.JSONObject;

public class Ranking_Entry {
    public final String playerName;
    public final int fieldSize;
    public final int points;
    public final String dateString;

    public Ranking_Entry(String playerName, int points, int fieldSize, String dateString) {
        this.playerName = playerName;
        this.points = points;
        this.fieldSize = fieldSize;
        this.dateString = dateString;
    }

    public JSONObject toJson() {
        JSONObject entry = new JSONObject();

        entry.put("playerName", playerName);
        entry.put("points", points);
        entry.put("fieldSize", fieldSize);
        entry.put("dateString", dateString);

        return entry;
    }

    public int getPoints() {
        return points;
    }

}
