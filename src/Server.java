import java.util.Optional;

class Server {
    /**
     * The unique ID of this server.
     */
    private final int id;

    /**
     * The customer currently being served, if any.
     */
    private final Customer currentCustomer;

    /**
     * The customer currently waiting, if any.
     */
    private final Customer waitingCustomer;

    /**
     * Creates a server and initalizes it with a id.
     *
     * @param id The ID of the server
     */
    public Server(int id) {
        this(id, null, null);
    }

    private Server(int id, Customer currentCustomer, Customer waitingCustomer) {
        this.id = id;
        this.currentCustomer = currentCustomer;
        this.waitingCustomer = waitingCustomer;
    }

    /**
     * Change this server's state to idle by removing its current customer.
     *
     * @return A new server with the current customer removed.
     */
    public Server makeIdle() {
        return new Server(this.id, null, this.waitingCustomer);
    }

    /**
     * Checks if the current server is idle.
     *
     * @return true if the server is idle (no current customer); false otherwise.
     */
    public boolean isIdle() {
        return this.currentCustomer == null;
    }

    /**
     * Checks if there is a customer waiting for given server.
     *
     * @return true if a customer is waiting for given server; false otherwise.
     */
    public boolean hasWaitingCustomer() {
        return this.waitingCustomer != null;
    }

    /**
     * Returns waiting customer for given server.
     *
     * @return customer waiting for given server.
     */
    public Optional<Customer> getWaitingCustomer() {
        return Optional.ofNullable(this.waitingCustomer);
    }

    /**
     * Serve a customer.
     *
     * @param customer The customer to be served.
     * @return The new server serving this customer.
     */
    public Server serve(Customer customer) {
        if (customer.equals(this.waitingCustomer)) {
            return new Server(this.id, customer, null);
        }
        return new Server(this.id, customer, this.waitingCustomer);
    }

    /**
     * Make a customer wait for this server.
     *
     * @param customer The customer who will wait for this server.
     * @return The new server with a waiting customer.
     */
    public Server askToWait(Customer customer) {
        return new Server(this.id, this.currentCustomer, customer);
    }

    /**
     * Return a string representation of this server.
     *
     * @return A string S followed by the ID of the server, followed by the
     * waiting customer.
     */
    public String toString() {
        return Integer.toString(this.id);
    }

    /**
     * Checks if two servers have the same id.
     *
     * @param obj Another objects to compared against.
     * @return true if obj is a server with the same id; false otherwise.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Server)) {
            return false;
        }
        return this.id == ((Server) obj).id;
    }

    /**
     * Return the hashcode for this server.
     *
     * @return the ID of this server as its hashcode.
     */
    public int hashCode() {
        return this.id;
    }
}
