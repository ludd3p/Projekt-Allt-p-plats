package controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import model.order.OrderItem;
import model.order.SupplierOrder;
import model.supplier.Supplier;
import view.OrderPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Panel used to keep track of order history and current orders.
 *
 * @Author Hazem Elkhalil
 */

public class OrderController {
    private Controller controller;
    private SupplierOrder currentSelectedSupplierOrder;
    private OrderPanel panel;
    private List<SupplierOrder> supplierOrderList;


    /**
     * @param controller which controller to use
     */
    public OrderController(Controller controller) {
        this.controller = controller;
        this.supplierOrderList = new ArrayList<>();


    }

    public void setup(OrderPanel panel) {
        this.panel = panel;
        getSupplierOrders();

    }


    public void saveOrderToFirebase(SupplierOrder supplierOrder) {
        DatabaseReference ref = Controller.getDatabaseReference().child("orders");
        ref.child(supplierOrder.getSupplier().getName()).setValueAsync(currentSelectedSupplierOrder);
    }

    /**
     * adds supplier to the list
     *
     * @param supplierOrder which order to add
     */
    public void addSupplierToList(SupplierOrder supplierOrder) {
        this.panel.getOrderHistoryList().add(supplierOrder);
        updateSupplierList();
    }

    public void updateSupplierList() {
        this.panel.getSupplierJList().setListData(supplierOrderList.toArray(new SupplierOrder[0]));
    }

    /**
     * previews and order
     *
     * @param supplierOrder Which order to preview
     */
    public void previewSupplierOrder(SupplierOrder supplierOrder) {
        if (supplierOrder == null) {
            getPanel().getCurrentSupplier().setListData(new OrderItem[0]);
            return;
        }
        this.currentSelectedSupplierOrder = supplierOrder;
        getPanel().getCurrentSupplier().setListData(supplierOrder.getOrderItems().toArray(new OrderItem[0]));
    }

    /**
     * @param supplierOrder
     */
    public void orderHasArrived(SupplierOrder supplierOrder) {
        //TODO måste fixa
    }

    public void removeOrder() {
        currentSelectedSupplierOrder.getOrderItems().clear();
        previewSupplierOrder(currentSelectedSupplierOrder);
    }

    public void addOrderItemToSelectedOrder(OrderItem item) {
        for (OrderItem orderItem : currentSelectedSupplierOrder.getOrderItems()) {
            if (orderItem.getIngredient() == item.getIngredient()) {
                orderItem.setQuantity(orderItem.getQuantity() + item.getQuantity());
                previewSupplierOrder(currentSelectedSupplierOrder);
                saveOrderToFirebase(currentSelectedSupplierOrder);
                return;
            }
        }
        currentSelectedSupplierOrder.getOrderItems().add(item);
        previewSupplierOrder(currentSelectedSupplierOrder);
        saveOrderToFirebase(currentSelectedSupplierOrder);
    }

    public void removeOrderItemFromCurrentOrder(OrderItem item) {
        currentSelectedSupplierOrder.getOrderItems().remove(item);
        previewSupplierOrder(currentSelectedSupplierOrder);
        saveOrderToFirebase(currentSelectedSupplierOrder);
    }

    public void getSupplierOrders() {
        for (Supplier supplier : controller.getSupplierController().getSupplierList()) {
            supplierOrderList.add(new SupplierOrder(supplier));
        }
        Controller.getDatabaseReference().child("orders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    SupplierOrder newOrder = d.getValue(SupplierOrder.class);
                    getSupplierOrder(newOrder.getSupplier().getName()).setOrderItems(newOrder.getOrderItems());
                }
                //updateSupplierList();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toString());
            }
        });
        //updateSupplierList();

    }

    public SupplierOrder getSupplierOrder(String name) {
        System.out.println(name);

        for (SupplierOrder supplierOrder : supplierOrderList) {
            if (supplierOrder.getSupplier().getName().equals(name))
                return supplierOrder;
        }
        SupplierOrder supplierOrder = new SupplierOrder(controller.getSupplierController().getSupplierFromName(name));
        supplierOrderList.add(supplierOrder);
        return supplierOrder;
    }

    /**
     * returns controller
     *
     * @return controller
     */

    public Controller getController() {
        return controller;
    }

    /**
     * Replaces controller
     *
     * @param controller new controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * replaces the panel
     *
     * @param panel new panel
     */
    public void setPanel(OrderPanel panel) {
        this.panel = panel;
    }

    public SupplierOrder getCurrentOrder() {
        return currentSelectedSupplierOrder;
    }

    public void setCurrentOrder(SupplierOrder currentSupplierOrder) {
        this.currentSelectedSupplierOrder = currentSupplierOrder;
        panel.getCurrentSupplier().setListData(currentSupplierOrder.getOrderItems().toArray(new OrderItem[0]));

    }

    public OrderPanel getPanel() {
        return panel;
    }

    public List<SupplierOrder> getOrderHistoryList() {
        return supplierOrderList;
    }

    public void setOrderHistoryList(List<SupplierOrder> supplierOrderHistoryList) {
        this.supplierOrderList = supplierOrderHistoryList;
    }

    public SupplierOrder getCurrentSelectedSupplierOrder() {
        return currentSelectedSupplierOrder;
    }

    public void setCurrentSelectedSupplierOrder(SupplierOrder currentSelectedSupplierOrder) {
        this.currentSelectedSupplierOrder = currentSelectedSupplierOrder;
    }

    public List<SupplierOrder> getSupplierOrderList() {
        return supplierOrderList;
    }

    public void setSupplierOrderList(List<SupplierOrder> supplierOrderList) {
        this.supplierOrderList = supplierOrderList;
    }

}
