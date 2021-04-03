package model;

// Använd klassen när vi lägger till ingridienser i recepten.

public class Ingredient {
    private String name;
    private double cost;
    private double currentAmount;
    private double criticalAmount;
    private double recommendedAmount;
    private Unit unit;

    public Ingredient() {
    }

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

    public String toString(String key) {
        return String.format("%s %s %s %s %s %s %s",
                "<html>",
                "Product: " + name,
                "<br>Cost: " + cost + " " + "sek/" + unit.getPrefix(),
                "<br>Current amount: " + currentAmount + " " + unit.getPrefix(),
                "Min amount: " + criticalAmount + " " + unit.getPrefix(),
                "Max amount: " + recommendedAmount + " " + unit.getPrefix(),
                "<!--" + key + "--></html>");
    }

}