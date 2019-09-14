public class Customer implements Comparable<Customer> {
    private final CustomerStates currentState;
    private final Integer id;
    private final double arrivalTime;
    private static final double SERVICE_TIME = 1.0;

    public Customer(int id, double arrivalTime) {
        this(id, arrivalTime, CustomerStates.ARRIVES);
    }

    public Customer(int id, double arrivalTime, CustomerStates currentState) {
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

    public double getDoneTime() {
        return this.getArrivalTime() + this.getServiceTime();
    }

    public CustomerStates getCurrentState() {
        return currentState;
    }

    public Customer setState(CustomerStates newState) {
        return new Customer(this.getIdInt(), this.getArrivalTime(), newState);
    }

    public Customer setStateToServed() {
        return new Customer(this.getIdInt(), this.getArrivalTime(), CustomerStates.SERVED);
    }

    public Customer setStateToLeaves() {
        return new Customer(this.getIdInt(), this.getArrivalTime(), CustomerStates.LEAVES);
    }

    @Override
    public String toString() {
        return String.format("%.3f %d %s", this.getArrivalTime(), this.getIdInt(), this.getCurrentState().getStateInLowerCaseString());
    }

    @Override
    public int compareTo(Customer customer) {
        return this.getId().compareTo(customer.getId());
    }
}
