package project2.cs2030.simulator;

import java.util.Optional;

/**
 * The counter for manual servers
 *
 * This class implements the base interface of CheckoutCounter and HasOneCheckoutHandler (namely, one server)
 */
class ServerCounter implements CheckoutCounter, HasOneCheckoutHandler {
    private final CheckoutQueue<Customer> checkoutQueue;
    private final Server server;
    private final double restingProbability;
    private final RandomGenerator randomGenerator;
    private final Logger logger;
    private final Statistics statistics;
    private Optional<Customer> currentlyServing = Optional.empty();

    ServerCounter(int maxQueueLength,
                  Server server,
                  double restingProbability,
                  RandomGenerator randomGenerator,
                  Logger logger,
                  Statistics statistics) {
        this.checkoutQueue = new ServerCheckoutQueue<>(maxQueueLength);
        this.server = server;
        this.restingProbability = restingProbability;
        this.randomGenerator = randomGenerator;
        this.logger = logger;
        this.statistics = statistics;
    }

    /**
     * @return Returns the server in-charge of this counter
     */
    @Override
    public CheckoutHandler getCheckoutHandler() {
        return this.server;
    }

    @Override
    public Optional<Event[]> addCustomerToCounter(double time, Customer customer) {
        if (this.canAcceptCustomer()) {
            this.checkoutQueue.joinCustomerQueue(customer);
            // Check if can serve immediately
            if (this.isAvailable()) {
                // Counter staff is idling
                return this.startServingCustomer(time);
            }

            // Log waiting
            this.logger.log(String.format("%.3f %s waits to be served by %s\n", time, customer, this.server));
        }

        return Optional.empty();
    }

    @Override
    public Optional<Event[]> startServingCustomer(double time) {
        // Check if there's queue
        if (this.checkoutQueue.getCurrentQueueLength() > 0) {
            final Customer customer = this.checkoutQueue.pollCustomer();

            this.currentlyServing = Optional.of(customer);

            // Log customer who was served
            this.logger.log(String.format("%.3f %s served by %s\n", time, customer, this.server));
            this.statistics.serveOneCustomer()
                    .recordWaitingTime(customer.timeWaited(time));

            double doneTime = time + this.randomGenerator.genServiceTime();

            return Optional.of(
                    new Event[]{
                            new EventImpl(doneTime, () -> this.finishServingCustomer(doneTime))
                    }
            );
        }

        return Optional.empty();
    }

    @Override
    public CheckoutQueue<Customer> getCheckoutQueue() {
        return this.checkoutQueue;
    }

    @Override
    public boolean isServingCustomer() {
        return this.currentlyServing.isPresent();
    }

    @Override
    public boolean isIdle() {
        return this.isAvailable() && this.checkoutQueue.getCurrentQueueLength() == 0;
    }

    @Override
    public boolean isAvailable() {
        return !this.isServingCustomer() && !this.isResting();
    }

    @Override
    public boolean canAcceptCustomer() {
        return this.checkoutQueue.canJoinCustomerQueue();
    }

    /**
     * @param time The time which this will be executed
     * @return An array of events to be executed.
     */
    @Override
    public Optional<Event[]> finishServingCustomer(double time) {
        // Set customer to done
        final Customer customer = this.currentlyServing.get();

        // Do logging and statistics
        this.logger.log(String.format("%.3f %s done serving by %s\n", time, customer, this.server));

        // Set as no one being served
        this.currentlyServing = Optional.empty();

        // Roll for a chance of resting
        if (this.randomGenerator.genRandomRest() < this.restingProbability) {
            // Add rest
            final double randomRestTime = this.randomGenerator.genRestPeriod();
            final double doneRestTime = time + randomRestTime;
            final Event restEvent = this.server.takeRest(doneRestTime);

            return Optional.of(new Event[]{
                    restEvent,
                    new EventImpl(doneRestTime, () -> this.startServingCustomer(doneRestTime))
            });
        }

        return this.startServingCustomer(time);
    }

    private boolean isResting() {
        return this.server.getRestState() == RestStates.SERVER_REST;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(CheckoutCounter o) {
        return this.getCheckoutQueue().getCurrentQueueLength().compareTo(o.getCheckoutQueue().getCurrentQueueLength());
    }
}
