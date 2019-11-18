package cs2030.simulator;

import java.util.Optional;

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
     * The chance of getting a greedy customer
     */
    private final double greedyProbability;

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
     * @param numOfServers       The number of servers.
     * @param numOfCustomers     The number of customers.
     * @param numOfSelfCheckout  The number of self checkout machines.
     * @param maxQueueLength     The maximum queue length of checkout queues
     * @param rngBaseSeed
     * @param arrivalRate
     * @param serviceRate
     * @param serverRestingRate
     * @param restingProbability
     */
    public SimState(int numOfCustomers,
                    int numOfServers,
                    int numOfSelfCheckout,
                    int maxQueueLength,
                    int rngBaseSeed,
                    double arrivalRate,
                    double serviceRate,
                    double serverRestingRate,
                    double restingProbability,
                    double greedyProbability) {
        this.numOfCustomers = numOfCustomers;
        this.stats = new Statistics();
        this.eventStreamProvider = new EventStreamProvider();
        this.log = new EventLogger();
        this.randomGenerator = new RandomGenerator(rngBaseSeed, arrivalRate, serviceRate, serverRestingRate);
        this.shop = new Shop(numOfServers, numOfSelfCheckout, maxQueueLength, restingProbability, this.randomGenerator, this.log, this.stats);
        this.lastCustomerId = 0;
        this.greedyProbability = greedyProbability;
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
     * Simulates the logic of what happened when a customer arrives.
     * The customer is either served, waiting to be served, or leaves.
     *
     * @param time The time the customer arrives.
     */
    public Optional<Event[]> simulateArrival(double time) {
        Customer customer;
        if (this.randomGenerator.genCustomerType() < this.greedyProbability) {
            // Generate greedy customer
            customer = new GreedyCustomer(time, this.incrementId().lastCustomerId);
        } else {
            customer = new NormalCustomer(time, this.incrementId().lastCustomerId);
        }
        // Create arrival event
        return Optional.of(new Event[]{
                new EventImpl(time, () -> this.noteArrival(time, customer)
                        .processArrival(time, customer)
                )
        });
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
        Optional<CheckoutCounter> availableCounter = this.shop.find(CheckoutCounter::isIdle);
        if (customer instanceof GreedyCustomer) {
            // find shortest queue for greedy customer
            availableCounter = availableCounter.or(() -> this.shop.getSortedCheckoutCounters().filter(CheckoutCounter::canAcceptCustomer).findFirst());
        } else {
            // no preference, just search
            availableCounter = availableCounter.or(() -> this.shop.find(CheckoutCounter::canAcceptCustomer));
        }

        if (availableCounter.isPresent()) {
            final CheckoutCounter checkoutCounter = availableCounter.get();
            return Optional.of(new Event[]{
                    new EventImpl(time, () -> checkoutCounter.addCustomerToCounter(time, customer))
            });
        }

        return Optional.of(new Event[]{
                new EventImpl(time, () -> {
                    customer.setLeave();
                    this.log.log(String.format("%.3f %s leaves\n", time, customer));
                    this.stats.looseOneCustomer();

                    return Optional.empty();
                })
        });
    }

    /**
     * Populate data and run simulation
     */
    public SimState run() {
        // Generate data
        double time = 0.0;
        for (int i = 0; i < this.numOfCustomers; i++) {
            this.simulateArrival(time).ifPresent(this.eventStreamProvider::addEvents);
            time += this.randomGenerator.genInterArrivalTime();
        }

        // Run the event stream
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
