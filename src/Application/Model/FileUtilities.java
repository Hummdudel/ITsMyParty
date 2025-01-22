package Application.Model;

import Application.MyApp;
import javafx.collections.ObservableList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtilities {

    private static String title = "File Utilities";
    private static String header;
    private static String content;

    public static void writeTracksToFile() {
        header = "Save File";
        if (MyApp.loadedPlaylist != null) {
            ObservableList<Track> tracks = Database.readPlaylistTracks(MyApp.loadedPlaylist.getId());
            File file = MyApp.instance.showSaveFileDialog();

            if (file != null) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    for (Track track : tracks) {
                        writer.write(track.getName() + " - " + track.getArtist() + " - " + track.getDuration());
                        writer.newLine();
                    }
                    content = "Die Playlist " + MyApp.loadedPlaylist.getName() + " wurde erfolgreich als Datei gespeichert!";
                    MyApp.instance.showMessage(title, header, content);
                } catch (IOException exception) {
                    content = exception.getMessage();
                    MyApp.instance.showWarning(title, header, content);
                }
            }
        }

    }
}
