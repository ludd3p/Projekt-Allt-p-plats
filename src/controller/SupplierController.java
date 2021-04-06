package controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import model.supplier.Supplier;
import model.supplier.WeekDays;
import view.SupplierPanel;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class SupplierController {

    private Controller controller;
    private Supplier currentSupplier;
    private SupplierPanel panel;
    private DatabaseReference databaseReference;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private ArrayList<Supplier> supplierList = new ArrayList<>();

    public SupplierController(Controller controller, DatabaseReference databaseReference) {
        this.controller = controller;
        this.databaseReference = databaseReference;
        getSuppliersFromDatabase();
    }

    public WeekDays[] getWeekDays() {
        return WeekDays.values();
    }

    public void addSupplierToDatabase(String name, String address, String city, String countrty, String email, String phonenumber){
        currentSupplier = new Supplier(name, address, city, countrty, email, phonenumber);
        //add
    }

    public void getSuppliersFromDatabase(){
        databaseReference.child("Suppliers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                supplierList.clear();
                for (DataSnapshot supplier : dataSnapshot.getChildren()){
                    currentSupplier = supplier.getValue(Supplier.class);
                    supplierList.add(currentSupplier);
                }
                pcs.firePropertyChange("Suppliers", null, supplierList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String getSupplierNames(){
        return currentSupplier.toStringName();
    }

    public String getSupplierInfo(){
        return currentSupplier.toString();
    }
}
