package Application.Model;

import Application.MyApp;

import java.sql.*;

public class Database {

    private static String title = "Database Operations";
    private static String header;
    private static String content;

    // 1. Login Data
    static String url = "jdbc:mysql://localhost:3306/itsmyparty";
    static String user = "root";
    static String password = "";

    static Connection connection = null;
    static CallableStatement callableStatement = null;

    // 2. Connect to database
    public static void connect() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Verbindung erfolgreich hergestellt!");
        }
        catch (SQLException exception) {
            header = "DB Connection";
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);
        }
    }

    // 3. CRUD

    // Create

    public static void createTrack(Track track) {

        String sql = "{CALL Create_Track(?, ?, ?, ?)}";

        try {
            callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, track.getName());
            callableStatement.setString(2, track.getArtist());
            callableStatement.setString(3, track.getSlug());
            callableStatement.setString(4, track.getDuration());


        }
        catch (SQLException exception) {

        }
    }

    public static void createPlaylist(String name) {

        String sql = "{CALL CreatePlaylist(?)}";

        try {
            callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, name);
            callableStatement.execute();

            System.out.println("Playlist " + name + " erfolgreich angelegt!");
        }
        catch (SQLException exception) {
            header = "Create Playlist";
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);
        }

    }



    // 4. Disconnect from Database

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
