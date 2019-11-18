package cs2030.simulator;

/**
 * The cs2030.simulator.SelfCheckoutMachine class keeps track of the machine's state
 *
 * @author woohuiren
 */
class SelfCheckoutMachine implements CheckoutHandler {
    /**
     * The unique ID of this machine.
     */
    private final int id;

    /**
     * Creates a machine and initalizes it with a unique id.
     */
    SelfCheckoutMachine(int id) {
        this.id = id;
    }

    /**
     * Return a string representation of this machine.
     *
     * @return A string S followed by the ID of the server, followed by the
     * waiting customer.
     */
    public String toString() {
        return "self-check " + this.id;
    }

    /**
     * Checks if two servers have the same id.
     *
     * @param obj Another objects to compared against.
     * @return true if obj is a server with the same id; false otherwise.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof SelfCheckoutMachine)) {
            return false;
        }
        return (this.id == ((SelfCheckoutMachine) obj).id);
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
}
