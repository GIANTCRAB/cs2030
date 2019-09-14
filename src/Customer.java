public class Customer implements Comparable<Customer> {
    private enum AVAILABLE_STATES {
        ARRIVES, SERVED, LEAVES
    }

    private final AVAILABLE_STATES currentState;
    private final Integer id;
    private final double arrivalTime;
    private static final double SERVICE_TIME = 1.0;

    public Customer(int id, double arrivalTime) {
        this(id, arrivalTime, AVAILABLE_STATES.ARRIVES);
    }

    public Customer(int id, double arrivalTime, AVAILABLE_STATES currentState) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.currentState = currentState;
    }

    public Integer getId() {
        return this.id;
    }

    private int getIdInt() {
        return this.id;
    }

    public double getServiceTime() {
        return Customer.SERVICE_TIME;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public AVAILABLE_STATES getCurrentState() {
        return currentState;
    }

    public String getCurrentStateInString() {
        return this.getCurrentState().toString().toLowerCase();
    }

    public Customer setStateToServed() {
        return new Customer(this.getIdInt(), this.getArrivalTime(), AVAILABLE_STATES.SERVED);
    }

    public Customer setStateToLeaves() {
        return new Customer(this.getIdInt(), this.getArrivalTime(), AVAILABLE_STATES.LEAVES);
    }

    @Override
    public String toString() {
        return String.format("%.3f %d %s", this.getArrivalTime(), this.getIdInt(), this.getCurrentStateInString());
    }

    @Override
    public int compareTo(Customer customer) {
        return this.getId().compareTo(customer.getId());
    }
}
