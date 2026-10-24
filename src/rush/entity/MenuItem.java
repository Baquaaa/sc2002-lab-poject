package rush.entity;

/** A dish on the menu. Prices are stored in cents to avoid rounding errors. */
public class MenuItem {

    private String name;
    private int priceCents;
    private int prepUnits;

    public MenuItem(String name, int priceCents, int prepUnits) {
        this.name = name;
        this.priceCents = priceCents;
        this.prepUnits = prepUnits;
    }

    public String getName() {
        return name;
    }

    public int getPriceCents() {
        return priceCents;
    }

    public int getPrepUnits() {
        return prepUnits;
    }

    public String toString() {
        return name;
    }
}
