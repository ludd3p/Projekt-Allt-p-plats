package controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import model.order.SupplierOrder;
import model.supplier.Supplier;
import model.supplier.WeekDay;
import view.SupplierPanel;

import java.util.ArrayList;

/**
 * Controller class for the Supplier
 *
 * @Author Alex Bergenholtz
 * @Version 3
 */

public class SupplierController {
    private Controller controller;
    private SupplierPanel panel;
    private DatabaseReference databaseReference;
    private ArrayList<Supplier> supplierList;

    /**
     * Class constructor
     *
     * @param controller        main controller object
     * @param databaseReference reference to Firebase database
     */
    public SupplierController(Controller controller, DatabaseReference databaseReference) {
        this.controller = controller;
        this.databaseReference = databaseReference;
        this.supplierList = new ArrayList<>();
        getSuppliersFromDatabase();
    }

    /**
     * Remove supplier from Firebase database
     *
     * @param supplier The supplier chosen to remove
     */
    public void removeSupplier(Supplier supplier) {
        databaseReference.child("Suppliers").child(supplier.getName()).setValueAsync(null);
        supplierList.remove(supplier);
        controller.getOrderController().removeSupplierOrder(supplier);
        updateSupplierList();
    }

    /**
     * Create new supplier
     *
     * @param name        of the supplier
     * @param address     of the supplier
     * @param city        of the supplier
     * @param zip         of the supplier
     * @param country     of the supplier
     * @param email       of the supplier
     * @param phonenumber of the supplier
     * @param day         of delivery from the supplier
     */
    public void createNewSupplier(String name, String address, String city, String zip, String country, String email, String phonenumber, WeekDay day) {
        Supplier newSupplier = new Supplier(name, address, city, zip, country, email, phonenumber, day);
        supplierList.add(newSupplier);
        databaseReference.child("Suppliers").child(name).setValueAsync(newSupplier);
        controller.getOrderController().addSupplierToList(new SupplierOrder(newSupplier));
        controller.getOrderController().updateSupplierList();
        updateSupplierList();
    }

    /**
     * Update the supplier list in SupplierPanel
     */
    public void updateSupplierList() {
        panel.getSupplierJList().setListData(this.supplierList.toArray(new Supplier[0]));
    }

    /**
     * Getting all the suppliers stored in Firebase database
     */
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

    public String[] getSupplierNames() {
        String[] supplierNames = new String[supplierList.size()];
        for (int i = 0; i < supplierNames.length; i++) {
            supplierNames[i] = supplierList.get(i).getName();
        }

        return supplierNames;
    }

    public Supplier getSupplierFromName(String supplierName) {
        for (Supplier supplier : supplierList) {
            if (supplier.getName().equals(supplierName)) {
                return supplier;
            }
        }
        return null;
    }

    public void setSupplierPanel(SupplierPanel panel) {
        this.panel = panel;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public SupplierPanel getPanel() {
        return panel;
    }

    public void setPanel(SupplierPanel panel) {
        this.panel = panel;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void setDatabaseReference(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public ArrayList<Supplier> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(ArrayList<Supplier> supplierList) {
        this.supplierList = supplierList;
    }
}
