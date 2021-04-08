package model.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * An object representing an order.
 *
 * @Author Hazem Elkhalil
 */


public class Order {
    private List<OrderItem> orderItems;
    private long id;
    private OrderStatus status;

    /**
     * Initiates order to the basic values
     */
    public Order() {
        this.orderItems = new ArrayList<>();
        this.id = System.currentTimeMillis();
        this.status = OrderStatus.PENDING;
    }


    /**
     * @return all items in the order
     */
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     * @param orderItems replace the orderitems
     */
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    /**
     * @return gets the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id replaces the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the status
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * @param status sets the status
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * Overrides the toString() function
     */
    @Override
    public String toString() {
        return new Date(id) + " | " + orderItems.size() + " | " + status.name().toLowerCase();
    }


}
