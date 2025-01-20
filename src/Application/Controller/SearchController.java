package Application.Controller;

import Application.Model.APIGetRequest;
import Application.Model.Database;
import Application.Model.Playlist;
import Application.Model.Track;
import Application.MyApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;

import java.util.ArrayList;

public class SearchController {
    public ComboBox<String> comboBoxPlaylist = new ComboBox<>();

    public TextField textSearchTrack;
    public TextField textFilterArtist;
    public TextField textSearchArtist;
    public TextField textFilterTrack;

    public TableView<Track> tableTracks;
    public TableColumn<Track, String> name;
    public TableColumn<Track, String> artist;
    public TableColumn<Track, String> album;
    public TableColumn<Track, String> duration;

    private ObservableList<Track> trackList;

    private ObservableList<Playlist> playlistList;

    @FXML
    public void initialize() {

        playlistList = Database.readAllPlaylists();
        fillComboBoxWithPlaylistNames();

        if (MyApp.loadedPlaylist != null) {
            comboBoxPlaylist.setValue(MyApp.loadedPlaylist.getName());
        }

        comboBoxPlaylist.setOnAction(event -> handleComboBoxSelection());

        // Set die CellValueFactory for each column
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        artist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        album.setCellValueFactory(new PropertyValueFactory<>("album"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        // Initialize ObservableList
        trackList = FXCollections.observableArrayList();

        // Bind ObservableList to TableView
        tableTracks.setItems(trackList);

        textFilterArtist.textProperty().addListener((observable) -> filterArtist());
        textFilterTrack.textProperty().addListener((observable) -> filterTrack());

        // Context menu for Table

//        ContextMenu contextMenu = new ContextMenu();
//
//        MenuItem option1 = new MenuItem("Add Track");
//        option1.setOnAction(event -> addTrackToPlaylist());

//        MenuItem option2 = new MenuItem("Option 2");
//        option2.setOnAction(event -> handleOption2());

//        contextMenu.getItems().addAll(option1);

        tableTracks.setRowFactory(tv -> {
            TableRow<Track> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY && !row.isEmpty()) {
                    Track track = row.getItem(); // Das Track-Objekt der angeklickten Zeile

                    ContextMenu contextMenu = new ContextMenu();
                    MenuItem option1 = new MenuItem("Add Track");
                    option1.setOnAction(e -> addTrackToPlaylist(track)); // Übergabe des Track-Objekts

                    contextMenu.getItems().add(option1);
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

    public void fillTableFromTrackSearch() {

        ArrayList<Track> trackList = new ArrayList<>();

        if (!(textSearchTrack.getText().equals(""))) {
            String trackName =  textSearchTrack.getText();

            trackList = APIGetRequest.getTrackList(trackName);

            this.trackList.clear();
            this.trackList.addAll(trackList);
        }
    }

    public void fillTableFromArtistSearch() {

        ArrayList<Track> trackList = new ArrayList<>();

        if (!(textSearchArtist.getText().equals(""))) {
            String artistName =  textSearchArtist.getText();

            trackList = APIGetRequest.getArtistTrackList(artistName);

            this.trackList.clear();
            this.trackList.addAll(trackList);
        }
    }

    public void filterArtist() {
        String keyword = textFilterArtist.getText();

        if (keyword.equals("")) {
            tableTracks.setItems(trackList);
        }
        else {
            ObservableList<Track> filteredData = FXCollections.observableArrayList();
            for (Track track : trackList) {
                if (track.getArtist().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredData.add(track);
                }
            }
            tableTracks.setItems(filteredData);
        }
    }

    public void filterTrack() {
        String keyword = textFilterTrack.getText();

        if (keyword.equals("")) {
            tableTracks.setItems(trackList);
        }
        else {
            ObservableList<Track> filteredData = FXCollections.observableArrayList();
            for (Track track : trackList) {
                if (track.getName().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredData.add(track);
                }
            }
            tableTracks.setItems(filteredData);
        }
    }

    public void handleEnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (keyEvent.getSource() == textSearchTrack) {
                fillTableFromTrackSearch();
            }
            else if (keyEvent.getSource() == textSearchArtist) {
                fillTableFromArtistSearch();
            }
        }
    }

    public void onButtonTrackSearchClick(ActionEvent actionEvent) {
        fillTableFromTrackSearch();
    }

    public void onButtonClearArtistClick(ActionEvent actionEvent) {
        textFilterArtist.setText("");
    }

    public void onButtonClearTrackSearchClick(ActionEvent actionEvent) {
        textFilterArtist.setText("");
        textSearchTrack.setText("");
    }

    public void onButtonClearArtistSearchClick(ActionEvent actionEvent) {
        textFilterTrack.setText("");
        textSearchArtist.setText("");
    }

    public void onButtonArtistSearchClick(ActionEvent actionEvent) {
        fillTableFromArtistSearch();
    }

    public void onButtonClearTrackClick(ActionEvent actionEvent) {
        textFilterTrack.setText("");
    }

    // Select and handle track from Table

    private void addTrackToPlaylist(Track track) {
        if (!Database.artistExists(track.getArtist())) {

        }

        if (!Database.trackExists(track.getName())) {

        }

    }
}
