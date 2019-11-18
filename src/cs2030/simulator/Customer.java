package cs2030.simulator;

/**
 * The cs2030.simulator.Customer class encapsulates information and methods pertaining to a
 * cs2030.simulator.Customer in a simulation.  In Lab 4, we simplfied the class to maintaining
 * only two variables -- id and timeArrived.
 *
 * @author weitsang
 * @author atharvjoshi
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
public abstract class Customer implements HasCustomerState {
    /**
     * The unique ID of this customer.
     */
    private final int id;

    /**
     * The time this customer arrives.
     */
    private final double timeArrived;

    /**
     * The current state of the customer
     */
    private CustomerStates customerState;

    /**
     * Create and initalize a new customer.
     * The {@code id} of the customer is set.
     *
     * @param timeArrived The time this customer arrived in the simulation.
     */
    public Customer(double timeArrived, int id) {
        this.timeArrived = timeArrived;
        this.id = id;
        this.customerState = CustomerStates.ARRIVES;
    }

    /**
     * Return the waiting time of this customer.
     *
     * @return The waiting time of this customer.
     */
    public double timeWaited(double t) {
        return t - this.timeArrived;
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

    @Override
    public CustomerStates getCustomerState() {
        return this.customerState;
    }

    @Override
    public void setServed() {
        this.customerState = CustomerStates.SERVED;
    }

    @Override
    public void setWait() {
        this.customerState = CustomerStates.WAITS;
    }

    @Override
    public void setLeave() {
        this.customerState = CustomerStates.LEAVES;
    }

    @Override
    public void setDone() {
        this.customerState = CustomerStates.DONE;
    }
}
