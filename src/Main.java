import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final ArrayList<Customer> customerArrayList = Main.readCustomerQueueInputs();
        Main.processCustomerQueue(customerArrayList);
    }

    private static ArrayList<Customer> readCustomerQueueInputs() {
        final ArrayList<Customer> customerArrayList = new ArrayList<>();
        Scanner reader = new Scanner(System.in);
        while (reader.hasNextDouble()) {
            customerArrayList.add(new Customer(customerArrayList.size() + 1, reader.nextDouble()));
        }
        reader.close();

        return customerArrayList;
    }

    private static void processCustomerQueue(ArrayList<Customer> customerArrayList) {
        CustomerServer customerServer = new CustomerServer(1, null);
        final PriorityQueue<CustomerEvent> customerEventPriorityQueue = new PriorityQueue<>();

        System.out.println("# Adding arrivals");
        for (Customer customer : customerArrayList) {
            final CustomerEvent customerEvent = new CustomerEvent(customer, customer.getArrivalTime(), CustomerStates.ARRIVES);
            customerEventPriorityQueue.add(customerEvent);
        }
        Main.outputCustomerEventCurrentStatus(customerEventPriorityQueue);

        while (true) {
            CustomerEvent customerEvent = customerEventPriorityQueue.poll();
            if (customerEvent == null) {
                break;
            }

            System.out.println("# Get next event: " + customerEvent.toString());
            final Customer customer = customerEvent.consumeEvent();
            customerArrayList.set(customer.getId() - 1, customer);

            // Process based on actions
            if (customerEvent.getEventAction() == CustomerStates.ARRIVES) {
                if (customerServer.canServe(customer)) {
                    customerServer = customerServer.serve(customer);
                    final CustomerEvent newCustomerEvent = new CustomerEvent(customer, customer.getArrivalTime(), CustomerStates.SERVED);
                    customerEventPriorityQueue.add(newCustomerEvent);
                } else {
                    final CustomerEvent newCustomerEvent = new CustomerEvent(customer, customer.getArrivalTime(), CustomerStates.LEAVES);
                    customerEventPriorityQueue.add(newCustomerEvent);
                }
            }

            if (customerEvent.getEventAction() == CustomerStates.SERVED) {
                final CustomerEvent newCustomerEvent = new CustomerEvent(customer, customer.getDoneTime(), CustomerStates.DONE);
                customerEventPriorityQueue.add(newCustomerEvent);
            }

            customerEventPriorityQueue.remove(customerEvent);
            Main.outputCustomerEventCurrentStatus(customerEventPriorityQueue);
        }

        System.out.println("Number of customers: " + customerArrayList.size());
    }

    private static void outputCustomerEventCurrentStatus(PriorityQueue<CustomerEvent> customerEventPriorityQueue) {
        for (CustomerEvent customerEvent : customerEventPriorityQueue) {
            System.out.println(customerEvent.toString());
        }
    }
}
