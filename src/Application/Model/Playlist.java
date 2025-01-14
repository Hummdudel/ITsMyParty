package Application.Model;

import java.util.ArrayList;

public class Playlist {

    // Attributes

    private int id;
    private String name;
    private String duration;
    private int seconds;

    private ArrayList<Track> trackList = new ArrayList<>();


    // Constructor

    public Playlist(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Playlist() {
    }


    // Getter + Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public ArrayList<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(ArrayList<Track> trackList) {
        this.trackList = trackList;
    }


    // Own methods

    public void calculateDuration(ArrayList<Track> trackList) {
        int sumSeconds = 0;
        for (Track track : trackList) {
            sumSeconds += track.getSeconds();
        }
        seconds = sumSeconds;
        duration = secondsToDuration(seconds);
    }

    public String secondsToDuration(int seconds) {
        return String.format("%02d:%02d:%02d", seconds / 360, (seconds % 360) / 60, seconds % 60);
    }


}
