package de.dhbw.boggle;

import de.dhbw.boggle.domain_services.Domain_Service_Duden_Check;
import de.dhbw.boggle.valueobjects.VO_Word;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.http.HttpRequest;
import java.util.Locale;
import java.util.Scanner;

public class DWDS_Digital_Dictionary_API implements Domain_Service_Duden_Check {

    @Override
    public boolean checkIfDudenServiceIsAvailable() {
        try {
            URL url = new URL("https://www.dwds.de/api/wb/snippet/?q=Test");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            return (conn.getResponseCode() == 200);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean lookUpWordInDuden(VO_Word word) {

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

                JSONParser jsonParser = new JSONParser();
                JSONArray wordArray = (JSONArray) jsonParser.parse(responseString);

                if(wordArray.isEmpty()) {
                    System.out.println("Word not found!");
                    return false;
                }

                JSONObject wordDefinition = (JSONObject) wordArray.get(0);

                System.out.println("Word found! lemma: " + wordDefinition.get("lemma").toString() + ", type: " + wordDefinition.get("wortart").toString());
                return true;

            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Error while looking up a Word");
        return false;
    }
}
