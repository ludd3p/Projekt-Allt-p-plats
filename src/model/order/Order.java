package model.order;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

// När något tar slut i lagret läggs den till i en order.
// Slutet av dagen skapas en order av allt som behövs köpas in.

public class Order {
    private Set<OrderItem> orderItems;
    private long id;
    private OrderStatus status;

    public Order() {
        this.orderItems = new HashSet<>();
        this.id = System.currentTimeMillis();
        this.status = OrderStatus.PENDING;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order: " + new Date(id) + " items: " + orderItems.size();
    }
}
