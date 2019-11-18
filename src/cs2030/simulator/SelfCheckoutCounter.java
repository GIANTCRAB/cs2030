package cs2030.simulator;

import java.util.*;

class SelfCheckoutCounter implements CheckoutCounter, HasManyCheckoutHandlers {
    private final Hashtable<CheckoutHandler, SelfCheckoutQueue> selfCheckoutQueues;
    private final Hashtable<CheckoutHandler, Optional<Customer>> selfCheckoutMachines;
    private final RandomGenerator randomGenerator;
    private final Logger logger;
    private final Statistics statistics;

    SelfCheckoutCounter(Hashtable<CheckoutHandler, SelfCheckoutQueue> selfCheckoutQueues,
                        Hashtable<CheckoutHandler, Optional<Customer>> selfCheckoutMachines,
                        RandomGenerator randomGenerator,
                        Logger logger,
                        Statistics statistics) {
        this.selfCheckoutQueues = selfCheckoutQueues;
        this.selfCheckoutMachines = selfCheckoutMachines;
        this.randomGenerator = randomGenerator;
        this.logger = logger;
        this.statistics = statistics;
    }

    @Override
    public Set<CheckoutHandler> getCheckoutHandlers() {
        return this.selfCheckoutMachines.keySet();
    }

    @Override
    public Optional<Event[]> addCustomerToCounter(double time, Customer customer) {
        Optional<Map.Entry<CheckoutHandler, SelfCheckoutQueue>> selfCheckoutQueueEntry = this.selfCheckoutQueues.entrySet().stream().filter(entry -> entry.getValue().canJoinCustomerQueue()).findFirst();
        if (selfCheckoutQueueEntry.isPresent()) {
            final Map.Entry<CheckoutHandler, SelfCheckoutQueue> selfCheckoutQueueEntryData = selfCheckoutQueueEntry.get();
            selfCheckoutQueueEntryData.getValue().joinCustomerQueue(customer);
            // Check if can serve immediately
            if (this.isAvailable()) {
                // Counter staff is idling
                return this.startServingCustomer(time);
            }

            // Set to waiting
            customer.setWait();
            // Log waiting
            this.logger.log(String.format("%.3f %s waits to be served by %s\n", time, customer, selfCheckoutQueueEntryData.getKey()));
        }

        return Optional.empty();
    }

    @Override
    public Optional<Event[]> startServingCustomer(double time) {
        // Check if there's queue
        // Needs to be sorted descending order, so no parallel stream

        Optional<Map.Entry<CheckoutHandler, SelfCheckoutQueue>> selfCheckoutQueueEntry = this.selfCheckoutQueues.entrySet().stream().filter(entry -> entry.getValue().getCurrentQueueLength() > 0).findFirst();
        if (selfCheckoutQueueEntry.isPresent()) {
            final CheckoutHandler checkoutHandler = selfCheckoutQueueEntry.get().getKey();
            final SelfCheckoutQueue selfCheckoutQueue = selfCheckoutQueueEntry.get().getValue();
            final Customer customer = selfCheckoutQueue.pollCustomer();

            // Set to served
            customer.setServed();
            this.selfCheckoutMachines.put(checkoutHandler, Optional.of(customer));

            // Log customer who was served
            this.logger.log(String.format("%.3f %s served by %s\n", time, customer, checkoutHandler));
            this.statistics.serveOneCustomer()
                    .recordWaitingTime(customer.timeWaited(time));

            double doneTime = time + this.randomGenerator.genServiceTime();

            return Optional.of(
                    new Event[]{
                            new EventImpl(doneTime, () -> this.finishServingCustomer(doneTime, checkoutHandler))
                    }
            );
        }

        return Optional.empty();
    }

    @Override
    public boolean isServingCustomer() {
        return this.selfCheckoutMachines.values().parallelStream().anyMatch(Optional::isPresent);
    }

    @Override
    public boolean isIdle() {
        return this.selfCheckoutQueues.values().parallelStream().anyMatch(selfCheckoutQueue -> selfCheckoutQueue.getCurrentQueueLength() == 0);
    }

    @Override
    public boolean isAvailable() {
        // This means that there are machines that are available
        return this.selfCheckoutMachines.values().parallelStream().anyMatch(Optional::isEmpty);
    }

    @Override
    public boolean canAcceptCustomer() {
        return this.selfCheckoutQueues.values().parallelStream().anyMatch(CheckoutQueue::canJoinCustomerQueue);
    }

    /**
     * @param time The time which this will be executed
     * @return An array of events to be executed.
     */
    @Override
    public Optional<Event[]> finishServingCustomer(double time, CheckoutHandler selfCheckoutMachine) {
        // Retrieve customer
        final Customer customer = this.selfCheckoutMachines.get(selfCheckoutMachine).get();
        // Set customer to done
        customer.setDone();

        // Do logging and statistics
        this.logger.log(String.format("%.3f %s done serving by %s\n", time, customer, selfCheckoutMachine));

        // Set as no one being served
        this.selfCheckoutMachines.put(selfCheckoutMachine, Optional.empty());

        return this.startServingCustomer(time);
    }
}
