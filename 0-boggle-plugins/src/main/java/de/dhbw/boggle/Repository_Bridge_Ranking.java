package de.dhbw.boggle;

import de.dhbw.boggle.entities.Entity_Ranking_Entry;
import de.dhbw.boggle.repositories.Repository_Ranking;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.boggle.valueobjects.VO_Date;
import de.dhbw.boggle.valueobjects.VO_Field_Size;
import de.dhbw.boggle.valueobjects.VO_Points;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Repository_Bridge_Ranking implements Repository_Ranking {

    private final String rankingFileName = "ranks.txt";
    private boolean rankingFileIsLoaded = false;

    private final List<Entity_Ranking_Entry> rankingEntries = new ArrayList<>();

    @Override
    public void addRankingEntry(Entity_Ranking_Entry rankingEntry) {

    }

    @Override
    public Entity_Ranking_Entry getRankingEntryByID(String uuid) {
        return null;
    }

    @Override
    public List<Entity_Ranking_Entry> getAllRankingEntries() {
        return rankingEntries;
    }

    private void loadRankingEntriesFromFile()  {

        JSONParser jsonParser = new JSONParser();

        String jsonFileContent = readOrCreateRankingFile();

        if(!jsonFileContent.isEmpty()) {
            rankingEntries.clear();

            try {
                Object obj = jsonParser.parse(jsonFileContent);
                JSONArray rankingList = (JSONArray) obj;

                rankingList.forEach(ranking_entry -> {
                    Entity_Ranking_Entry parsedRankingEntry = parseRankingEntryObject( (JSONObject) ranking_entry);
                    rankingEntries.add(parsedRankingEntry);
                });

            } catch (ParseException  e) {
                e.printStackTrace();
            }
        }

        rankingFileIsLoaded = true;
    }

    private String readOrCreateRankingFile() {

        File file = new File(rankingFileName);
        FileReader fileReader = null;

        int currentCharacter;
        StringBuffer fileContent = new StringBuffer();

        try {
            file.createNewFile();

            fileReader = new FileReader(file);
            while ((currentCharacter = fileReader.read()) != -1) {
                fileContent.append((char) currentCharacter);
            }
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContent.toString();
    }

    private Entity_Ranking_Entry parseRankingEntryObject(JSONObject rankingEntry)
    {
        JSONObject rankingEntryObject = (JSONObject) rankingEntry.get("rankingEntry");

        String playerName = (String) rankingEntryObject.get("playerName");
        int points = (int) rankingEntryObject.get("points");
        int fieldSize = (int) rankingEntryObject.get("fieldSize");
        String date = (String) rankingEntryObject.get("date");

        return new Entity_Ranking_Entry(playerName, new VO_Points(points), new VO_Field_Size(fieldSize), new VO_Date(date));
    }

}
