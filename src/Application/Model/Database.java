package Application.Model;

import Application.MyApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
        }
        catch (SQLException exception) {
            header = "DB Connection";
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);
        }
    }

    // 3. CRUD

    // Create

    public static void createArtist(String name) {
        String sql = "{CALL Create_Artist(?)}";
        connect();

        try {
            callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, name);
            callableStatement.execute();

        }
        catch (SQLException exception) {
            header = "Create Artist";
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);
        }
        finally {
            disconnect();
        }
    }

    public static void createTrack(Track track) {
        String sql = "{CALL Create_Track(?, ?, ?, ?)}";
        connect();

        try {
            callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, track.getName());
            callableStatement.setString(2, track.getArtist());
            callableStatement.setString(3, track.getDuration());
            callableStatement.setInt(4, track.durationToSeconds(track.getDuration()));
            callableStatement.execute();

        }
        catch (SQLException exception) {
            header = "Create Track";
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);
        }
        finally {
            disconnect();
        }
    }

    public static void createPlaylist(String name) {
        String sql = "{CALL Create_Playlist(?)}";
        connect();

        try {
            callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, name);
            callableStatement.execute();

        }
        catch (SQLException exception) {
            header = "Create Playlist";
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);
        }
        finally {
            disconnect();
        }
    }

    public static void createPlaylistEntry(Track track, Playlist playlist) {
        String sql = "{CALL Create_Playlist_Entry(?, ?)}";
        header = "Create Playlist Entry";
        connect();

        try {
            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, track.getId());
            callableStatement.setInt(2, playlist.getId());
            callableStatement.execute();

            content = "Der Track " + track.getName() + " wurde erfolgreich zur Playlist " + playlist.getName() + " hinzugefügt!";
            MyApp.instance.showMessage(title, header, content);

        } catch (SQLException exception) {
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);
        }
        finally {
            disconnect();
        }
    }

    // Read

    public static ObservableList<Playlist> readAllPlaylists() {
        String sql = "{CALL Read_All_Playlists()}";
        connect();

        try {
            callableStatement = connection.prepareCall(sql);
            ResultSet resultSet = callableStatement.executeQuery();

            ObservableList<Playlist> playlistList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                Playlist playlist = new Playlist(id, name);
                playlistList.add(playlist);
            }
            return playlistList;
        }
        catch (SQLException exception) {
            header = "Read All Playlists";
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);
        }
        finally {
            disconnect();
        }
        return null;
    }

    public static ObservableList<Track> readPlaylistTracks(int playlistId) {
        String sql = "{CALL Read_Playlist_Tracks(?)}";
        connect();

        try {
            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, playlistId);
            ResultSet resultSet = callableStatement.executeQuery();

            ObservableList<Track> trackList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                String trackName = resultSet.getString("track");
                String artistName = resultSet.getString("artist");
                String duration = resultSet.getString("duration");
                int priority = resultSet.getInt("priority");

                Track track = new Track(trackName, artistName, duration, priority);
                trackList.add(track);
            }
            return trackList;

        }
        catch (SQLException exception) {
            header = "Read Playlist Tracks";
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);
        }
        finally {
            disconnect();
        }

        return null;
    }

    public static boolean artistExists(String artistName) {
        String sql = "{CALL Check_ArtistExists(?)}";
        connect();

        try {
            callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, artistName);
            ResultSet resultSet = callableStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
            return false;
        }
        catch (SQLException exception) {
            header = "Check Artist Exists";
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);
        }
        finally {
            disconnect();
        }
        return false;
    }

    public static boolean trackExists(String trackName) {
        String sql = "{CALL Check_TrackExists(?)}";
        connect();

        try {
            callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, trackName);
            ResultSet resultSet = callableStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
            return false;
        }
        catch (SQLException exception) {
            header = "Check Track Exists";
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);
        }
        finally {
            disconnect();
        }
        return false;
    }

    public static int readTrackId(String trackName) {
        String sql = "{CALL Read_Track_ID(?)}";
        connect();

        try {
            callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, trackName);
            ResultSet resultSet = callableStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
            return -1;
        }
        catch (SQLException exception) {
            header = "Read Track ID";
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);
        }
        finally {
            disconnect();
        }
        return -1;
    }

    public static Playlist readPlaylist(String playlistName) {
        String sql = "{CALL Read_Playlist(?)}";
        connect();

        try {
            callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, playlistName);
            ResultSet resultSet = callableStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                Playlist playlist = new Playlist(id, playlistName);
                return playlist;
            }
            return null;
        }
        catch (SQLException exception) {
            header = "Read Playlist";
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);
        }
        finally {
            disconnect();
        }
        return null;
    }

    // Update

    public static void updatePriority(int priority, Track track, Playlist playlist) {
        String sql = "{CALL Update_Priority(?, ?, ?)}";
        connect();

        try {
            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, priority);
            callableStatement.setInt(2, track.getId());
            callableStatement.setInt(3, playlist.getId());
            callableStatement.execute();

        }
        catch (SQLException exception) {
            header = "Update Priority";
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);
        }
        finally {
            disconnect();
        }
    }

    // Delete

    public static void deletePlaylistEntry(Track track, Playlist playlist) {
        String sql = "{CALL Delete_Playlist_Entry(?, ?)}";
        header = "Delete Playlist Entry";
        connect();

        try {
            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, track.getId());
            callableStatement.setInt(2, playlist.getId());
            callableStatement.execute();

            content = "Der Track " + track.getName() + " wurde erfolgreich aus der Playlist " + playlist.getName() + " gelöscht!";
            MyApp.instance.showMessage(title, header, content);

        }
        catch (SQLException exception) {
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);
        }
        finally {
            disconnect();
        }
    }

    public static void deletePlaylist(Playlist playlist) {
        String sql = "{CALL Delete_Playlist(?)}";
        header = "Delete Playlist";
        connect();

        try {
            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, playlist.getId());
            callableStatement.execute();

            content = "Die Playlist " + playlist.getName() + " wurde erfolgreich gelöscht!";
            MyApp.instance.showMessage(title, header, content);

        }
        catch (SQLException exception) {
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);
        }
        finally {
            disconnect();
        }
    }


    // 4. Disconnect from Database

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException exception) {
            header = "Disconnect from Database";
            content = exception.getMessage();
            MyApp.instance.showWarning(title, header, content);
        }
    }
}
