package model.ingredient;

/**
 * Enum containing units used for ingredients.
 *
 * @Author Jonathan Engström
 * @Version 3.0
 */
public enum Unit {
    Milliliter("ml"),
    Deciliter("dl"),
    Centiliter("cl"),
    Liter("l"),
    Milligram("mg"),
    Gram("g"),
    Kilogram("kg"),
    Styck("st");

    private String prefix;

    Unit(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public static Unit getUnitBasedOnPrefix(String prefix){
        for(Unit unit : Unit.values()){
            if(unit.prefix.equals(prefix)){
                return unit;
            }
        }
        return null;
    }

    public static String[] getPrefixArray(){
        String[] prefixArray = new String[values().length];

        for(int i = 0; i < prefixArray.length; i++){
            prefixArray[i] = values()[i].prefix;
        }

        return prefixArray;
    }


}
