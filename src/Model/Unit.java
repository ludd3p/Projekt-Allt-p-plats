package Model;

// Ifall man räknar order i gram eller styck
// Exemepl en order på mjöl läggs följade: 500 gram mjöl meddans
// en order på mjölk räknas i 5 st mjölk packet.
public enum Unit {
    MILLILITRE("mL"),
    DECILITRE("dL"),
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

    public static String[] getPrefixArray(){
        String[] prefixArray = new String[values().length];

        for(int i = 0; i < prefixArray.length; i++){
            prefixArray[i] = values()[i].prefix;
        }

        return prefixArray;
    }
}
