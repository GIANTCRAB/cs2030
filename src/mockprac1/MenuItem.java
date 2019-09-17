package mockprac1;

public interface MenuItem {
    Integer getId();

    MenuItem setId(Integer id);

    String getCategory();

    MenuItem setCategory(String category);

    String getName();

    MenuItem setName(String name);

    int getCost();

    MenuItem setCost(int cost);
}
