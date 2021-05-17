package model.supplier;

/**
 * Class for the supplier
 *
 * @Author Alex Bergenholtz
 * @Version 1.3
 */

public class Supplier {

    private String name;
    private String address;
    private String city;
    private String zip;
    private String countrty;
    private String email;
    private String phonenumber;
    private WeekDay dayOfDelivery;

    /**
     * Empty constructor
     */
    public Supplier(){}

    /**
     * Constructor with all info to create a complete supplier
     * @param name of supplier
     * @param address of supplier
     * @param city of supplier
     * @param zip of supplier
     * @param country of supplier
     * @param email of supplier
     * @param phonenumber of supplier
     * @param day of delivery from supplier
     */
    public Supplier(String name, String address, String city, String zip, String country, String email, String phonenumber, WeekDay day) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.countrty = country;
        this.email = email;
        this.phonenumber = phonenumber;
        this.dayOfDelivery = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountrty() {
        return countrty;
    }

    public void setCountrty(String countrty) {
        this.countrty = countrty;
    }


    public WeekDay getDayOfDelivery() {
        return dayOfDelivery;
    }

    public void setDayOfDelivery(WeekDay dayOfDelivery) {
        this.dayOfDelivery = dayOfDelivery;
    }

    public String toString() {
        return this.getName();
    }


}
