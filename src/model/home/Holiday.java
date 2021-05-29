package model.home;

import com.google.firebase.database.Exclude;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class handles the holidays so the user can add the holiday with the specific date.
 *
 * @author Qassem Aburas
 * @Version 3.0
 */

public class Holiday {
    private String name;
    private Date date;
    private int id;

    public Holiday() {
    }

    /**
     * constructor that take the name of the holiday and the date and id as parameters when
     * the user adds a new holiday
     *
     * @param name the name of the holiday
     * @param date the date of the holiday
     * @param id   for a specific holiday
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

    @Exclude
    public Date realDate() {
        return date;
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return dateFormat.format(date);
    }

    public void setDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

        try {
            this.date = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
