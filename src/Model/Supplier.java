package Model;

public class Supplier {

    private String name;
    private String address;
    private String city;
    private String countrty;
    private String email;
    private int phonenumber;

    public Supplier(){

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

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
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
}
