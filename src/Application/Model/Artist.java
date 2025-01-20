package Application.Model;

public class Artist {

    private int id;
    private String name;
    private String slug;

    public Artist(int id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    public Artist(String slug, String name) {
        this.slug = slug;
        this.name = name;
    }

    public Artist() {
    }

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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
