package lyapkoandy13.gsonger;

/**
 * Created by Andrew on 01.09.2017.
 */

public class Song {
    private String author;
    private String artist;

    public Song() {
    }

    public Song(String author, String artist, String id, String name, String text) {
        this.author = author;
        this.artist = artist;
        this.id = id;
        this.name = name;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String name;
    private String text;

}
