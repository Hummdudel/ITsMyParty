package Application.Controller;

import Application.Model.Database;
import Application.MyApp;
import javafx.event.ActionEvent;

public class MainSceneController {

    public void onMenuSearchClick(ActionEvent actionEvent) {
        MyApp.instance.showView("SearchView");
    }

    public void onMenuPlaylistsClick(ActionEvent actionEvent) {
        MyApp.instance.showView("PlaylistView");
    }

    public void onMenuNewPlaylistClick(ActionEvent actionEvent) {
        PlaylistController.createNewPlaylist();
    }
}
