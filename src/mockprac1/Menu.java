package mockprac1;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<MenuItem> menuItems = new ArrayList<>();

    public MenuItem add(String category, String itemName, int cost) {
        final MenuItem food = new Food(this.menuItems.size(), category, itemName, cost);
        this.menuItems.add(food);
        return food;
    }
}
