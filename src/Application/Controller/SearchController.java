package Application.Controller;

import Application.Model.APIGetRequest;
import Application.Model.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class SearchController {
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

    @FXML
    public void initialize() {
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
}
