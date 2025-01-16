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

import java.util.ArrayList;

public class SearchController {
    public TextField textSearchTrack;
    public TextField textSearchArtist;
    public TableView<Track> tableTracks;
    public TableColumn<Track, String> name;
    public TableColumn<Track, String> artist;
    public TableColumn<Track, String> duration;

    private ObservableList<Track> trackList;

    @FXML
    public void initialize() {
        // Setze die Zellwertfabrik für jede Spalte
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        artist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        // Initialisiere die ObservableList
        trackList = FXCollections.observableArrayList();

        // Binde die ObservableList an die TableView
        tableTracks.setItems(trackList);
    }


    public void onButtonSearchClick(ActionEvent actionEvent) {

        ArrayList<Track> trackList = new ArrayList<>();

        if (!(textSearchTrack.getText().equals(""))) {
            String trackName =  textSearchTrack.getText();

            if (!(textSearchArtist.getText().equals(""))) {
                String artistName = textSearchArtist.getText();

                Track track = APIGetRequest.getTrack(trackName, artistName);
                track.showdetails();
            }
            else {
                trackList = APIGetRequest.getTrackList(trackName);

//                for (Track track : trackList) {
//                    track.showdetails();
//                  }

                this.trackList.clear();
                this.trackList.addAll(trackList);

            }
        }
    }
}
