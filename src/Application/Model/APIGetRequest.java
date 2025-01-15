package Application.Model;

import Application.MyApp;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIGetRequest {

    private static String title = "API Get Request";
    private static String header;
    private static String content;

    private static final String apiKey = "?api_key=f2cbc621198d6de13d91092037cd8716";

    public static Track getTrack(String queryString, String... additionalInformation) {

        try {
            String apiUrl = "https://api.musicdb.io/v1/" + queryString + apiKey;

            if (additionalInformation.length > 0) {
                String ai = String.join(",", additionalInformation);
                apiUrl += "&ai=" + ai;
            }

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

                Track track = new Track();
                JSONObject jsonResponse = new JSONObject(response.toString());
                track.setName(jsonResponse.getString("name"));
                track.setSlug(jsonResponse.getString("slug"));
                track.setDuration(jsonResponse.getString("duration"));
                JSONObject album = jsonResponse.getJSONObject("album");
                JSONObject artist = album.getJSONObject("artist");
                track.setArtist(artist.getString("name"));
                return track;

            } else {
                System.out.println("GET-Anfrage fehlgeschlagen: " + responseCode);
                /*header = "API Query";
                content = "GET-Anfrage fehlgeschlagen: " + responseCode;
                MyApp.instance.showWarning(title, header, content);*/
            }
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
            /*header = "API Connection";
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);*/
        }

        return null;

    }

}
