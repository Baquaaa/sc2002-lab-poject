package rush.entity;

import java.util.ArrayList;
import java.util.List;

/** The restaurant's menu. */
public class Menu {

    private List<MenuItem> dishes = new ArrayList<>();

    public Menu() {
        dishes.add(new MenuItem("Salad", 900, 1));
        dishes.add(new MenuItem("Burger", 1500, 1));
        dishes.add(new MenuItem("Pasta", 1800, 2));
    }

    public List<MenuItem> getDishes() {
        return dishes;
    }

    /** Returns the dish at the given position, wrapping around the end of the list. */
    public MenuItem getDish(int index) {
        return dishes.get(index % dishes.size());
    }
}
