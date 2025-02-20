package Application.Model;

public class Track {

    // Attributes

    private int id;
    private String name;
    private String artist;
    private String album;
    private String duration;
    private int priority;
    private int seconds;


    // Constructor

    public Track(int id, String name, String artist, String duration) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.duration = duration;
        seconds = durationToSeconds(duration);
    }

    public Track(int id, String name, String artist, String album, String duration) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        seconds = durationToSeconds(duration);
    }

    public Track(String name, String artist, String duration) {
        this.name = name;
        this.artist = artist;
        this.duration = duration;
        seconds = durationToSeconds(duration);
    }

    public Track(String name, String artist, String album, String duration) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        seconds = durationToSeconds(duration);
    }

    public Track(String name, String artist, String duration, int priority) {
        this.name = name;
        this.artist = artist;
        this.duration = duration;
        this.priority = priority;
        seconds = durationToSeconds(duration);
    }

    public Track() {
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }


    // Own methods

    public int durationToSeconds(String duration) {
        String[] parts = duration.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }

    public void showdetails() {
        System.out.println("Name: " + name);
        System.out.println("Artist: " + artist);
        System.out.println("Duration: " + duration);
        System.out.println("Seconds: " + seconds);
    }

}
