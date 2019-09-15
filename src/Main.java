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
        final CustomerStatistics customerStatistics = new CustomerStatistics();
        CustomerServer customerServer = new CustomerServer(1, null, null);
        final int MAX_WAITING_CUSTOMERS = 1;
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
                    final CustomerEvent newCustomerEvent = new CustomerEvent(customer, customerServer, customer.getServiceStartTime(), CustomerStates.SERVED);
                    customerEventPriorityQueue.offer(newCustomerEvent);
                } else {
                    if (customerServer.canWaitServe()) {
                        final double waitingTime = customerServer.getAvailableTime() - customer.getArrivalTime();
                        final Customer waitingCustomer = new Customer(customer.getId(), customer.getArrivalTime(), waitingTime, customer.getCurrentState());
                        customerArrayList.set(waitingCustomer.getId() - 1, waitingCustomer);
                        customerServer = customerServer.waitServe(waitingCustomer);
                        final CustomerEvent newCustomerEvent = new CustomerEvent(waitingCustomer, customerServer, waitingCustomer.getArrivalTime(), CustomerStates.WAITS);
                        customerEventPriorityQueue.offer(newCustomerEvent);
                        customerStatistics.incrementTotalWaitingTime(waitingTime);
                    } else {
                        final CustomerEvent newCustomerEvent = new CustomerEvent(customer, customer.getArrivalTime(), CustomerStates.LEAVES);
                        customerEventPriorityQueue.offer(newCustomerEvent);
                        customerStatistics.incrementNumberOfCustomersLeaves();
                    }
                }
            }

            if (customerEvent.getEventAction() == CustomerStates.WAITS) {
                final CustomerEvent newCustomerEvent = new CustomerEvent(customer, customerServer, customer.getServiceStartTime(), CustomerStates.SERVED);
                customerEventPriorityQueue.offer(newCustomerEvent);
            }

            if (customerEvent.getEventAction() == CustomerStates.SERVED) {
                final CustomerEvent newCustomerEvent = new CustomerEvent(customer, customerServer, customer.getDoneTime(), CustomerStates.DONE);
                customerEventPriorityQueue.offer(newCustomerEvent);
                customerStatistics.incrementNumberOfCustomersServed();
                customerServer = customerServer.hasServed(customer);
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
