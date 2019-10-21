package lab7;

class Customer {
    /**
     * The unique ID of this customer.
     */
    private final int id;

    /**
     * The time this customer arrives.
     */
    private final double timeArrived;

    /**
     * Create and initalize a new customer.
     *
     * @param timeArrived The time this customer arrived in the simulation.
     * @param id The ID of the customer
     */
    public Customer(double timeArrived, int id) {
        this.timeArrived = timeArrived;
        this.id = id;
    }

    /**
     * Return the waiting time of this customer.
     *
     * @return The waiting time of this customer.
     */
    public double timeWaited(double t) {
        return t - timeArrived;
    }

    /**
     * Return a string representation of this customer.
     *
     * @return The id of the customer prefixed with "C"
     */
    public String toString() {
        return Integer.toString(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Customer) {
            Customer c = (Customer) obj;
            return c.id == this.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
