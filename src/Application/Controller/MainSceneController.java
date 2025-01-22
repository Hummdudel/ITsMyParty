package Application.Controller;

import Application.Model.Database;
import Application.Model.FileUtilities;
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

    public void onMenuSavePlaylist(ActionEvent actionEvent) {
        FileUtilities.writeTracksToFile();
    }

    public void onMenuQuit(ActionEvent actionEvent) {
        MyApp.instance.shutdown();
    }

    public void onMenuAbout(ActionEvent actionEvent) {
        String title = "IT’s My Party!";
        String header = "About";
        String content = "Version 1.0\nby Lars Lachmann\nThanks to Mr. Sahin, the best development teacher!\nUsed API: https://musicdb.io/api";
        MyApp.instance.showMessage(title, header, content);
    }
}
