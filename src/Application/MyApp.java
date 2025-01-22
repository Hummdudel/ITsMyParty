package Application;

import Application.Model.APIGetRequest;
import Application.Model.Playlist;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class MyApp extends Application {

    public static Playlist loadedPlaylist;

    // Singleton for accessing this class easily from anywhere
    public static MyApp instance;

    private Stage primaryStage;
    private BorderPane borderPane;

    @Override
    public void start(Stage primaryStage) throws Exception {

        instance = this;

        this.primaryStage = primaryStage;

        primaryStage.setTitle("IT’s My Party!");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        primaryStage.setResizable(false);

        setIcon();
        loadMainScene();
        showView("PlaylistView");
        primaryStage.show();
    }

    public void setIcon() {
        this.primaryStage.getIcons().add(new Image("/Images/Ahdesign91-Media-Player-ITunes.512.png"));
    }

    public void loadMainScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL xmlURL = getClass().getResource("/Application/View/MainScene.fxml");
        fxmlLoader.setLocation(xmlURL);

        borderPane = fxmlLoader.load();

        Scene mainScene = new Scene(borderPane);
        primaryStage.setScene(mainScene);
    }

    public void showView(String viewName) {

        String newView = "/Application/View/" + viewName + ".fxml";

        borderPane.getChildren().remove(borderPane.getCenter());

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL xmlURL = getClass().getResource(newView);
        fxmlLoader.setLocation(xmlURL);

        Pane pane = null;
        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        borderPane.setCenter(pane);
    }

    public void shutdown() {
        Platform.exit();
        System.exit(0);
    }

    public void showWarning(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void showMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public String showInputDialog(String title, String header, String content) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);

        // Zeige den Dialog und warte auf die Eingabe
        Optional<String> result = dialog.showAndWait();

        // Überprüfe, ob der OK-Button gedrückt wurde
        if (result.isPresent()) {
            return result.get(); // Rückgabe des eingegebenen Textes
        } else {
            return null; // Der Dialog wurde abgebrochen
        }
    }

    public int showConfirmation(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, title, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            return 1;
        }
        else if (alert.getResult() == ButtonType.NO) {
            return 0;
        }
        else {
            return -1;
        }
    }

    public File showSaveFileDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Datei speichern");

        // Optional: Setze einen Standard-Dateinamen oder ein Verzeichnis
        fileChooser.setInitialFileName("neuePlaylist.txt");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home"), "Desktop"));

        // Optional: Filtere die Dateitypen, die angezeigt werden
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Textdateien (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Zeige den Speichern-Dialog und erhalte die ausgewählte Datei
        File file = fileChooser.showSaveDialog(instance.primaryStage);
        return file;
    }
}