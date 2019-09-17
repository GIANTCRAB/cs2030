package mockprac1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        final Menu menu = Main.readMenuItems(reader);
        menu.print();
        final Order order = Main.readOrderItems(reader, menu);
        System.out.print("\n--- Order ---\n");
        System.out.println(order.toString());
    }

    private static Menu readMenuItems(Scanner reader) {
        final Menu menu = new Menu();
        while (reader.next().equals("add")) {
            final String category = reader.next();
            final String itemName = reader.next();
            if (category.equals("Combo")) {
                List<Integer> itemIdList = new ArrayList<>();
                while (reader.hasNextInt()) {
                    itemIdList.add(reader.nextInt());
                }
                menu.add(category, itemName, itemIdList);
            } else {
                menu.add(category, itemName, reader.nextInt());
            }
        }

        return menu;
    }

    private static Order readOrderItems(Scanner reader, Menu menu) {
        final Order order = new Order(menu);
        List<Integer> itemIdList = new ArrayList<>();
        while (reader.hasNext()) {
            itemIdList.add(reader.nextInt());
        }
        return order.add(itemIdList);
    }
}
