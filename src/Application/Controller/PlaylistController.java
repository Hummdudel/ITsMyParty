package Application.Controller;

import Application.Model.Database;
import Application.Model.FileUtilities;
import Application.Model.Playlist;
import Application.Model.Track;
import Application.MyApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.util.ArrayList;

public class PlaylistController {

    private String title = "Playlist Controller";
    private static String header;
    private static String content;

    public ComboBox<String> comboBoxPlaylist = new ComboBox<>();

    public Label labelNumberOfTracks;
    public Label labelPlaylistDuration;

    public TableView<Track> tableTracks;
    public TableColumn<Track, String> name;
    public TableColumn<Track, String> artist;
    public TableColumn<Track, String> duration;
    public TableColumn<Track, Integer> priority;

    private ObservableList<Track> trackList;
    private ObservableList<Playlist> playlistList;

    public void initialize() {

        // ComboBox

        playlistList = Database.readAllPlaylists();
        fillComboBoxWithPlaylistNames();

        if (MyApp.loadedPlaylist != null) {
            comboBoxPlaylist.setValue(MyApp.loadedPlaylist.getName());
            handleComboBoxSelection();
        }

        comboBoxPlaylist.setOnAction(event -> handleComboBoxSelection());

        // Tracklist Table

        // Set CellValueFactory for each column
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        artist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        priority.setCellValueFactory(new PropertyValueFactory<>("priority"));

        // Context menu for Table

        tableTracks.setRowFactory(tv -> {
            TableRow<Track> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY && !row.isEmpty()) {
                    Track track = row.getItem(); // Das Track-Objekt der angeklickten Zeile

                    ContextMenu contextMenu = new ContextMenu();
                    MenuItem optionRemoveTrack = new MenuItem("Remove Track");
                    optionRemoveTrack.setOnAction(event1 -> removeTrackFromPlaylist(track)); // Übergabe des Track-Objekts
                    MenuItem optionUpdatePriority = new MenuItem("Update Priority");
                    optionUpdatePriority.setOnAction(event1 -> updatePiority(track)); // Übergabe des Track-Objekts

                    contextMenu.getItems().add(optionRemoveTrack);
                    contextMenu.getItems().add(optionUpdatePriority);
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                }
            });
            return row;
        });
    }

    private void handleComboBoxSelection() {
        String selectedPlaylistName = comboBoxPlaylist.getSelectionModel().getSelectedItem();
        if (selectedPlaylistName != null) {
            // Finde die ausgewählte Playlist in der Liste
            Playlist selectedPlaylist = playlistList.stream()
                    .filter(playlist -> playlist.getName().equals(selectedPlaylistName))
                    .findFirst()
                    .orElse(null);

            MyApp.loadedPlaylist = selectedPlaylist;

            if (selectedPlaylist != null) {
                // Verwende die Playlist-ID für die Datenbankabfrage
                trackList = Database.readPlaylistTracks(selectedPlaylist.getId());
                tableTracks.setItems(trackList);

                // Aktualisiere die Labels
                if (trackList.size() > 0) {
                    labelNumberOfTracks.setText(String.valueOf(trackList.size()));
                    // Berechne die Dauer der Playlist (angenommen, du hast eine Methode dafür)
                    labelPlaylistDuration.setText(calculateDuration(trackList));
                }
            }
        }
    }

    private void fillComboBoxWithPlaylistNames() {
        ObservableList<String> playlistNames = FXCollections.observableArrayList();

        for (Playlist playlist : playlistList) {
            playlistNames.add(playlist.getName());
        }

        comboBoxPlaylist.setItems(playlistNames);
    }

    private void removeTrackFromPlaylist(Track track) {
        track.setId(Database.readTrackId(track.getName()));
        Database.deletePlaylistEntry(track, MyApp.loadedPlaylist);
        MyApp.instance.showView("PlaylistView");
    }

    private void updatePiority(Track track) {
        String newPriorityString = MyApp.instance.showInputDialog("Playlists", "New Playlist", "Priorität (Zahl):");
        if (newPriorityString != null) {
            try {
                int newPriority = Integer.parseInt(newPriorityString);
                track.setId(Database.readTrackId(track.getName()));
                Database.updatePriority(newPriority, track, MyApp.loadedPlaylist);
                MyApp.instance.showView("PlaylistView");
            }
            catch (NumberFormatException exception) {
                header = "Update Priority";
                content = "Ungültige Eingabe! Bitte geben Sie eine gültige Zahl ein.";
                MyApp.instance.showWarning(title, header, content);
            }
        }
    }

    private String calculateDuration(ObservableList<Track> trackList) {
        int sumSeconds = 0;
        for (Track track : trackList) {
            sumSeconds += track.getSeconds();
        }
        return secondsToDuration(sumSeconds);
    }

    private String secondsToDuration(int seconds) {
        return String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60);
    }

    public void onButtonDeletePlaylistClick(ActionEvent actionEvent) {
        if (MyApp.loadedPlaylist != null) {
            header = "Delete Playlist";
            content = "Soll die Playlist " + MyApp.loadedPlaylist.getName() + " wirklich gelöscht werden?";
            int choice =  MyApp.instance.showConfirmation(title, header, content);
            if (choice == 1) {
                Database.deletePlaylist(MyApp.loadedPlaylist);
                MyApp.loadedPlaylist = null;
                MyApp.instance.showView("PlaylistView");
            }
        }

    }

    public void onButtonNewPlaylistClick(ActionEvent actionEvent) {
        createNewPlaylist();
    }

    public static void createNewPlaylist() {
        String playlistName = MyApp.instance.showInputDialog("Playlists", "New Playlist", "Name:");
        if (!playlistName.equals("")) {
            Database.createPlaylist(playlistName);
            MyApp.loadedPlaylist = Database.readPlaylist(playlistName);
            MyApp.instance.showView("PlaylistView");
        }
    }

    public void onButtonSavePlaylistClick(ActionEvent actionEvent) {
        FileUtilities.writeTracksToFile();
    }

    public void onButtonToSearchClick(ActionEvent actionEvent) {
        MyApp.instance.showView("SearchView");
    }
}
