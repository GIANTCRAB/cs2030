package project2.cs2030.simulator;

import java.util.Optional;

/**
 * The project2.cs2030.simulator.Server class keeps track of who is the customer being served (if any)
 * and who is the customer waiting to be served (if any).
 *
 * @author woohuiren
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
class Server implements CheckoutHandler, HasRestState {
    /**
     * The unique ID of this server.
     */
    private final int id;

    /**
     * The states of the server
     */
    private RestStates restState;

    /**
     * Creates a server and initalizes it with a unique id.
     */
    Server(int id) {
        this(id, RestStates.SERVER_BACK);
    }

    private Server(int id, RestStates restState) {
        this.id = id;
        this.restState = restState;
    }

    /**
     * Return a string representation of this server.
     *
     * @return A string S followed by the ID of the server, followed by the
     * waiting customer.
     */
    public String toString() {
        return "server " + this.id;
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
        return (this.id == ((Server) obj).id);
    }

    /**
     * Return the hashcode for this server.
     *
     * @return the ID of this server as its hashcode.
     */
    public int hashCode() {
        return this.id;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public RestStates getRestState() {
        return this.restState;
    }

    @Override
    public Event takeRest(double doneTime) {
        this.restState = RestStates.SERVER_REST;

        return new EventImpl(doneTime, this::stopRest);
    }

    @Override
    public Optional<Event[]> stopRest() {
        this.restState = RestStates.SERVER_BACK;

        return Optional.empty();
    }
}
