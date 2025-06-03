import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.jndi.ldap.BerDecoder;

public class CreationDataCSV {

    public static <JsonElement> void main(String[] args) {
        String urlString = "file:///Users/delebakat/IdeaProjects/GetMyData/MyData.jSON"; // Remplacez par votre URL

        try {
            // Connexion HTTP
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Lire la réponse
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Afficher la réponse brute
            System.out.println("Réponse brute : " + response.toString());

            // Parser le JSON avec GSON
            JsonElement json = (JsonElement) JsonParser.parseString(response.toString());
            System.out.println("Données formatées : " + json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}