public class Customer {
    private final CustomerStates currentState;
    private final Integer id;
    private final double arrivalTime;
    private final double waitingTime;
    private static final double SERVICE_TIME = 1.0;

    public Customer(int id, double arrivalTime) {
        this(id, arrivalTime, CustomerStates.ARRIVES);
    }

    public Customer(int id, double arrivalTime, CustomerStates currentState) {
        this(id, arrivalTime, 0.0, currentState);
    }

    public Customer(int id, double arrivalTime, double waitingTime, CustomerStates currentState) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.waitingTime = waitingTime;
        this.currentState = currentState;
    }

    public Integer getId() {
        return this.id;
    }

    public int getIdInt() {
        return this.id;
    }

    public double getServiceTime() {
        return Customer.SERVICE_TIME;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public double getWaitingTime() {
        return this.waitingTime;
    }

    public double getServiceStartTime() {
        return this.getArrivalTime() + this.getWaitingTime();
    }

    public double getDoneTime() {
        return this.getServiceStartTime() + this.getServiceTime();
    }

    public CustomerStates getCurrentState() {
        return currentState;
    }

    public Customer setState(CustomerStates newState) {
        return new Customer(this.getIdInt(), this.getArrivalTime(), this.getWaitingTime(), newState);
    }

    @Override
    public String toString() {
        return String.format("%.3f %d %s", this.getArrivalTime(), this.getIdInt(), this.getCurrentState().getStateInLowerCaseString());
    }
}
