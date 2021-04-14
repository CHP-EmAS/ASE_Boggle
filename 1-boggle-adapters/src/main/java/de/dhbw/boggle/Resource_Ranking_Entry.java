package de.dhbw.boggle;

public class Resource_Ranking_Entry {
    private String playerName;
    private int fieldSize;
    private int points;
    private String dateString;

    public Resource_Ranking_Entry(String playerName, int points, int fieldSize, String dateString) {
        this.playerName = playerName;
        this.points = points;
        this.fieldSize = fieldSize;
        this.dateString = dateString;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int getPoints() {
        return points;
    }

    public String getDateString() {
        return dateString;
    }
}
