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
    public TextField textSearchArtist;

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

        textSearchArtist.textProperty().addListener((observable) -> filterArtist());
    }

    public void fillTable() {

        ArrayList<Track> trackList = new ArrayList<>();

        if (!(textSearchTrack.getText().equals(""))) {
            String trackName =  textSearchTrack.getText();

            trackList = APIGetRequest.getTrackList(trackName);

            this.trackList.clear();
            this.trackList.addAll(trackList);
        }
    }

    public void filterArtist() {
        String keyword = textSearchArtist.getText();

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

    public void onButtonSearchClick(ActionEvent actionEvent) {
        fillTable();
    }

    public void handleEnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            fillTable();
        }
    }
}
