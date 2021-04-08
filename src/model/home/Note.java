package model.home;

/**
 * @author Qassem Aburas
 * @version 1.0
 */
public class Note {
    private int id;
    private String title;
    private String description;

    public Note(){}
    public Note(String title, String description, int id) {
        this.title = title;
        this.description = description;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return title;
    }

}
