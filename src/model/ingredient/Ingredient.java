package model.ingredient;

// Använd klassen när vi lägger till ingridienser i recepten.

import com.google.firebase.database.Exclude;
import controller.StorageController;
import model.Unit;

import java.util.ArrayList;


public class Ingredient {

    private String key;
    private String name;
    private double cost;
    private double currentAmount;
    private double criticalAmount;
    private double recommendedAmount;
    private Unit unit;

    public Ingredient() {
    }

    /**
     * Constructor.
     *
     * @param name              of ingredient
     * @param cost              of ingredient. cost/unit
     * @param currentAmount     of ingredient in storage
     * @param criticalAmount
     * @param recommendedAmount of ingredient in storage
     * @param unit
     */
    public Ingredient(String name, double cost, double currentAmount, double criticalAmount, double recommendedAmount, Unit unit) {
        this.name = name;
        this.cost = cost;
        this.currentAmount = currentAmount;
        this.criticalAmount = criticalAmount;
        this.recommendedAmount = recommendedAmount;
        this.unit = unit;
    }

    public Ingredient(String name, Unit unit) {
        this.name = name;
        this.unit = unit;
    }

    //<editor-fold desc = "Setters and getters">
    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public double getCriticalAmount() {
        return criticalAmount;
    }

    public void setCriticalAmount(double criticalAmount) {
        this.criticalAmount = criticalAmount;
    }

    public double getRecommendedAmount() {
        return recommendedAmount;
    }

    public void setRecommendedAmount(double recommendedAmount) {
        this.recommendedAmount = recommendedAmount;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    //</editor-fold>

    /**
     * @return a string with info about the ingredient.
     */
    public String toString() {
        return String.format("%s %s %s %s %s %s %s",
                "<html>",
                "Produkt: " + name,
                "<br>Kostnad: " + cost + " " + "sek/" + unit.getPrefix(),
                "<br>Nuvarande mängd: " + currentAmount + " " + unit.getPrefix(),
                "Minsta mängd: " + criticalAmount + " " + unit.getPrefix(),
                "Rekommenderad mängd: " + recommendedAmount + " " + unit.getPrefix(),
                "<!--" + key + "--></html>");
    }

    //<editor-fold desc = "Static methods">


    /**
     * Adds an ingredient to the list of ingredients.
     *
     * @param ingredient Ingredient-object.
     */
    public static void addIngredientToList(Ingredient ingredient) {
        if (!checkIfIngredientExists(ingredient.getKey())) {
            StorageController.allIngredients.add(ingredient);
        }
    }

    /**
     * Removes an ingredient form the list.
     *
     * @param key of the ingredient to remove.
     */
    public static void removeIngredientFromList(String key) {
        StorageController.allIngredients.removeIf(ingredient -> ingredient.getKey().equals(key));
    }

    /**
     * Updates an ingredient in the ingredientList
     *
     * @param key
     * @param ingredientNewValues Ingredient-object with updated values.
     * @return
     */
    public static Ingredient updateIngredient(String key, Ingredient ingredientNewValues) {
        Ingredient updatedIngredient = null;
        for (Ingredient ingredient : StorageController.allIngredients) {
            if (ingredient.getKey().equals(key)) {
                ingredient.setName(ingredientNewValues.getName());
                ingredient.setCost(ingredientNewValues.getCost());
                ingredient.setCurrentAmount(ingredientNewValues.getCurrentAmount());
                ingredient.setCriticalAmount(ingredientNewValues.getCriticalAmount());
                ingredient.setRecommendedAmount(ingredientNewValues.getRecommendedAmount());
                ingredient.setUnit(ingredientNewValues.getUnit());
                updatedIngredient = ingredient;
            }
        }

        return updatedIngredient;
    }

    /**
     * Adds a specified quantity to the current quantity of an object.
     *
     * @param key           of ingredient to change.
     * @param quantityToAdd
     * @return the updated Ingredient-object.
     */
    public static Ingredient updateIngredientCurrentQuantity(String key, double quantityToAdd) {
        Ingredient updatedIngredient = null;

        for (Ingredient ingredient : StorageController.allIngredients) {
            if (ingredient.getKey().equals(key)) {
                ingredient.setCurrentAmount(ingredient.getCurrentAmount() + quantityToAdd);
                updatedIngredient = ingredient;
            }
        }

        return updatedIngredient;
    }

    /**
     * Checks if an ingredient already exists in the list.
     *
     * @param key
     * @return
     */
    public static boolean checkIfIngredientExists(String key) {
        for (Ingredient ingredient : StorageController.allIngredients) {
            System.out.println(ingredient.toString());
        }

        for (Ingredient ingredient : StorageController.allIngredients) {
            if (ingredient.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the amount of any of the ingredients are below the critical amount.
     *
     * @return an ArrayList containing all ingredients below the critical amount.
     */
    public static ArrayList<Ingredient> checkIfIngredientsBelowCriticalAmount() {
        ArrayList<Ingredient> ingredientsBelowCriticalAmount = new ArrayList<>();

        for (Ingredient ingredient : StorageController.allIngredients) {
            if (ingredient.getCurrentAmount() < ingredient.getCriticalAmount()) {
                ingredientsBelowCriticalAmount.add(ingredient);
            }
        }

        return ingredientsBelowCriticalAmount;
    }

    /**
     * Returns an ArrayList with strings for the storage panel.
     *
     * @return ArrayList containing strings with info about the ingredients.
     */
    public static ArrayList<String> getIngredientStringsForStorage() {
        ArrayList<String> stringsForStorage = new ArrayList<>();

        for (Ingredient ingredient : StorageController.allIngredients) {
            stringsForStorage.add(ingredient.toString());
        }

        return stringsForStorage;
    }
    //</editor-fold>
}
