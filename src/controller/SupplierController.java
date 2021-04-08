package controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import model.supplier.Supplier;
import model.supplier.WeekDay;
import view.SupplierPanel;

import java.util.ArrayList;

public class SupplierController {
    private Controller controller;
    private SupplierPanel panel;
    private DatabaseReference databaseReference;
    private ArrayList<Supplier> supplierList;

    public SupplierController(Controller controller, DatabaseReference databaseReference) {
        this.controller = controller;
        this.databaseReference = databaseReference;
        this.supplierList = new ArrayList<>();
        getSuppliersFromDatabase();
    }

    public void setUp(SupplierPanel panel) {
        this.panel = panel;
    }

    private void updateSupplier(Supplier old, Supplier newSup) {
        removeSupplier(old);
        createNewSupplier(newSup);
    }

    public void removeSupplier(Supplier supplier) {
        databaseReference.child("Suppliers").child(supplier.getName()).setValueAsync(null);
        supplierList.remove(supplier);
        updateSupplierList();
    }

    public void createNewSupplier(String name, String address, String city, String countrty, String email, String phonenumber, WeekDay day) {
        Supplier newSupplier = new Supplier(name, address, city, countrty, email, phonenumber, day);
        supplierList.add(newSupplier);
        databaseReference.child("Suppliers").child(name).setValueAsync(newSupplier);
        updateSupplierList();
    }

    public void createNewSupplier(Supplier supplier) {
        supplierList.add(supplier);
        databaseReference.child("Suppliers").child(supplier.getName()).setValueAsync(supplier);
        updateSupplierList();
    }

    public void updateSupplierList() {
        panel.getSupplierJList().setListData(this.supplierList.toArray(new Supplier[0]));
    }

    public void getSuppliersFromDatabase() {
        databaseReference.child("Suppliers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                supplierList.clear();
                for (DataSnapshot supplier : dataSnapshot.getChildren()) {
                    Supplier newSupplier = supplier.getValue(Supplier.class);
                    supplierList.add(newSupplier);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toString());
            }
        });
    }

    public WeekDay[] getWeekDays() {
        return WeekDay.values();
    }

}
