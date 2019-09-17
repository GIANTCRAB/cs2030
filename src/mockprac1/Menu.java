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

    public MenuItem add(String category, String comboName, List<Integer> itemIdList) {
        final Combo comboItem = new Combo(this.menuItems.size(), category, comboName);
        for (Integer itemId : itemIdList) {
            comboItem.addOtherItem(this.menuItems.get(itemId));
        }
        if (!this.categories.contains(category)) {
            this.categories.add(category);
        }
        this.menuItems.add(comboItem);
        return comboItem;
    }

    public MenuItem get(int id) {
        return this.menuItems.get(id);
    }

    public void print() {
        for (String category : this.categories) {
            for (MenuItem menuItem : this.menuItems) {
                if (category.equals(menuItem.getCategory())) {
                    System.out.println(menuItem.toString());
                }
            }
        }
    }
}
