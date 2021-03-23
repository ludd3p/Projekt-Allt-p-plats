package Model;

/**
 * Class used in Recipe to hold the ingredient and amount to be used.
 * @Author Ludvig Wedin Pettersson
 */
public class RecipeIngredient {
    private Ingredient ingredient;
    private double amount;

    public RecipeIngredient(Ingredient ingredient, double amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
