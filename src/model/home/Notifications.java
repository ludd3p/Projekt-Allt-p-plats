package model.home;


import javax.management.*;

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

    @Override
    public String toString() {
        return "Notifications: " + title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}