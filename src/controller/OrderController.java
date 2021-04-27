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

/**
 * Panel used to keep track of order history and current orders.
 *
 * @Author Hazem Elkhalil
 */

public class OrderController {
    private Controller controller;
    private SupplierOrder currentSelectedSupplierOrder;
    private OrderPanel panel;
    private List<SupplierOrder> supplierOrderHistoryList;


    /**
     * @param controller which controller to use
     */
    public OrderController(Controller controller) {
        this.controller = controller;
        this.supplierOrderHistoryList = new ArrayList<>();
        getSupplierOrders();

    }

    public void setup(OrderPanel panel) {
        this.panel = panel;
        for (Supplier supplier : controller.getSupplierController().getSupplierList()) {
            supplierOrderHistoryList.add(new SupplierOrder(supplier));

        }
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
        this.panel.getSupplierJList().setListData(supplierOrderHistoryList.toArray(new SupplierOrder[0]));
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
        System.out.println("HERE");
        Controller.getDatabaseReference().child("orders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("NOW");

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    SupplierOrder supplierOrder = d.getValue(SupplierOrder.class);
                    System.out.println(supplierOrder);

                    getSupplierOrder(supplierOrder.getSupplier().getName()).setOrderItems(supplierOrder.getOrderItems());

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toString());
            }
        });
    }

    public SupplierOrder getSupplierOrder(String name) {
        for (SupplierOrder supplierOrder : supplierOrderHistoryList) {
            if (supplierOrder.getSupplier().getName().equals(name))
                return supplierOrder;
        }

        SupplierOrder supplierOrder = new SupplierOrder(controller.getSupplierController().getSupplierFromName(name));
        supplierOrderHistoryList.add(supplierOrder);
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
        return supplierOrderHistoryList;
    }

    public void setOrderHistoryList(List<SupplierOrder> supplierOrderHistoryList) {
        this.supplierOrderHistoryList = supplierOrderHistoryList;
    }

    public SupplierOrder getCurrentSelectedSupplierOrder() {
        return currentSelectedSupplierOrder;
    }

    public void setCurrentSelectedSupplierOrder(SupplierOrder currentSelectedSupplierOrder) {
        this.currentSelectedSupplierOrder = currentSelectedSupplierOrder;
    }

    public List<SupplierOrder> getSupplierOrderHistoryList() {
        return supplierOrderHistoryList;
    }

    public void setSupplierOrderHistoryList(List<SupplierOrder> supplierOrderHistoryList) {
        this.supplierOrderHistoryList = supplierOrderHistoryList;
    }

}
