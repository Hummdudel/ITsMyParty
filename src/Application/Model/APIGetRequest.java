package Application.Model;

import Application.MyApp;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class APIGetRequest {

    private static String title = "API Get Request";
    private static String header;
    private static String content;

    private static final String apiUrlRoot = "https://api.musicdb.io/v1/";
    private static final String apiKey = "?api_key=f2cbc621198d6de13d91092037cd8716";

    public static Track getTrack(String queryTrackName, String queryArtistName) {

        String query = "artist/" + queryArtistName.replace("-", "").replace(" ", "-").toLowerCase()
                + "/track/" + queryTrackName.replace(" ", "-").toLowerCase() + "/";

        try {
            String apiUrl = apiUrlRoot + query + apiKey + "&ai=artist,album";

            System.out.println(apiUrl);

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());

                if (!(jsonResponse.getString("duration").equals(""))) {
                    String name = jsonResponse.getString("name");
                    String slug = jsonResponse.getString("slug");
                    String duration = jsonResponse.getString("duration");
                    String artist = jsonResponse.getJSONObject("album").getJSONObject("artist").getString("name");

                    Track track = new Track(name, slug, artist, duration);
                    return track;
                }

            } else {
                System.out.println("GET-Anfrage fehlgeschlagen: " + responseCode);
                header = "API Query";
                content = "GET-Anfrage fehlgeschlagen: " + responseCode;
                MyApp.instance.showWarning(title, header, content);
            }
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
            header = "API Connection";
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);
        }
        return null;
    }

    public static ArrayList<Track> getTrackList(String queryTrackName) {

        ArrayList<Track> trackList = new ArrayList<>();

        String query = "track/" + queryTrackName.replace(" ", "-").toLowerCase();

        try {
            String apiUrl = apiUrlRoot + query + apiKey + "&ai=artist,album";

            System.out.println(apiUrl);

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray tracksArray = jsonResponse.getJSONArray("tracks");

                for (int i = 0; i < tracksArray.length(); i++) {
                    JSONObject trackObject = tracksArray.getJSONObject(i);

                    if (!(trackObject.getString("duration").equals(""))) {
                        // Extrahiere die relevanten Informationen
                        String name = trackObject.getString("name");
                        String slug = trackObject.getString("slug");
                        String duration = trackObject.getString("duration");
                        String artist = trackObject.getJSONObject("album").getJSONObject("artist").getString("name");

                        Track track = new Track(name, slug, artist, duration);
                        trackList.add(track);
                    }
                }

                return trackList;

            } else {
                System.out.println("GET-Anfrage fehlgeschlagen: " + responseCode);
                header = "API Query";
                content = "GET-Anfrage fehlgeschlagen: " + responseCode;
                MyApp.instance.showWarning(title, header, content);
            }
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
            header = "API Connection";
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);
        }
        return null;
    }

}
