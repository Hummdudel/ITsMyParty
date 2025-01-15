package Application;

import Application.Model.APIGetRequest;
import Application.Model.Track;

public class Launcher {
    public static void main(String[] args) {
        // MyApp.launch(MyApp.class, args);

        Track track = new Track();
        track = APIGetRequest.getTrack("artist/new-model-army/track/51st-state", "artist,album");
        track.showdetails();

    }
}
