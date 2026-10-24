package rush.entity;

/**
 * A combo made of two existing menu items. It takes as long as both items together
 * and costs 90% of their combined price, rounded to the nearest cent.
 */
public class ComboMeal extends MenuItem {

    private MenuItem first;
    private MenuItem second;

    public ComboMeal(MenuItem first, MenuItem second) {
        super(first.getName() + " + " + second.getName() + " Combo",
                Money.multiply(first.getPriceCents() + second.getPriceCents(), 0.9),
                first.getPrepUnits() + second.getPrepUnits());
        this.first = first;
        this.second = second;
    }

    public boolean contains(MenuItem item) {
        return first == item || second == item;
    }

    public MenuItem getFirst() {
        return first;
    }

    public MenuItem getSecond() {
        return second;
    }
}
