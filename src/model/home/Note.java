package model.home;

/**
 * This class handle the notes that the user write in the system
 * @author Qassem Aburas
 * @Version 3.0
 */
public class Note {
    private int id;
    private String title;
    private String description;

    public Note(){}

    /**
     * constructor the takes title and description and id as parameters
     * when creating a note (an object of notes)
     * @param title
     * @param description
     * @param id
     */
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
