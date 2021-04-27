package model.recipe;

import model.ingredient.Ingredient;

/**
 * Class used in Recipe to hold the ingredient and amount to be used.
 * @Author Ludvig Wedin Pettersson
 * @Version 1.0
 */
public class RecipeIngredient {
    private Ingredient ingredient;
    private double amount;

    /**
     * Default constructor used when recreating a RecipeIngredient from firebase.
     */
    public RecipeIngredient(){}

    /**
     * Constructor used when creating a new RecipeIngredient.
     * @param ingredient Ingredient object.
     * @param amount double representing the amount of Ingredient used in Recipe.
     */
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

    /**
     * toString used when multiplier for recipe is used in GUI.
     * @param multi int incoming multiplier.
     * @return String with multiplied amount.
     */
    public String toString2(int multi){
        String s = String.format("%s %.2f %s", ingredient.getName(), (amount*multi), ingredient.getUnit());
        return s;
    }

    /**
     * toString returning the ingredients name, amount to be used, and which unit type.
     * @return String.
     */
    public String toString(){
        String s = String.format("%s %.2f %s", ingredient.getName(), amount, ingredient.getUnit());
        return s;
    }


}
