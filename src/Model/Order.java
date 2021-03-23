package Model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

// När något tar slut i lagret läggs den till i en order.
// Slutet av dagen skapas en order av allt som behövs köpas in.

public class Order {
    private Set<OrderItem> orderItems;
    private UUID id;

    public Order() {
        this.orderItems = new HashSet<>();
        this.id = UUID.randomUUID();
    }


    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Order: " + id + " items: " + orderItems.size();
    }
}
