package cs2030;

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
     * The priority queue of events.
     */
    private final EventStreamProvider eventStreamProvider;

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
    private final int lastCustomerId;

    /**
     * A private constructor of internal states.
     *
     * @param shop                The list of servers.
     * @param stats               The statistics being kept.
     * @param eventStreamProvider A priority queue of events.
     * @param log                 A log of what's happened so far.
     */
    private SimState(Shop shop, Statistics stats, EventStreamProvider eventStreamProvider,
                     Logger log, int lastCustomerId) {
        this.shop = shop;
        this.stats = stats;
        this.eventStreamProvider = eventStreamProvider;
        this.log = log;
        this.lastCustomerId = lastCustomerId;
    }

    /**
     * Constructor for creating the simulation state from scratch.
     *
     * @param numOfServers The number of servers.
     */
    public SimState(int numOfServers, int numOfSelfCheckout) {
        this(new Shop(numOfServers, numOfSelfCheckout),
                new Statistics(),
                new EventStreamProvider(),
                new EventLogger(), 1);
    }

    /**
     * Update the statistics of this simulation.
     *
     * @param stats The updated statistics to replace the existing one.
     * @return The new simulation state.
     */
    private SimState stats(Statistics stats) {
        return new SimState(this.shop, stats, this.eventStreamProvider, this.log, this.lastCustomerId);
    }

    /**
     * Update a server of this simulations.
     *
     * @param s The updated server to replace the existing one.
     * @return The new simulation state.
     */
    private SimState server(Server s) {
        return new SimState(shop.replace(s), this.stats, this.eventStreamProvider, this.log,
                this.lastCustomerId);
    }

    /**
     * Update the event queue of this simulations.
     *
     * @param eventStreamProvider The priority queue to replace the existing one.
     * @return The new simulation state.
     */
    private SimState events(EventStreamProvider eventStreamProvider) {
        return new SimState(this.shop, this.stats, eventStreamProvider, this.log, this.lastCustomerId);
    }

    /**
     * Update the event log of this simulations.
     *
     * @param s The log string to append to this event log.
     * @return The new simulation state.
     */
    private SimState log(String s) {
        return new SimState(this.shop, this.stats, this.eventStreamProvider, this.log.log(s), this.lastCustomerId);
    }

    /**
     * Update the event log of this simulations.
     *
     * @param id The server id
     * @return The new simulation state.
     */
    private SimState id(int id) {
        return new SimState(this.shop, this.stats, this.eventStreamProvider, this.log, id);
    }

    /**
     * Called when a customer arrived in the simulation.
     *
     * @param time The time the customer arrives.
     * @param c    The customer that arrrives.
     * @return A new state of the simulation after the customer arrives.
     */
    public SimState noteArrival(double time, Customer c) {
        return log(String.format("%.3f %s arrives\n", time, c));
    }

    /**
     * Called when a customer arrived in the simulation.  This methods update
     * the logs of simulation.
     *
     * @param time The time the customer arrives.
     * @param c    The customer that arrrives.
     * @return A new state of the simulation after the customer arrives.
     */
    public SimState noteWait(double time, Server s, Customer c) {
        return log(String.format("%.3f %s waits to be served by %s\n", time, c, s));
    }

    /**
     * Called when a customer is done being served in the simulation.
     * This methods update the logs of the simulation.
     *
     * @param time The time the customer arrives.
     * @param s    The server that serves the customer.
     * @param c    The customer that is served.
     * @return A new state of the simulation after the customer is done being
     * served.
     */
    public SimState noteDone(double time, Server s, Customer c) {
        return log(String.format("%.3f %s done serving by %s\n", time, c, s));
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
        return log(String.format("%.3f %s leaves\n", time, customer))
                .stats(stats.looseOneCustomer());
    }

    /**
     * Simulates the logic of what happened when a customer arrives.
     * The customer is either served, waiting to be served, or leaves.
     *
     * @param time The time the customer arrives.
     * @return A new state of the simulation.
     */
    public SimState simulateArrival(double time) {
        Customer customer = new NormalCustomer(time, this.lastCustomerId);
        return noteArrival(time, customer)
                .id(this.lastCustomerId + 1)
                .processArrival(time, customer);
    }

    /**
     * Handle the logic of finding idle servers to serve the customer,
     * or a server that the customer can wait for, or leave.  Called
     * from simulateArrival.
     *
     * @param time     The time the customer arrives.
     * @param customer The customer to be served.
     * @return A new state of the simulation.
     */
    private SimState processArrival(double time, Customer customer) {
        return shop.find(CheckoutCounter::isIdle)
                .map(server -> serveCustomer(time, server, customer))
                // TODO: If greedy, find the shortest queue
                .or(() -> shop
                        .find(CheckoutCounter::canAcceptCustomer)
                        .map(server -> noteWait(time, server, customer)
                                .server(server.askToWait(customer))))
                .orElseGet(() -> noteLeave(time, customer));
    }

    /**
     * Simulates the logic of what happened when a customer is done being
     * served.  The server either serve the next customer or becomes idle.
     *
     * @param time     The time the service is done.
     * @param server   The server serving the customer.
     * @param customer The customer being served.
     * @return A new state of the simulation.
     */
    public SimState simulateDone(double time, Server server, Customer customer) {
        return
                shop.find(s -> s.equals(server))
                        .flatMap(s -> s.getWaitingCustomer())
                        .map(c -> noteDone(time, server, customer).serveCustomer(time, server, c))
                        .orElseGet(() -> noteDone(time, server, customer).server(server.makeIdle()));
    }

    /**
     * Handle the logic of server serving customer.  A new done event
     * is generated and scheduled.
     *
     * @param time     The time this customer is served.
     * @param server   The server serving this customer.
     * @param customer The customer being served.
     * @return A new state of the simulation.
     */
    public SimState serveCustomer(double time, CheckoutCounter checkoutCounter, Customer customer) {
        double doneTime = time + Simulation.SERVICE_TIME;
        final Event serveEvent = new EventImpl(time, () -> {
            checkoutCounter.addCustomerToCounter(customer);
            checkoutCounter.startServingCustomer();
            // TODO: what about self checkout machine?
            this.noteServed(time, ((ServerCounter) checkoutCounter).getCheckoutHandler(), customer);
            return new EventImpl(doneTime, () -> {
                // Do something when done
            });
        });
        this.eventStreamProvider.addEvent(serveEvent);
        return server(server.serve(customer))
                .noteServed(time, server, customer)
                .addEvent(doneTime, state -> state.simulateDone(doneTime, server, customer));
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
