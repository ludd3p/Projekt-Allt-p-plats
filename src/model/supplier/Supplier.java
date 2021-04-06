package model.supplier;

public class Supplier {

    private String name;
    private String address;
    private String city;
    private String countrty;
    private String email;
    private String phonenumber;



    public Supplier(String name, String address, String city, String countrty, String email, String phonenumber) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.countrty = countrty;
        this.email = email;
        this.phonenumber = phonenumber;
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

    public String getCountrty() {
        return countrty;
    }

    public void setCountrty(String countrty) {
        this.countrty = countrty;
    }

    public String toStringName(){
        return name;
    }

    public String toString(){
        return String.format("Supplier: %s \n Address: %s \n City; %s \n Country: %s \n Email: %s \n Phone number: %s", name, address, city, countrty, email, phonenumber);
    }
}
