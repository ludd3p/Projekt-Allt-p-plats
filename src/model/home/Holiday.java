package model.home;

import java.util.Date;

/**
 * @author Qassem Aburas
 * @version 1.0
 */

public class Holiday {
    private String name;
    private Date date;
    private int id;

    public Holiday(){

    }
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
