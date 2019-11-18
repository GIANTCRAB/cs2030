package cs2030.simulator;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

class ServerCounter implements CheckoutCounter, HasOneCheckoutHandler {
    private final CheckoutQueue checkoutQueue;
    private final Server server;
    private final double restingProbability;
    private final RandomGenerator randomGenerator;
    private final Logger logger;
    private final Statistics statistics;
    private Optional<Customer> currentlyServing = Optional.empty();

    ServerCounter(ServerCheckoutQueue serverCheckoutQueue,
                  Server server,
                  double restingProbability,
                  RandomGenerator randomGenerator,
                  Logger logger,
                  Statistics statistics) {
        this.checkoutQueue = serverCheckoutQueue;
        this.server = server;
        this.restingProbability = restingProbability;
        this.randomGenerator = randomGenerator;
        this.logger = logger;
        this.statistics = statistics;
    }

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

            // Set to waiting
            customer.setWait();
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

            // Set to served
            customer.setServed();
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
        customer.setDone();

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
}
