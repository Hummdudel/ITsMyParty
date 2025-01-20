package Application.Controller;

import Application.Model.Database;
import Application.Model.Playlist;
import Application.Model.Track;
import Application.MyApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class PlaylistController {
    public ComboBox<String> comboBoxPlaylist = new ComboBox<>();

    public Label labelNumberOfTracks;
    public Label labelPlaylistDuration;

    public TableView<Track> tableTracks;
    public TableColumn<Track, String> track;
    public TableColumn<Track, String> artist;
    public TableColumn<Track, String> duration;
    public TableColumn<Track, Integer> priority;

    private ObservableList<Playlist> playlistList;

    public void initialize() {
        playlistList = Database.readAllPlaylists();
        fillComboBoxWithPlaylistNames();

        track.setCellValueFactory(new PropertyValueFactory<>("track"));
        artist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        priority.setCellValueFactory(new PropertyValueFactory<>("priority"));

        if (MyApp.loadedPlaylist != null) {
            comboBoxPlaylist.setValue(MyApp.loadedPlaylist.getName());
        }

        comboBoxPlaylist.setOnAction(event -> handleComboBoxSelection());
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
                ObservableList<Track> tracks = Database.readPlaylistTracks(selectedPlaylist.getId());
                tableTracks.setItems(tracks);

                // Aktualisiere die Labels
                assert tracks != null;
                labelNumberOfTracks.setText(String.valueOf(tracks.size()));
                // Berechne die Dauer der Playlist (angenommen, du hast eine Methode dafür)
                labelPlaylistDuration.setText(selectedPlaylist.getDuration());
            }
        }
    }

    private void fillComboBoxWithPlaylistNames() {
        ObservableList<String> playlistNames = FXCollections.observableArrayList();

        for (Playlist playlist : playlistList) {
            playlistNames.add(playlist.getName());
            System.out.println(playlist.getName());
        }

        comboBoxPlaylist.setItems(playlistNames);
    }
}
