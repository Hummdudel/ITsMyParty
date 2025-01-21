package Application.Model;

public class Playlist {

    // Attributes

    private int id;
    private String name;


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

}
