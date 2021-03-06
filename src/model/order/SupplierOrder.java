package model.order;

import model.supplier.Supplier;

import java.util.ArrayList;
import java.util.List;

/**
 * Panel used to keep track of order history and current orders.
 *
 * @Author Hazem Elkhalil
 * @Version 3.0
 */

public class SupplierOrder {
    private List<OrderItem> orderItems;
    private Supplier supplier;

    /**
     * Initiates order to the basic values
     */
    public SupplierOrder(Supplier supplier) {
        this.orderItems = new ArrayList<>();
        this.supplier = supplier;
    }

    public SupplierOrder(List<OrderItem> orderItems, Supplier supplier) {
        this.orderItems = orderItems;
        this.supplier = supplier;
    }

    public SupplierOrder() {
        this.orderItems = new ArrayList<>();

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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return supplier.getName();
    }

}
