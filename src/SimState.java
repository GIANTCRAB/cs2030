import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class SimState {
    /**
     * The priority queue of events.
     */
    private final PriorityQueue<Event> events;

    /**
     * The statistics maintained.
     */
    private final Statistics stats;

    /**
     * The shop of servers.
     */
    private final Shop shop;

    /**
     * The unique ID of the last created customer.
     */
    private final int lastCustomerId;

    /**
     * All output
     */
    private final StringBuilder consolidatedOutput;

    /**
     * Constructor for creating the simulation state from scratch.
     *
     * @param numOfServers The number of servers.
     */
    public SimState(int numOfServers) {
        this(new PriorityQueue<>(), new Statistics(), new Shop(numOfServers), new StringBuilder(), 1);
    }

    SimState(PriorityQueue<Event> events, Statistics stats, Shop shop, StringBuilder consolidatedOutput, int lastCustomerId) {
        this.events = events;
        this.stats = stats;
        this.shop = shop;
        this.consolidatedOutput = consolidatedOutput;
        this.lastCustomerId = lastCustomerId;
    }

    /**
     * Add an event to the simulation's event queue.
     *
     * @param time   The event to be added to the queue.
     * @param action
     * @return The new simulation state.
     */
    public SimState addEvent(double time, Function<SimState, SimState> action) {
        final PriorityQueue<Event> newEvents = this.events.add(new Event(time, action));
        return new SimState(newEvents, this.stats, this.shop, this.consolidatedOutput, this.lastCustomerId);
    }

    /**
     * Retrieve the next event with earliest time stamp from the
     * priority queue, and a new state.  If there is no more event, an
     * Optional.empty will be returned.
     *
     * @return A pair object with an (optional) event and the new simulation
     * state.
     */
    private Pair<Optional<Event>, SimState> nextEvent() {
        final Pair<Optional<Event>, PriorityQueue<Event>> result = this.events.poll();
        final SimState updatedSimState = new SimState(result.second, this.stats, this.shop, this.consolidatedOutput, this.lastCustomerId);
        return Pair.of(result.first, updatedSimState);
    }

    /**
     * Log a customer's arrival in the simulation.
     *
     * @param time The time the customer arrives.
     * @param c    The customer that arrrives.
     * @return A new state of the simulation after the customer arrives.
     */
    public SimState noteArrival(double time, Customer c) {
        return this.addOutput(String.format("%.3f %s arrives\n", time, c));
    }

    /**
     * Log when a customer waits in the simulation.
     *
     * @param time The time the customer starts waiting.
     * @param s    The server the customer is waiting for.
     * @param c    The customer who waits.
     * @return A new state of the simulation after the customer waits.
     */
    private SimState noteWait(double time, Server s, Customer c) {
        return this.addOutput(String.format("%.3f %s waits to be served by %s\n", time, c, s));
    }

    /**
     * Log when a customer is served in the simulation.
     *
     * @param time The time the customer arrives.
     * @param s    The server that serves the customer.
     * @param c    The customer that is served.
     * @return A new state of the simulation after the customer is served.
     */
    public SimState noteServed(double time, Server s, Customer c) {
        final SimState updatedSimState = this.addOutput(String.format("%.3f %s served by %s\n", time, c, s));
        final Statistics newStats = updatedSimState.stats.serveOneCustomer().recordWaitingTime(c.timeWaited(time));
        return new SimState(updatedSimState.events,
                newStats,
                updatedSimState.shop,
                updatedSimState.consolidatedOutput,
                updatedSimState.lastCustomerId);
    }

    /**
     * Log when a customer is done being served in the simulation.
     *
     * @param time The time the customer arrives.
     * @param s    The server that serves the customer.
     * @param c    The customer that is served.
     * @return A new state of the simulation after the customer is done being
     * served.
     */
    public SimState noteDone(double time, Server s, Customer c) {
        return this.addOutput(String.format("%.3f %s done serving by %s\n", time, c, s));
    }

    /**
     * Log when a customer leaves the shops without service.
     *
     * @param time     The time this customer leaves.
     * @param customer The customer who leaves.
     * @return A new state of the simulation.
     */
    public SimState noteLeave(double time, Customer customer) {
        final SimState updatedSimState = this.addOutput(String.format("%.3f %s leaves\n", time, customer));
        return new SimState(updatedSimState.events,
                updatedSimState.stats.looseOneCustomer(),
                updatedSimState.shop,
                updatedSimState.consolidatedOutput,
                updatedSimState.lastCustomerId);
    }

    /**
     * Simulates the logic of what happened when a customer arrives.
     * The customer is either served, waiting to be served, or leaves.
     *
     * @param time The time the customer arrives.
     * @return A new state of the simulation.
     */
    public SimState simulateArrival(double time) {
        Customer customer = new Customer(time, this.lastCustomerId);
        final SimState updatedSimState = this.incrementLastCustomerId();
        return updatedSimState.addEvent(time, simState -> simState.noteArrival(time, customer))
                .addEvent(time, simState -> simState.processArrival(time, customer));
    }

    private SimState incrementLastCustomerId() {
        final int newLastCustomerId = this.lastCustomerId + 1;
        return new SimState(this.events, this.stats, this.shop, this.consolidatedOutput, newLastCustomerId);
    }

    private SimState addOutput(String output) {
        final StringBuilder consolidatedOutput = new StringBuilder(this.consolidatedOutput.toString());
        consolidatedOutput.append(output);
        return new SimState(this.events, this.stats, this.shop, consolidatedOutput, this.lastCustomerId);
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
        final Optional<Server> idleServer = this.shop.findIdleServer();
        if (idleServer.isPresent()) {
            return this.serveCustomer(time, idleServer.get(), customer);
        }
        final Optional<Server> serverWithNoWaitingCustomer = this.shop.findServerWithNoWaitingCustomer();
        if (serverWithNoWaitingCustomer.isPresent()) {
            final SimState updatedSimState = this.noteWait(time, serverWithNoWaitingCustomer.get(), customer);
            final Shop newShop = updatedSimState.shop.replace(serverWithNoWaitingCustomer.get().askToWait(customer));
            return new SimState(updatedSimState.events,
                    updatedSimState.stats,
                    newShop,
                    updatedSimState.consolidatedOutput,
                    updatedSimState.lastCustomerId);
        }
        return this.noteLeave(time, customer);
    }

    /**
     * Simulate the logic of what happened when a customer is done being
     * served.  The server either serve the next customer or becomes idle.
     *
     * @param time     The time the service is done.
     * @param server   The server serving the customer.
     * @param customer The customer being served.
     * @return A new state of the simulation.
     */
    public SimState simulateDone(double time, Server server, Customer customer) {
        final SimState updatedSimState = this.addEvent(time, simState -> simState.noteDone(time, server, customer));
        Optional<Customer> c = server.getWaitingCustomer();
        if (c.isPresent()) {
            return updatedSimState.addEvent(time, simState -> simState.serveCustomer(time, server, c.get()));
        }
        final Server updatedServer = server.makeIdle();
        final Shop updatedShop = updatedSimState.shop.replace(updatedServer);

        return new SimState(updatedSimState.events,
                updatedSimState.stats,
                updatedShop,
                updatedSimState.consolidatedOutput,
                updatedSimState.lastCustomerId);
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
    private SimState serveCustomer(double time, Server server, Customer customer) {
        double doneTime = time + Simulation.SERVICE_TIME;
        final Server updatedServer = server.serve(customer);
        final Shop updatedShop = this.shop.replace(updatedServer);
        return new SimState(this.events,
                this.stats,
                updatedShop,
                this.consolidatedOutput,
                this.lastCustomerId)
                .noteServed(time, updatedServer, customer)
                .addEvent(doneTime, simState -> simState.simulateDone(doneTime, updatedServer, customer));
    }

    /**
     * The main simulation loop.  Repeatedly get events from the event
     * queue, simulate and update the event.  Return the final simulation
     * state.
     *
     * @return The final state of the simulation.
     */
    public SimState run() {
        Stream.generate(this::nextEvent)
                .filter(consumer -> consumer.first.isPresent())
                .forEach((consumer) -> {
                    System.out.println(consumer.first.get());
                    consumer.first.get().simulate(consumer.second);
                });
        return this;
    }

    /**
     * Return a string representation of the simulation state, which
     * consists of all the logs and the stats.
     *
     * @return A string representation of the simulation.
     */
    public String toString() {
        return stats.toString();
    }

    /**
     * The Event class encapsulates information and methods pertaining to a
     * Simulator event.  This is an abstract class that should be subclassed
     * into a specific event in the simulator.  The {@code simulate} method
     * must be written.
     *
     * @author Woo Huiren
     * @version CS2030 AY19/20 Sem 1 Lab 7
     */
    public class Event implements Comparable<Event> {
        /**
         * The time this event occurs at.
         */
        private final double time;
        private final Function<SimState, SimState> action;

        /**
         * Creates an event and initializes it.
         *
         * @param time The time of occurrence.
         */
        public Event(double time, Function<SimState, SimState> action) {
            this.time = time;
            this.action = action;
        }

        /**
         * Defines natural ordering of events by their time.
         * Events ordered in ascending order of their timestamps.
         *
         * @param other Another event to compare against.
         * @return 0 if two events occur at same time, a positive number if
         * this event has later than other event, a negative number otherwise.
         */
        public int compareTo(Event other) {
            return (int) Math.signum(this.time - other.time);
        }


        /**
         * The method that simulates this event.
         *
         * @return The updated state after simulating this event.
         */
        public SimState simulate(SimState simState) {
            return this.action.apply(simState);
        }
    }

}
