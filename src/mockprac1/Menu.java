package mockprac1;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Menu {
    private List<MenuItem> menuItems = new ArrayList<>();
    private Set<String> categories = new LinkedHashSet<>();

    public MenuItem add(String category, String itemName, int cost) {
        final MenuItem food = new Food(this.menuItems.size(), category, itemName, cost);
        this.menuItems.add(food);
        this.categories.add(category);
        return food;
    }

    public MenuItem add(String category, String comboName, List<Integer> itemIdList) {
        final Combo comboItem = new Combo(this.menuItems.size(), category, comboName);
        for (Integer itemId : itemIdList) {
            comboItem.addOtherItem(this.menuItems.get(itemId));
        }
        this.categories.add(category);
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
