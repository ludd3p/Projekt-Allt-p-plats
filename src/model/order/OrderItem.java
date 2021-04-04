package model.order;

// En orderitem representerar en ingridient och hur mycket av igridienten som behövs köpas in

import model.ingredient.Ingredient;

public class OrderItem {
    private Ingredient ingredient;
    private int quantity;

    public OrderItem(Ingredient ingredient, int quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
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
        return ingredient + " - " + quantity;
    }
}
