import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final PriorityQueue<Customer> customerPriorityQueue = Main.readCustomerQueueInputs();

        for (Customer customer : customerPriorityQueue) {
            System.out.println(customer.toString());
        }

        System.out.println("Number of customers: " + customerPriorityQueue.size());
    }

    private static PriorityQueue<Customer> readCustomerQueueInputs() {
        final PriorityQueue<Customer> customerPriorityQueue = new PriorityQueue<>();
        Scanner reader = new Scanner(System.in);
        while (reader.hasNextDouble()) {
            customerPriorityQueue.add(new Customer(customerPriorityQueue.size(), reader.nextDouble()));
        }
        reader.close();

        return customerPriorityQueue;
    }
}
