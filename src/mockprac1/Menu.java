package mockprac1;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<MenuItem> menuItems = new ArrayList<>();
    private List<String> categories = new ArrayList<>();

    public MenuItem add(String category, String itemName, int cost) {
        final MenuItem food = new Food(this.menuItems.size(), category, itemName, cost);
        this.menuItems.add(food);
        if (!this.categories.contains(category)) {
            this.categories.add(category);
        }
        return food;
    }

    public MenuItem get(int id) {
        return this.menuItems.get(id);
    }

    public void print() {
        for (String category: this.categories) {
            for (MenuItem menuItem : this.menuItems) {
                if (category.equals(menuItem.getCategory())) {
                    System.out.println(menuItem.toString());
                }
            }
        }
    }
}
