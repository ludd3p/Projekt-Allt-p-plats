package model.ingredient;

// Använd klassen när vi lägger till ingridienser i recepten.

import model.supplier.Supplier;

/**
 * Class for  ingredients.
 *
 * @Author Jonathan Engström
 * @Version 3.0
 */

public class Ingredient {
    private String name;
    private double cost;
    private double currentAmount;
    private double criticalAmount;
    private double recommendedAmount;
    private Unit unit;
    private Supplier supplier;

    public Ingredient() {
    }

    /**
     * Constructor.
     *
     * @param name              of ingredient
     * @param cost              of ingredient. cost/unit
     * @param currentAmount     of ingredient in storage
     * @param criticalAmount    of ingredient for warning
     * @param recommendedAmount of ingredient in storage
     * @param unit              of the ingredient
     * @param supplier          of the ingredient
     */
    public Ingredient(String name, double cost, double currentAmount, double criticalAmount, double recommendedAmount, Unit unit, Supplier supplier) {
        this.name = name;
        this.cost = cost;
        this.currentAmount = currentAmount;
        this.criticalAmount = criticalAmount;
        this.recommendedAmount = recommendedAmount;
        this.unit = unit;
        this.supplier = supplier;
    }

    public Ingredient(String name, Unit unit) {
        this.name = name;
        this.unit = unit;
    }

    /**
     * Updates the values of the object with new values.
     *
     * @param ingredientNewValues
     */
    public void updateValues(Ingredient ingredientNewValues) {
        name = ingredientNewValues.getName();
        cost = ingredientNewValues.getCost();
        currentAmount = ingredientNewValues.getCurrentAmount();
        criticalAmount = ingredientNewValues.getCriticalAmount();
        recommendedAmount = ingredientNewValues.getRecommendedAmount();
        unit = ingredientNewValues.getUnit();
        supplier = ingredientNewValues.getSupplier();
    }

    //<editor-fold desc = "Setters and getters">

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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
    //</editor-fold>

    /**
     * @return a string with info about the ingredient.
     */
    public String toString() {
        return String.format("%s %s %s %s %s %s %s %s",
                "<html>",
                "Produkt: " + name,
                "<br>Kostnad: " + cost + " " + "sek/" + unit.getPrefix(),
                "<br>Leverantör: " + supplier.getName(),
                "<br>Nuvarande mängd: " + currentAmount + " " + unit.getPrefix(),
                "Minsta mängd: " + criticalAmount + " " + unit.getPrefix(),
                "Rekommenderad mängd: " + recommendedAmount + " " + unit.getPrefix(), "</html>");
    }
}
