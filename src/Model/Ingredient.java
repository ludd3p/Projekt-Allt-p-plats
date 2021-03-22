package Model;

// Använd klassen när vi lägger till ingridienser i recepten.

public class Ingredient {
    private String name;
    private Unit unit;

    public Ingredient(String name, Unit unit) {
        this.name = name;
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + ":" + unit.getPrefix();
    }
}
