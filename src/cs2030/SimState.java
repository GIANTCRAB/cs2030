package cs2030;

import cs2030.simulator.RandomGenerator;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * This class encapsulates all the simulation states.  There are four main
 * components: (i) the event queue, (ii) the statistics, (iii) the shop
 * (the servers) and (iv) the event logs.
 *
 * @author atharvjoshi
 * @author weitsang
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
public class SimState {
    /**
     * The number of customers to generate
     */
    private final int numOfCustomers;

    /**
     * The priority queue of events.
     */
    private final EventStreamProvider eventStreamProvider;

    /**
     * The RNG maintained.
     */
    private final RandomGenerator randomGenerator;

    /**
     * The statistics maintained.
     */
    private final Statistics stats;

    /**
     * The shop of servers.
     */
    private final Shop shop;

    /**
     * The event logs.
     */
    private final Logger log;

    /**
     * The customer id.
     */
    private int lastCustomerId;

    /**
     * Constructor for creating the simulation state from scratch.
     *
     * @param numOfServers      The number of servers.
     * @param numOfCustomers    The number of customers.
     * @param numOfSelfCheckout The number of self checkout machines.
     * @param maxQueueLength    The maximum queue length of checkout queues
     * @param randomGenerator   RNG generator seeded by Main
     */
    public SimState(int numOfCustomers, int numOfServers, int numOfSelfCheckout, int maxQueueLength, RandomGenerator randomGenerator) {
        this.numOfCustomers = numOfCustomers;
        this.stats = new Statistics();
        this.eventStreamProvider = new EventStreamProvider();
        this.log = new EventLogger();
        this.randomGenerator = randomGenerator;
        this.shop = new Shop(numOfServers, numOfSelfCheckout, maxQueueLength, this.randomGenerator, this.log, this.stats);
        this.lastCustomerId = 1;
    }

    /**
     * Update the event log of this simulations.
     *
     * @return The new simulation state.
     */
    private SimState incrementId() {
        this.lastCustomerId++;

        return this;
    }

    /**
     * Called when a customer arrived in the simulation.
     *
     * @param time The time the customer arrives.
     * @param c    The customer that arrrives.
     * @return A new state of the simulation after the customer arrives.
     */
    public SimState noteArrival(double time, Customer c) {
        this.eventStreamProvider.addEvent(
                new EventImpl(time, () -> {
                    this.log.log(String.format("%.3f %s arrives\n", time, c));
                    return Optional.empty();
                }));

        return this;
    }

    /**
     * Called when a customer leaves the shops without service.
     * Update the log and statistics.
     *
     * @param time     The time this customer leaves.
     * @param customer The customer who leaves.
     * @return A new state of the simulation.
     */
    public SimState noteLeave(double time, Customer customer) {
        this.log.log(String.format("%.3f %s leaves\n", time, customer));
        this.stats.looseOneCustomer();

        return this;
    }

    /**
     * Simulates the logic of what happened when a customer arrives.
     * The customer is either served, waiting to be served, or leaves.
     *
     * @param time The time the customer arrives.
     */
    public Optional<Event[]> simulateArrival(double time) {
        // TODO: implement greedy customer arrival
        Customer customer = new NormalCustomer(time, this.lastCustomerId);
        return this.noteArrival(time, customer)
                .incrementId()
                .processArrival(time, customer);
    }

    /**
     * Handle the logic of finding idle servers to serve the customer,
     * or a server that the customer can wait for, or leave.  Called
     * from simulateArrival.
     *
     * @param time     The time the customer arrives.
     * @param customer The customer to be served.
     * @return A stream of events to be processed. The method calling this needs to run eventStreamProvider.addEvent on the output
     */
    private Optional<Event[]> processArrival(double time, Customer customer) {
        // Find the idle counter and go there
        return this.shop.find(CheckoutCounter::isIdle).map(checkoutCounter -> new Event[]{
                new EventImpl(time, () -> checkoutCounter.addCustomerToCounter(time, customer))
        }).or(() -> this.shop.find(CheckoutCounter::canAcceptCustomer)
                .map(checkoutCounter -> Optional.of(
                        new Event[]{
                                new EventImpl(time, () -> checkoutCounter.addCustomerToCounter(time, customer))
                        }
                ))
                .orElseGet(() -> {
                    this.noteLeave(time, customer);
                    return Optional.empty();
                }));
    }

    /**
     * Run simulation
     */
    public SimState run() {
        double time = 0.0;
        for (int i = 0; i < this.numOfCustomers; i++) {
            this.simulateArrival(time).ifPresent(this.eventStreamProvider::addEvents);
            time += this.randomGenerator.genInterArrivalTime();
        }

        this.eventStreamProvider.processEvents();

        return this;
    }

    /**
     * Return a string representation of the simulation state, which
     * consists of all the logs and the stats.
     *
     * @return A string representation of the simulation.
     */
    public String toString() {
        return this.log + this.stats.toString();
    }
}
