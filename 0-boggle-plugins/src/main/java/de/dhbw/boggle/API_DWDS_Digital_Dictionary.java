package de.dhbw.boggle;

import de.dhbw.boggle.domain_services.Domain_Service_Dictionary_Check;
import de.dhbw.boggle.value_objects.VO_Word;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class API_DWDS_Digital_Dictionary implements Domain_Service_Dictionary_Check {

    private static boolean checkedIfAPIIsAvailable = false;
    private static boolean apiAvailable = false;

    @Override
    public boolean dictionaryServiceIsAvailable() {

        if(checkedIfAPIIsAvailable)
            return apiAvailable;

        checkedIfAPIIsAvailable = true;
        apiAvailable = true;

        /*
        try {
            URL url = new URL("https://www.dwds.de/api/wb/snippet/?q=Test");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            apiAvailable = (conn.getResponseCode() == 200);
            checkedIfAPIIsAvailable = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        return apiAvailable;

    }

    @Override
    public boolean lookUpWordInDictionary(VO_Word word) {

        if(!checkedIfAPIIsAvailable)
           throw new RuntimeException("API is not available or has not yet been checked for availability!");

        if(!apiAvailable)
            return false;

        String upperCaseWord = word.getWord();
        String lowerCaseWord = upperCaseWord.toLowerCase();
        String capitalizedWord = lowerCaseWord.substring(0, 1).toUpperCase() + lowerCaseWord.substring(1);

        return checkIfWordExistsInDigitalDictionary(capitalizedWord + "|" + lowerCaseWord + "|" + upperCaseWord);
    }

    private boolean checkIfWordExistsInDigitalDictionary(String word) {

        System.out.println("Looking up word " + word + " in API...");

        try {
            URL url = new URL("https://www.dwds.de/api/wb/snippet/?q=" + word);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() != 200) {
                System.out.println("Error while looking up a Word! ResponseCode is not 200");
                return false;
            } else {

                String responseString = "";
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    responseString = responseString.concat(scanner.nextLine());
                }

                scanner.close();

                return parseAPIResponse(responseString);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Error while looking up a Word");
        return false;
    }

    private boolean parseAPIResponse(String responseString) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONArray wordArray = (JSONArray) jsonParser.parse(responseString);

        if(wordArray.isEmpty()) {
            System.out.println("Word not found!");
            return false;
        }

        JSONObject wordDefinition = (JSONObject) wordArray.get(0);

        String wordType = wordDefinition.get("wortart") == null ? "---" : wordDefinition.get("wortart").toString();

        System.out.println("Word found! lemma: " + wordDefinition.get("lemma").toString() + ", type: " + wordType);
        return true;
    }
}
