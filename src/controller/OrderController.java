package controller;

import com.google.firebase.database.DatabaseReference;
import model.order.Order;
import model.order.OrderItem;
import model.order.OrderStatus;
import view.OrderPanel;

public class OrderController {
    private Controller controller;
    private OrderPanel panel;
    private Order currentOrder;

    public OrderController(Controller controller) {
        this.controller = controller;
        this.currentOrder = new Order();
        controller.getDailyEvent().addAction(this::saveCurrentOrder);
        //addOrderToHistory(currentOrder);

    }

    public void saveCurrentOrder() {
        DatabaseReference ref = Controller.getDatabaseReference().child("orders");

        ref.child(currentOrder.getId() + "").setValueAsync(currentOrder);
        currentOrder.setStatus(OrderStatus.ORDERED);
        currentOrder = new Order();
    }


    public void addOrderToHistory(Order order) {
        this.panel.getOrderHistoryList().add(order);
        Order[] val = new Order[this.panel.getOrderHistoryList().size()];
        this.panel.getOrderHistoryList().toArray(val);
        this.panel.getOrderHistoryJList().setListData(val);
    }

    public void orderPreview(Order order) {
        if (order == null)
            order = new Order();
        OrderItem[] items = new OrderItem[order.getOrderItems().size()];
        order.getOrderItems().toArray(items);
        this.panel.getCurrentOrderList().setListData(items);
    }

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

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setPanel(OrderPanel panel) {
        this.panel = panel;
    }
}
