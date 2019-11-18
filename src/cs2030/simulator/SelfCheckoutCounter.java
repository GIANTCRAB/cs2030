package cs2030.simulator;

import java.util.*;
import java.util.Map.Entry;

class SelfCheckoutCounter implements CheckoutCounter, HasManyCheckoutHandlers {
    private final SelfCheckoutQueue<Customer> selfCheckoutQueue;
    private final Map<CheckoutHandler, Optional<Customer>> selfCheckoutMachines;
    private final RandomGenerator randomGenerator;
    private final Logger logger;
    private final Statistics statistics;

    SelfCheckoutCounter(int maxQueueLength,
                        Map<CheckoutHandler, Optional<Customer>> selfCheckoutMachines,
                        RandomGenerator randomGenerator,
                        Logger logger,
                        Statistics statistics) {
        this.selfCheckoutQueue = new SelfCheckoutQueue<>(maxQueueLength);
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
        if (customer.toString().equals("14")) {
            System.out.println("add 14 to customer counter");
        }
        if (this.canAcceptCustomer()) {
            final var availCheckoutHandler = this.getQueueableCheckoutHandler();
            this.selfCheckoutQueue.joinCustomerQueue(customer);
            // Check if can serve immediately
            if (this.isAvailable(availCheckoutHandler)) {
                // checkout machine can serve right now
                return this.startServingCustomer(time);
            }

            // Set to waiting
            customer.setWait();
            // Log waiting
            this.logger.log(String.format("%.3f %s waits to be served by %s\n", time, customer, getFirstCheckoutHandler()));
        }

        return Optional.empty();
    }

    private Optional<CheckoutHandler> getAvailableCheckoutHandler() {
        return this.selfCheckoutMachines.entrySet().stream().filter(entry -> entry.getValue().isEmpty()).findFirst().map(Entry::getKey);
    }

    private CheckoutHandler getQueueableCheckoutHandler() {
        return this.getAvailableCheckoutHandler()
                .orElseGet(this::getFirstCheckoutHandler);
    }

    private CheckoutHandler getFirstCheckoutHandler() {
        return this.selfCheckoutMachines.keySet().stream().findFirst().get();
    }

    @Override
    public Optional<Event[]> startServingCustomer(double time) {
        // Check if there's queue
        if (this.selfCheckoutQueue.getCurrentQueueLength() > 0) {
            final Customer customer = this.selfCheckoutQueue.pollCustomer();
            final CheckoutHandler checkoutHandler = this.getAvailableCheckoutHandler().get();

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

    private boolean isAvailable(CheckoutHandler checkoutHandler) {
        return this.selfCheckoutQueue.getCurrentQueueLength() <= 1 && this.selfCheckoutMachines.entrySet().parallelStream().anyMatch(entry -> entry.getKey().equals(checkoutHandler) && entry.getValue().isEmpty());
    }

    @Override
    public boolean isServingCustomer() {
        return this.selfCheckoutMachines.values().parallelStream().anyMatch(Optional::isPresent);
    }

    @Override
    public boolean isIdle() {
        return this.isAvailable() && this.selfCheckoutQueue.getCurrentQueueLength() == 0;
    }

    @Override
    public boolean isAvailable() {
        // This means that there are machines that are available
        return this.selfCheckoutMachines.values().parallelStream().anyMatch(Optional::isEmpty);
    }

    @Override
    public boolean canAcceptCustomer() {
        return this.selfCheckoutQueue.canJoinCustomerQueue();
    }

    /**
     * @param time The time which this will be executed
     * @return An array of events to be executed.
     */
    @Override
    public Optional<Event[]> finishServingCustomer(double time, CheckoutHandler selfCheckoutMachine) {
        final var selfCheckOutMachineAndCustomer = this.selfCheckoutMachines.get(selfCheckoutMachine);
        if (selfCheckOutMachineAndCustomer.isPresent()) {
            // Retrieve customer
            final Customer customer = selfCheckOutMachineAndCustomer.get();
            // Set customer to done
            customer.setDone();

            if (customer.toString().equals("13")) {
                System.out.println("13 finish");
            }

            // Do logging and statistics
            this.logger.log(String.format("%.3f %s done serving by %s\n", time, customer, selfCheckoutMachine));

            // Set as no one being served
            this.selfCheckoutMachines.put(selfCheckoutMachine, Optional.empty());

            return this.startServingCustomer(time);
        }

        return Optional.empty();
    }
}
