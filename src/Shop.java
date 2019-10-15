import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * A shop object maintains the list of servers and support queries
 * for server.
 *
 * @author Woo Huiren
 */
class Shop {
    /**
     * List of servers.
     */
    private final List<Server> servers;

    /**
     * Create a new shop with a given number of servers.
     *
     * @param numOfServers The number of servers.
     */
    Shop(int numOfServers) {
        this.servers = new ArrayList<>(numOfServers);
        IntStream.of(1, numOfServers + 1).forEach(consumer -> this.servers.add(new Server(numOfServers)));
    }

    Shop(List<Server> servers) {
        this.servers = servers;
    }

    /**
     * @param serverFind find using some method
     * @return the found server data
     */
    public Optional<Server> find(Predicate<? super Server> serverFind) {
        return this.servers.stream().filter(serverFind).findFirst();
    }

    /**
     * @param newServer the new server to replace the old one
     * @return the new Shop list
     */
    public Shop replace(Server newServer) {
        final List<Server> newServers = new ArrayList<>(this.servers.size());
        this.servers.forEach((server) -> {
            if (server.equals(newServer)) {
                newServers.add(newServer);
            } else {
                newServers.add(server);
            }
        });
        return new Shop(newServers);
    }

    /**
     * Return the first idle server in the list.
     *
     * @return An idle server, or {@code null} if every server is busy.
     */
    public Server findIdleServer() {
        for (Server server : this.servers) {
            if (server.isIdle()) {
                return server;
            }
        }
        return null;
    }

    /**
     * Return the first server with no waiting customer.
     *
     * @return A server with no waiting customer, or {@code null} is every
     * server already has a waiting customer.
     */
    public Server findServerWithNoWaitingCustomer() {
        for (Server server : this.servers) {
            if (!server.hasWaitingCustomer()) {
                return server;
            }
        }
        return null;
    }

    /**
     * Return a string representation of this shop.
     *
     * @return A string reprensetation of this shop.
     */
    public String toString() {
        return servers.toString();
    }
}
