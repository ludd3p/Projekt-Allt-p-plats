package model.home;

import javax.management.*;

/**
 * This class handle the notifications that should be shown in the home page of the system.
 * @author Qassem Aburas
 * @version 1.2
 */

public class Notifications extends NotificationBroadcasterSupport {

    private int id;
    private String title;
    private String description;

    public Notifications() {
    }

    public Notifications(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Notifications: " + title;
    }
}