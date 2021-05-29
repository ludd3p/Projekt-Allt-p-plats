package model.order;

/**
 * Panel used to keep track of order history and current orders.
 *
 * @Author Hazem Elkhalil
 * @Version 3.0
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

    public OrderItem() {
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return ingredient.getName() + " - " + quantity + ingredient.getUnit().getPrefix();
    }


}
