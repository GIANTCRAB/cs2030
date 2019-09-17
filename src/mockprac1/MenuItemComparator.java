package mockprac1;

import java.util.Comparator;

public class MenuItemComparator implements Comparator<MenuItem> {
    @Override
    public int compare(MenuItem menuItem, MenuItem menuItemTwo) {
        return menuItem.getCategory().compareTo(menuItemTwo.getCategory());
    }
}
