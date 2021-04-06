package controller;

import com.google.firebase.database.DatabaseReference;
import model.order.Order;
import model.order.OrderItem;
import model.order.OrderStatus;
import view.OrderPanel;

/**
 * Panel used to keep track of order history and current orders.
 *
 * @Author Hazem Elkhalil
 */

public class OrderController {
    private Controller controller;
    private OrderPanel panel;
    private Order currentOrder;

    /**
     * @param controller which controller to use
     */
    public OrderController(Controller controller) {
        this.controller = controller;
        this.currentOrder = new Order();
        controller.getDailyEvent().addAction(this::saveCurrentOrder);
        addOrderToHistory(currentOrder);
    }

    /**
     * Saves current order to firebase and creates a new order.
     */
    public void saveCurrentOrder() {
        DatabaseReference ref = Controller.getDatabaseReference().child("orders");

        ref.child(currentOrder.getId() + "").setValueAsync(currentOrder);
        currentOrder.setStatus(OrderStatus.ORDERED);
        currentOrder = new Order();
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
    }

    /**
     * @param order
     */
    public void orderHasArrived(Order order) {
        order.setStatus(OrderStatus.DELIVERED);
    }

    public void cancelOrder(Order order) {
        orderPreview(null);
        this.panel.getOrderHistoryList().remove(order);
        Order[] val = new Order[this.panel.getOrderHistoryList().size()];
        this.panel.getOrderHistoryList().toArray(val);
        this.panel.getOrderHistoryJList().setListData(val);
        orderPreview(null);

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
}
