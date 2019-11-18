package cs2030.simulator;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * A shop object maintains the list of servers and support queries
 * for server.
 *
 * @author weitsang
 * @author atharvjoshi
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
class Shop {
    /**
     * List of servers.
     */
    private final Set<CheckoutCounter> checkoutCounters;

    /**
     * Create a new shop with a given number of servers.
     *
     * @param numOfServers The number of servers.
     */
    public Shop(int numOfServers,
                int numOfSelfCheckout,
                int maxQueueLength,
                double restingProbability,
                RandomGenerator randomGenerator,
                Logger logger,
                Statistics statistics) {
        this.checkoutCounters = new LinkedHashSet<>();
        Stream.iterate(1, i -> i + 1)
                .limit(numOfServers)
                .map(num -> new ServerCounter(
                        maxQueueLength,
                        new Server(num),
                        restingProbability,
                        randomGenerator,
                        logger,
                        statistics)
                )
                .forEach(this.checkoutCounters::add);

        // Initialize self checkout
        if (numOfSelfCheckout > 0) {
            final Map<CheckoutHandler, Optional<Customer>> selfCheckoutMachines = new LinkedHashMap<>();
            final int selfCheckoutStart = numOfServers + 1;
            Stream.iterate(selfCheckoutStart, x -> x + 1)
                    .limit(numOfSelfCheckout)
                    .map(SelfCheckoutMachine::new)
                    .forEach(selfCheckoutMachine -> {
                        // Add to self checkout counter
                        selfCheckoutMachines.put(selfCheckoutMachine, Optional.empty());
                    });
            this.checkoutCounters.add(new SelfCheckoutCounter(maxQueueLength, selfCheckoutMachines, randomGenerator, logger, statistics));
        }

    }

    /**
     * Find a server matching the predicate.
     *
     * @param predicate A predicate to match the server with.
     * @return Optional.empty if no server matches the predicate, or the
     * optional of the server if a matching server is found.
     */
    public Optional<CheckoutCounter> find(Predicate<CheckoutCounter> predicate) {
        return this.checkoutCounters
                .stream()
                .filter(predicate)
                .findFirst();
    }

    public Stream<CheckoutCounter> getSortedCheckoutCounters() {
        return this.checkoutCounters.stream().sorted();
    }

    /**
     * Return a string representation of this shop.
     *
     * @return A string reprensetation of this shop.
     */
    public String toString() {
        return this.checkoutCounters.toString();
    }
}
