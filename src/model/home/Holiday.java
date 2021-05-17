package model.home;

import java.util.Date;

/**
 * This class handles the holidays so the user can add the holiday with the specific date.
 * @author Qassem Aburas
 * @version 1.0
 */

public class Holiday {
    private String name;
    private Date date;
    private int id;

    public Holiday(){
    }

    /**
     * constructor that take the name of the holiday and the date and id as parameters when
     * the user adds a new holiday
     * @param name the name of the holiday
     * @param date the date of the holiday
     * @param id for a specific holiday
     */
    public Holiday(String name, Date date, int id) {
        this.name = name;
        this.date = date;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

}
