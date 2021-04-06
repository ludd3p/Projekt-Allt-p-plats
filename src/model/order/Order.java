package model.order;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * An object representing an order.
 *
 * @Author Hazem Elkhalil
 */


public class Order {
    private Set<OrderItem> orderItems;
    private long id;
    private OrderStatus status;

    /**
     * Initiates order to the basic values
     */
    public Order() {
        this.orderItems = new HashSet<>();
        this.id = System.currentTimeMillis();
        this.status = OrderStatus.PENDING;
    }

    /**
     *
     * @return all items in the order
     */
    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     *
     * @param orderItems replace the orderitems
     */
    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    /**
     *
     * @return gets the id
     */
    public long getId() {
        return id;
    }

    /**
     *
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
        return "Order: " + new Date(id) + " items: " + orderItems.size();
    }
}
