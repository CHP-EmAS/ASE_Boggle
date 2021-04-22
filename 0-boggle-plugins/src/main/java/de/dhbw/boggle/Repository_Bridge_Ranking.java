package de.dhbw.boggle;

import de.dhbw.boggle.entities.Entity_Ranking_Entry;
import de.dhbw.boggle.ranking_entry.Ranking_Entry;
import de.dhbw.boggle.ranking_entry.Ranking_Entry_Mapper;
import de.dhbw.boggle.repositories.Repository_Ranking;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import de.dhbw.boggle.valueobjects.VO_Date;
import de.dhbw.boggle.valueobjects.VO_Field_Size;
import de.dhbw.boggle.valueobjects.VO_Points;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Repository_Bridge_Ranking implements Repository_Ranking {

    private final String rankingFileName = "ranks.json";
    private boolean rankingFileIsLoaded = false;

    private final Ranking_Entry_Mapper rankingEntryMapper = new Ranking_Entry_Mapper();

    private final List<Entity_Ranking_Entry> rankingEntries = new ArrayList<>();

    @Override
    public void addRankingEntry(Entity_Ranking_Entry rankingEntry) {
        if(!rankingFileIsLoaded)
            throw new RuntimeException("Ranking entries are not loaded! Please load them first!");

        rankingEntries.add(rankingEntry);

        saveRankingEntriesToFile(rankingFileName);
    }

    @Override
    public Entity_Ranking_Entry getRankingEntryByID(String uuid) {
        if(!rankingFileIsLoaded)
            throw new RuntimeException("Ranking entries are not loaded! Please load them first!");

        return rankingEntries.stream().filter(rankingEntry -> rankingEntry.getId().equals(uuid))
                .findFirst().orElse(null);
    }

    @Override
    public List<Entity_Ranking_Entry> getRankingByFieldSize(VO_Field_Size fieldSize) {
        if(!rankingFileIsLoaded)
            throw new RuntimeException("Ranking entries are not loaded! Please load them first!");

        return rankingEntries.stream()
                .filter(rankingEntry -> rankingEntry.getFieldSize().equals(fieldSize))
                .collect(Collectors.toList());
    }

    @Override
    public boolean loadAllRankingEntries() {
        if(!rankingFileIsLoaded) {
            rankingFileIsLoaded = true;
            return loadRankingEntriesFromFile(rankingFileName);
        }

        return true;
    }

    private void saveRankingEntriesToFile(String fileName)  {

        JSONArray rankingListJSONArray = new JSONArray();

        rankingEntries.forEach(ranking_entry -> {
            Ranking_Entry newEntry = rankingEntryMapper.apply(ranking_entry);

            rankingListJSONArray.add(newEntry.toJson());
        });

        try (FileWriter file = new FileWriter(fileName)) {

            file.write(rankingListJSONArray.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean loadRankingEntriesFromFile(String fileName)  {

        JSONParser jsonParser = new JSONParser();

        String jsonFileContent = readOrCreateRankingFile(fileName);

        if(!jsonFileContent.isEmpty()) {
            rankingEntries.clear();

            try {
                Object obj = jsonParser.parse(jsonFileContent);
                JSONArray rankingList = (JSONArray) obj;

                rankingList.forEach(rankingEntry -> {
                    Entity_Ranking_Entry parsedRankingEntry = parseRankingEntryObject( (JSONObject) rankingEntry);
                    rankingEntries.add(parsedRankingEntry);
                });

            } catch (ParseException  e) {
                e.printStackTrace();
                return false;
            }
        }

       return true;
    }

    private String readOrCreateRankingFile(String fileName) {

        File file = new File(fileName);
        FileReader fileReader;

        int currentCharacter;
        StringBuilder fileContent = new StringBuilder();

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

    private Entity_Ranking_Entry parseRankingEntryObject(JSONObject rankingEntry) {

        String playerName = (String) rankingEntry.get("playerName");
        long points = (long) rankingEntry.get("points");
        long fieldSize = (long) rankingEntry.get("fieldSize");
        String date = (String) rankingEntry.get("dateString");

        return new Entity_Ranking_Entry(playerName, new VO_Points((int) points), new VO_Field_Size((short) fieldSize), new VO_Date(date));
    }

}
