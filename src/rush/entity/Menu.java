package rush.entity;

import java.util.ArrayList;
import java.util.List;

/** The restaurant's menu. */
public class Menu {

    private List<MenuItem> dishes = new ArrayList<>();
    private List<ComboMeal> combos = new ArrayList<>();

    public Menu() {
        dishes.add(new MenuItem("Salad", 900, 1));
        dishes.add(new MenuItem("Burger", 1500, 1));
        dishes.add(new MenuItem("Pasta", 1800, 2));
        combos.add(new ComboMeal(dishes.get(0), dishes.get(1))); // Salad + Burger
        combos.add(new ComboMeal(dishes.get(0), dishes.get(2))); // Salad + Pasta
    }

    public List<MenuItem> getDishes() {
        return dishes;
    }

    /** Returns the dish at the given position, wrapping around the end of the list. */
    public MenuItem getDish(int index) {
        return dishes.get(index % dishes.size());
    }

    public List<ComboMeal> getCombos() {
        return combos;
    }

    /** The first combo that contains the given dish, or null if there is none. */
    public ComboMeal findComboWith(MenuItem dish) {
        for (ComboMeal combo : combos) {
            if (combo.contains(dish)) {
                return combo;
            }
        }
        return null;
    }

    public MenuItem getDishWithMostPrepUnits() {
        MenuItem longest = dishes.get(0);
        for (MenuItem dish : dishes) {
            if (dish.getPrepUnits() > longest.getPrepUnits()) {
                longest = dish;
            }
        }
        return longest;
    }
}
