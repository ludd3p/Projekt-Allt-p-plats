package model;

// Ifall man räknar order i gram eller styck
// Exemepl en order på mjöl läggs följade: 500 gram mjöl meddans
// en order på mjölk räknas i 5 st mjölk packet.
public enum Unit {
    MILLILITRE("mL"),
    DECILITRE("dL"),
    CENTILITRE("cl"),
    LITRE("L"),
    MILLIGRAM("mg"),
    GRAM("g"),
    KILOGRAM("kg"),
    WEIGHT("g"),
    AMOUNT("st");

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
