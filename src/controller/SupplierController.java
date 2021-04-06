package controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import model.supplier.Supplier;
import model.supplier.WeekDays;
import view.SupplierPanel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class SupplierController {

    private Controller controller;
    private SupplierPanel panel;
    private DatabaseReference databaseReference;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private ArrayList<Supplier> supplierList = new ArrayList<>();
    private ArrayList<String> supplierNames = new ArrayList<>();

    public SupplierController(Controller controller, DatabaseReference databaseReference) {
        this.controller = controller;
        this.databaseReference = databaseReference;
        getSuppliersFromDatabase();
    }

    public void registerPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }

    public void addSupplierToDatabase(String name, String address, String city, String countrty, String email, String phonenumber){
        Supplier newSupplier = new Supplier(name, address, city, countrty, email, phonenumber);
        databaseReference.child("Suppliers").child(name).setValueAsync(newSupplier);
    }

    public void getSuppliersFromDatabase(){
        databaseReference.child("Suppliers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                supplierList.clear();
                for (DataSnapshot supplier : dataSnapshot.getChildren()){
                    Supplier newSupplier = supplier.getValue(Supplier.class);
                    supplierList.add(newSupplier);
                }

                pcs.firePropertyChange("Supplier list", null, supplierList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toString());
            }
        });
    }

    public WeekDays[] getWeekDays() {
        return WeekDays.values();
    }

    public void setSupplierPanel(SupplierPanel panel){
        this.panel = panel;
    }
}
