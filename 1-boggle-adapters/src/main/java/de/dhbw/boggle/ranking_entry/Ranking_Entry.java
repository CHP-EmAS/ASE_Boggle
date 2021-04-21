package de.dhbw.boggle.ranking_entry;

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
}
