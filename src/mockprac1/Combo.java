package mockprac1;

import java.util.ArrayList;
import java.util.List;

public class Combo implements MenuItem {
    private Integer id;
    private String category;
    private String name;
    private int cost;
    private List<MenuItem> otherItems = new ArrayList<>();
    private final static int COMBO_DISCOUNT = 50;

    public Combo(int id, String category, String name) {
        this.setId(id)
                .setCategory(category)
                .setName(name);
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public Combo setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public String getCategory() {
        return this.category;
    }

    @Override
    public Combo setCategory(String category) {
        this.category = category;
        return this;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Combo setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public int getCost() {
        return this.cost - Combo.COMBO_DISCOUNT;
    }

    @Override
    public Combo setCost(int cost) {
        this.cost = cost;
        return this;
    }

    public Combo incrementCost(int cost) {
        this.cost += cost;
        return this;
    }

    public List<MenuItem> getOtherItems() {
        return otherItems;
    }

    public Combo addOtherItem(MenuItem menuItem) {
        this.getOtherItems().add(menuItem);
        this.incrementCost(menuItem.getCost());
        return this;
    }

    @Override
    public String toString() {
        StringBuilder formattedString = new StringBuilder();
        formattedString.append(String.format("#%d %s: %s (%d)", this.getId(), this.getCategory(), this.getName(), this.getCost()));
        for (MenuItem menuItem : this.getOtherItems()) {
            formattedString.append("\n   ");
            formattedString.append(menuItem.toString());
        }
        return formattedString.toString();
    }
}
