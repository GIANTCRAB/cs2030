package mockprac1;

public class Food implements MenuItem {
    private Integer id;
    private String category;
    private String name;
    private int cost;

    public Food(int id, String category, String name, int cost) {
        this.setId(id)
                .setCategory(category)
                .setName(name)
                .setCost(cost);
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public Food setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public String getCategory() {
        return this.category;
    }

    @Override
    public Food setCategory(String category) {
        this.category = category;
        return this;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Food setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public int getCost() {
        return this.cost;
    }

    @Override
    public Food setCost(int cost) {
        this.cost = cost;
        return this;
    }

    @Override
    public String toString() {
        return String.format("#%d %s: %s (%d)", this.getId(), this.getCategory(), this.getName(), this.getCost());
    }
}
