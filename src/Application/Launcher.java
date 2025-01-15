package Application;

import Application.Model.APIGetRequest;

public class Launcher {
    public static void main(String[] args) {
        // MyApp.launch(MyApp.class, args);

        APIGetRequest.getTrack("artist/new-model-army/track/51st-state", "artist,album");
    }
}
