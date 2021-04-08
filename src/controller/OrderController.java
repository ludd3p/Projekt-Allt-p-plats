package controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import model.order.Order;
import model.order.OrderItem;
import model.order.OrderStatus;
import view.OrderPanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Panel used to keep track of order history and current orders.
 *
 * @Author Hazem Elkhalil
 */

public class OrderController {
    private Controller controller;
    private Order currentOrder;
    private OrderPanel panel;
    private Order currentPreview;
    private List<Order> orderHistoryList;


    /**
     * @param controller which controller to use
     */
    public OrderController(Controller controller) {
        this.controller = controller;
        this.orderHistoryList = new ArrayList<>();
        this.currentOrder = new Order();
        getOrdersFromFirebase();
    }

    public void setup(OrderPanel panel) {
        this.panel = panel;
        controller.getDailyEvent().addAction(this::saveCurrentOrder);
        addOrderToHistory(currentOrder);
    }

    /**
     * Saves current order to firebase and creates a new order.
     */
    public void saveCurrentOrder() {
        saveOrderToFirebase(currentOrder);
        currentOrder.setStatus(OrderStatus.ORDERED);
        currentOrder = new Order();
    }

    public void saveOrderToFirebase(Order order) {
        DatabaseReference ref = Controller.getDatabaseReference().child("orders");
        ref.child(order.getId() + "").setValueAsync(currentOrder);
    }

    /**
     * adds order to history.
     *
     * @param order which order to add
     */
    public void addOrderToHistory(Order order) {
        this.panel.getOrderHistoryList().add(order);
        Order[] val = new Order[this.panel.getOrderHistoryList().size()];
        this.panel.getOrderHistoryList().toArray(val);
        this.panel.getOrderHistoryJList().setListData(val);
    }

    /**
     * previews and order
     *
     * @param order Which order to preview
     */
    public void orderPreview(Order order) {
        if (order == null)
            order = new Order();
        OrderItem[] items = new OrderItem[order.getOrderItems().size()];
        order.getOrderItems().toArray(items);
        this.panel.getCurrentOrderList().setListData(items);
        this.currentPreview = order;
    }

    /**
     * @param order
     */
    public void orderHasArrived(Order order) {
        order.setStatus(OrderStatus.DELIVERED);
    }

    public void removeOrder(Order order) {
        orderPreview(null);
        this.panel.getOrderHistoryList().remove(order);
        Order[] val = new Order[this.panel.getOrderHistoryList().size()];
        this.panel.getOrderHistoryList().toArray(val);
        this.panel.getOrderHistoryJList().setListData(val);
        orderPreview(null);
        Controller.databaseReference.child("orders").child(order.getId() + "").setValueAsync(null);

    }

    public void addOrderItemToCurrentOrder(OrderItem item) {
        for (OrderItem orderItem : currentOrder.getOrderItems()) {
            if (orderItem.getIngredient() == item.getIngredient()) {
                orderItem.setQuantity(orderItem.getQuantity() + item.getQuantity());
                orderPreview(currentOrder);
                saveOrderToFirebase(currentOrder);
                return;
            }
        }
        currentOrder.getOrderItems().add(item);
        orderPreview(currentOrder);
        saveOrderToFirebase(currentOrder);
    }

    public void removeOrderItemFromCurrentOrder(OrderItem item) {
        currentOrder.getOrderItems().remove(item);
        orderPreview(currentOrder);
        saveOrderToFirebase(currentOrder);
    }

    public void getOrdersFromFirebase() {

        Controller.getDatabaseReference().child("orders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, Object>> ingredientMap = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();

                for (Map.Entry<String, HashMap<String, Object>> stringHashMapEntry : ingredientMap.entrySet()) {
                    Map.Entry mapElement = stringHashMapEntry;
                    Order order = (dataSnapshot.child((String) mapElement.getKey()).getValue(Order.class));
                    orderHistoryList.add(order);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toString());
            }
        });
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

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public OrderPanel getPanel() {
        return panel;
    }

    public Order getCurrentPreview() {
        return currentPreview;
    }

    public void setCurrentPreview(Order currentPreview) {
        this.currentPreview = currentPreview;
    }

    public List<Order> getOrderHistoryList() {
        return orderHistoryList;
    }

    public void setOrderHistoryList(List<Order> orderHistoryList) {
        this.orderHistoryList = orderHistoryList;
    }
}
