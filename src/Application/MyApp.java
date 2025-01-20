package Application;

import Application.Model.APIGetRequest;
import Application.Model.Playlist;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

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

    public void showWarning(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}