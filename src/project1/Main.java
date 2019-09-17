package project1;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        final ArrayList<CustomerServer> customerServerArrayList = Main.readCustomerServerInputs(reader);
        final ArrayList<Customer> customerArrayList = Main.readCustomerQueueInputs(reader);
        reader.close();
        Main.processCustomerQueue(customerArrayList, customerServerArrayList);
    }

    private static ArrayList<CustomerServer> readCustomerServerInputs(Scanner reader) {
        final ArrayList<CustomerServer> customerServerArrayList = new ArrayList<>();
        final int customerServersAvailable = reader.nextInt();
        for (int i = 0; i < customerServersAvailable; i++) {
            final CustomerServer customerServer = new CustomerServer(i + 1, null, null);
            customerServerArrayList.add(customerServer);
        }

        return customerServerArrayList;
    }

    private static ArrayList<Customer> readCustomerQueueInputs(Scanner reader) {
        final ArrayList<Customer> customerArrayList = new ArrayList<>();
        while (reader.hasNextDouble()) {
            customerArrayList.add(new Customer(customerArrayList.size() + 1, reader.nextDouble()));
        }

        return customerArrayList;
    }

    private static void processCustomerQueue(ArrayList<Customer> customerArrayList, ArrayList<CustomerServer> customerServerArrayList) {
        final CustomerStatistics customerStatistics = new CustomerStatistics();
        final PriorityQueue<CustomerEvent> customerEventPriorityQueue = new PriorityQueue<>(customerArrayList.size() + 1, new CustomerEventComparator());

        for (Customer customer : customerArrayList) {
            final CustomerEvent customerEvent = new CustomerEvent(customer, customer.getArrivalTime(), CustomerStates.ARRIVES);
            customerEventPriorityQueue.offer(customerEvent);
        }

        while (!customerEventPriorityQueue.isEmpty()) {
            CustomerEvent customerEvent = customerEventPriorityQueue.poll();

            System.out.println(customerEvent.toString());
            final Customer customer = customerEvent.consumeEvent();

            boolean customerHasBeenProcess = false;
            for (CustomerServer customerServer : customerServerArrayList) {
                // Process based on actions
                if (customerEvent.getEventAction() == CustomerStates.ARRIVES) {
                    if (customerServer.canServe(customer)) {
                        customerServer = customerServer.serve(customer);
                        final CustomerEvent newCustomerEvent = new CustomerEvent(customer, customerServer, customer.getServiceStartTime(), CustomerStates.SERVED);
                        customerEventPriorityQueue.offer(newCustomerEvent);

                        customerHasBeenProcess = true;
                        break;
                    }
                }

                if (customerEvent.getEventAction() == CustomerStates.WAITS) {
                    if (customerEvent.getCustomerServer() != null && customerEvent.getCustomerServer() == customerServer) {
                        final CustomerEvent newCustomerEvent = new CustomerEvent(customer, customerServer, customer.getServiceStartTime(), CustomerStates.SERVED);
                        customerEventPriorityQueue.offer(newCustomerEvent);

                        customerHasBeenProcess = true;
                        break;
                    }
                }

                if (customerEvent.getEventAction() == CustomerStates.SERVED) {
                    if (customerEvent.getCustomerServer() != null && customerEvent.getCustomerServer() == customerServer) {
                        final CustomerEvent newCustomerEvent = new CustomerEvent(customer, customerServer, customer.getDoneTime(), CustomerStates.DONE);
                        customerEventPriorityQueue.offer(newCustomerEvent);
                        customerStatistics.incrementNumberOfCustomersServed();
                        customerServer.hasServed(customer);

                        customerHasBeenProcess = true;
                        break;
                    }
                }

                if (customerEvent.getEventAction() == CustomerStates.LEAVES || customerEvent.getEventAction() == CustomerStates.DONE) {
                    customerHasBeenProcess = true;
                    break;
                }
            }

            if (!customerHasBeenProcess) {
                // Try to see if customer can be added to waiting list
                for (CustomerServer customerServer : customerServerArrayList) {
                    if (customerServer.canWaitServe()) {
                        final double waitingTime = customerServer.getAvailableTime() - customer.getArrivalTime();
                        customer.setWaitingTime(waitingTime);
                        customerServer.waitServe(customer);
                        final CustomerEvent newCustomerEvent = new CustomerEvent(customer, customerServer, customer.getArrivalTime(), CustomerStates.WAITS);
                        customerEventPriorityQueue.offer(newCustomerEvent);
                        customerStatistics.incrementTotalWaitingTime(waitingTime);

                        customerHasBeenProcess = true;
                        break;
                    }
                }
            }

            if (!customerHasBeenProcess) {
                // Customer cannot be served, nor can wait
                final CustomerEvent newCustomerEvent = new CustomerEvent(customer, customer.getArrivalTime(), CustomerStates.LEAVES);
                customerEventPriorityQueue.offer(newCustomerEvent);
                customerStatistics.incrementNumberOfCustomersLeaves();
            }

            customerEventPriorityQueue.remove(customerEvent);
        }

        System.out.printf("[%.3f %d %d]\n",
                customerStatistics.computeAverageWaitingTime(),
                customerStatistics.getNumberOfCustomersServed(),
                customerStatistics.getNumberOfCustomersLeaves()
        );
    }
}
