package model.order;

/**
 * An object that represents an item in a order list.
 *
 * @Author Hazem Elkhalil
 */

import model.ingredient.Ingredient;

public class OrderItem {
    private Ingredient ingredient;
    private int quantity;

    /**
     * @param ingredient which ingridient the item represents
     * @param quantity   how many/much of the item
     */
    public OrderItem(Ingredient ingredient, int quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return ingredient + " - " + quantity;
    }
}
