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
        String playlistName = MyApp.instance.showInputDialog("Playlists", "New Playlist", "Name:");
        if (playlistName != null) {
            Database.createPlaylist(playlistName);
            MyApp.loadedPlaylist = Database.readPlaylist(playlistName);
            MyApp.instance.showView("PlaylistView");
        }
    }
}
