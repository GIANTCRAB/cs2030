package cs2030;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;

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
    private final List<CheckoutCounter> checkoutCounters = new ArrayList<>();

    /**
     * Create a new shop with a given number of servers.
     *
     * @param numOfServers The number of servers.
     */
    Shop(int numOfServers, int numOfSelfCheckout) {
        final int MAX_WAITING_CUSTOMERS = 1;

        Stream.iterate(1, i -> i + 1)
                .map(num -> new ServerCounter(new ServerCheckoutQueue(MAX_WAITING_CUSTOMERS), new Server(num)))
                .limit(numOfServers)
                .forEach(this.checkoutCounters::add);

        // TODO: initialize self checkout
    }

    /**
     * Constructor for updated shop.
     */
    Shop(List<Server> servers) {
        this.servers = servers;
    }

    /**
     * Find a server matching the predicate.
     *
     * @param predicate A predicate to match the server with.
     * @return Optional.empty if no server matches the predicate, or the
     * optional of the server if a matching server is found.
     */
    public Optional<CheckoutCounter> find(Predicate<CheckoutCounter> predicate) {
        return this.checkoutCounters.stream()
                .filter(predicate)
                .findFirst();
    }

    /**
     * Returns a new shop when one of the server changes its state.
     */
    public Shop replace(Server server) {
        return new Shop(
                servers.stream()
                        .map(s -> (s.equals(server) ? server : s))
                        .collect(Collectors.toList())
        );
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
