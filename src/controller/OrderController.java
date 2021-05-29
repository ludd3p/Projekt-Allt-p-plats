package controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import model.ingredient.Ingredient;
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
 * @Version 3.0
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
        ref.child(supplierOrder.getSupplier().getName()).setValueAsync(supplierOrder);
    }

    /**
     * adds supplier to the list
     *
     * @param supplierOrder which order to add
     */
    public void addSupplierToList(SupplierOrder supplierOrder) {
        this.panel.getController().getOrderHistoryList().add(supplierOrder);
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
     * Refill the storage with items in the order
     *
     * @param supplierOrder
     */
    public void orderHasArrived(SupplierOrder supplierOrder) {
        for (OrderItem orderItem : supplierOrder.getOrderItems()) {
            controller.getStorageController().updateQuantityOfIngredient(orderItem.getIngredient(), orderItem.getQuantity());
        }
        supplierOrder.getOrderItems().clear();
        updateSupplierList();
        saveOrderToFirebase(supplierOrder);
        previewSupplierOrder(supplierOrder);
    }

    /**
     * Clears the order items in the order
     */
    public void removeOrder() {
        currentSelectedSupplierOrder.getOrderItems().clear();
        saveOrderToFirebase(currentSelectedSupplierOrder);
        previewSupplierOrder(currentSelectedSupplierOrder);
    }

    /**
     * Add item to the selected order
     *
     * @param item The item to add
     */
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

    /**
     * Add item to the selected order
     *
     * @param ingredient The ingredient to add
     * @param quantity   The quanittiy to add
     */
    public void addOrderItemToSupplierOrder(Ingredient ingredient, int quantity) {
        if (quantity <= 0)
            quantity = (int) ingredient.getRecommendedAmount();
        getSupplierOrder(ingredient.getSupplier().getName()).getOrderItems().add(new OrderItem(ingredient, quantity));
        saveOrderToFirebase(getSupplierOrder(ingredient.getSupplier().getName()));
        updateSupplierList();
    }

    /**
     * Removes item from the selected order
     *
     * @param item the item to remove
     */
    public void removeOrderItemFromCurrentOrder(OrderItem item) {
        currentSelectedSupplierOrder.getOrderItems().remove(item);
        previewSupplierOrder(currentSelectedSupplierOrder);
        saveOrderToFirebase(currentSelectedSupplierOrder);
    }

    /**
     * Returns all the supplier orders
     */
    public void getSupplierOrders() {
        for (Supplier supplier : controller.getSupplierController().getSupplierList()) {
            supplierOrderList.add(new SupplierOrder(supplier));
        }
        Controller.getDatabaseReference().child("orders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    SupplierOrder newOrder = d.getValue(SupplierOrder.class);

                    SupplierOrder supplierOrder = getSupplierOrder(newOrder.getSupplier().getName());
                    if (supplierOrder != null)
                        supplierOrder.setOrderItems(newOrder.getOrderItems());
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

    /**
     * @param name The name to locate
     * @return supplier based by name ( returnes null if none found)
     */
    public SupplierOrder getSupplierOrder(String name) {
        for (SupplierOrder supplierOrder : supplierOrderList) {
            if (supplierOrder.getSupplier().getName().equals(name))
                return supplierOrder;
        }

        return null;
    }


    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

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

    public void removeSupplierOrder(Supplier supplier) {
        SupplierOrder supord = this.getSupplierOrder(supplier.getName());
        if (supord == null)
            return;

        DatabaseReference ref = Controller.getDatabaseReference().child("orders");

        ref.child(supord.getSupplier().getName()).setValueAsync(null);

        supplierOrderList.remove(supord);
        updateSupplierList();
    }
}
