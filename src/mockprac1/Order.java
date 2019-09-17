package mockprac1;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final Menu menu;
    private List<MenuItem> orderedItems = new ArrayList<>();

    Order(Menu menu) {
        this.menu = menu;
    }

    public Order add(int[] itemsIdToOrder) {
        for (int itemIdToOrder : itemsIdToOrder) {
            MenuItem menuItem = this.menu.get(itemIdToOrder);
            this.orderedItems.add(menuItem);
        }

        return this;
    }

    @Override
    public String toString() {
        int totalCost = 0;
        StringBuilder formattedString = new StringBuilder();
        for (MenuItem menuItem : this.orderedItems) {
            formattedString.append("\n");
            formattedString.append(menuItem.toString());
            totalCost += menuItem.getCost();
        }
        formattedString.append("\nTotal: ");
        formattedString.append(totalCost);

        return formattedString.toString();
    }
}
