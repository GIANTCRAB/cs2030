import java.util.ArrayList;
import java.util.Arrays;
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
        double nextWaitSlot = 0.0;
        final CustomerStatistics customerStatistics = new CustomerStatistics();
        CustomerServer customerServer = new CustomerServer(1, null);
        final PriorityQueue<CustomerEvent> customerEventPriorityQueue = new PriorityQueue<>(customerArrayList.size() + 1, new CustomerEventComparator());

        System.out.println("# Adding arrivals");
        for (Customer customer : customerArrayList) {
            final CustomerEvent customerEvent = new CustomerEvent(customer, customer.getArrivalTime(), CustomerStates.ARRIVES);
            customerEventPriorityQueue.offer(customerEvent);
        }
        Main.outputCustomerEventCurrentStatus(customerEventPriorityQueue);

        while (!customerEventPriorityQueue.isEmpty()) {
            CustomerEvent customerEvent = customerEventPriorityQueue.poll();

            System.out.println("# Get next event: " + customerEvent.toString());
            final Customer customer = customerEvent.consumeEvent();
            customerArrayList.set(customer.getId() - 1, customer);

            // Process based on actions
            if (customerEvent.getEventAction() == CustomerStates.ARRIVES) {
                if (customerServer.canServe(customer)) {
                    customerServer = customerServer.serve(customer);
                    final CustomerEvent newCustomerEvent = new CustomerEvent(customer, customer.getArrivalTime(), CustomerStates.SERVED);
                    customerEventPriorityQueue.offer(newCustomerEvent);
                } else {
                    if (nextWaitSlot <= customer.getArrivalTime()) {
                        final CustomerEvent newCustomerEvent = new CustomerEvent(customer, customer.getArrivalTime(), CustomerStates.WAITS);
                        customerEventPriorityQueue.offer(newCustomerEvent);
                        nextWaitSlot = customerServer.getAvailableTime();
                    } else {
                        final CustomerEvent newCustomerEvent = new CustomerEvent(customer, customer.getArrivalTime(), CustomerStates.LEAVES);
                        customerEventPriorityQueue.offer(newCustomerEvent);
                        customerStatistics.incrementNumberOfCustomersLeaves();
                    }
                }
            }

            if (customerEvent.getEventAction() == CustomerStates.WAITS) {
                final Customer waitingCustomer = new Customer(customer.getId(), nextWaitSlot, customer.getCurrentState());
                customerArrayList.set(waitingCustomer.getId() - 1, waitingCustomer);
                customerServer = customerServer.serve(waitingCustomer);
                final CustomerEvent newCustomerEvent = new CustomerEvent(waitingCustomer, nextWaitSlot, CustomerStates.SERVED);
                customerStatistics.incrementTotalWaitingTime(nextWaitSlot - customer.getArrivalTime());
                customerEventPriorityQueue.offer(newCustomerEvent);
            }

            if (customerEvent.getEventAction() == CustomerStates.SERVED) {
                final CustomerEvent newCustomerEvent = new CustomerEvent(customer, customer.getDoneTime(), CustomerStates.DONE);
                customerEventPriorityQueue.offer(newCustomerEvent);
                customerStatistics.incrementNumberOfCustomersServed();
            }

            customerEventPriorityQueue.remove(customerEvent);
            Main.outputCustomerEventCurrentStatus(customerEventPriorityQueue);
        }

        System.out.println("Number of customers: " + customerArrayList.size());
        System.out.printf("[%.3f %d %d]",
                customerStatistics.computeAverageWaitingTime(),
                customerStatistics.getNumberOfCustomersServed(),
                customerStatistics.getNumberOfCustomersLeaves()
        );
    }

    private static void outputCustomerEventCurrentStatus(PriorityQueue<CustomerEvent> customerEventPriorityQueue) {
        final CustomerEvent[] customerEventArray = customerEventPriorityQueue.toArray(new CustomerEvent[customerEventPriorityQueue.size()]);
        Arrays.sort(customerEventArray, new CustomerEventComparator());
        for (CustomerEvent customerEvent : customerEventArray) {
            System.out.println(customerEvent.toString());
        }
    }
}
