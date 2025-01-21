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
    private static String queryTrack = "track/";
    private static String queryArtist = "artist/";


    public static ArrayList<Track> getTrackListByTrackname(String name) {
        return getTrackList(name, queryTrack);
    }

    public static ArrayList<Track> getTrackListByArtistName(String name) {
        return getTrackList(name, queryArtist);
    }

    private static ArrayList<Track> getTrackList(String name, String queryType) {

        ArrayList<Track> trackList = new ArrayList<>();

        String query = name.replace("-", "").replace(" ", "-").toLowerCase();
        String additionalInfo = "";

        if (queryType.equals(queryTrack)) {
            query = "track/" + query;
            additionalInfo = "&ai=artist,album";
        }
        else if (queryType.equals(queryArtist)) {
            query = "artist/" + query;
            additionalInfo = "&ai=albums,tracks";
        }

        try {
            String apiUrl = apiUrlRoot + query + apiKey + additionalInfo;

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

                if (queryType.equals(queryTrack)) {
                    JSONArray tracksArray = jsonResponse.getJSONArray("tracks");

                    for (int i = 0; i < tracksArray.length(); i++) {
                        JSONObject trackObject = tracksArray.getJSONObject(i);

                        if (!(trackObject.getString("duration").equals(""))) {
                            // Extrahiere die relevanten Informationen
                            String trackName = trackObject.getString("name");
                            String slug = trackObject.getString("slug");
                            String duration = trackObject.getString("duration");
                            String artist = trackObject.getJSONObject("album").getJSONObject("artist").getString("name");
                            String album = trackObject.getJSONObject("album").getString("name");

                            Track track = new Track(trackName, artist, album, duration);
                            trackList.add(track);
                        }
                    }
                }
                else if (queryType.equals(queryArtist)) {
                    String artist = jsonResponse.getString("name");

                    JSONArray albumsArray = jsonResponse.getJSONArray("albums");

                    for (int i = 0; i < albumsArray.length(); i++) {
                        JSONObject albumObject = albumsArray.getJSONObject(i);
                        String album = albumObject.getString("name");

                        JSONArray tracksArray = albumObject.getJSONArray("tracks");

                        for (int j = 0; j < tracksArray.length(); j++) {
                            JSONObject trackObject = tracksArray.getJSONObject(j);

                            if (!(trackObject.getString("duration").equals(""))) {
                                // Extrahiere die relevanten Informationen
                                String trackName = trackObject.getString("name");
                                String slug = trackObject.getString("slug");
                                String duration = trackObject.getString("duration");

                                Track track = new Track(trackName, artist, album, duration);
                                trackList.add(track);
                            }
                        }
                    }
                }
                return trackList;

            } else {
                header = "API Query";
                content = "GET-Anfrage fehlgeschlagen: " + responseCode;
                MyApp.instance.showWarning(title, header, content);
            }
        }
        catch (Exception exception) {
            header = "API Connection";
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);
        }

        return null;
    }
}
