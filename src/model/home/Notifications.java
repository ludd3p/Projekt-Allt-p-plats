package model.home;

import java.util.UUID;

/**
 * This class handle the notifications that should be shown in the home page of the system.
 *
 * @author Qassem Aburas
 * @Version 3.0
 */

public class Notifications {

    private UUID id;
    private String title;
    private String description;

    public Notifications() {
    }

    public Notifications(String title, String description) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id.toString();
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    @Override
    public String toString() {
        return "<html><div style='text-align: center;'>" + title + "<br/>" + description + "</div></html>";
    }
}