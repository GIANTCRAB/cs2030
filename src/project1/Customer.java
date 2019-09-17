package project1;

public class Customer {
    private CustomerStates currentState;
    private final Integer id;
    private double arrivalTime;
    private double waitingTime;
    private static final double SERVICE_TIME = 1.0;

    public Customer(int id, double arrivalTime) {
        this(id, arrivalTime, null);
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

    public double getServiceTime() {
        return Customer.SERVICE_TIME;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public double getWaitingTime() {
        return this.waitingTime;
    }

    public Customer setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
        return this;
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
        this.currentState = newState;
        return this;
    }

    @Override
    public String toString() {
        return String.format("%.3f %d %s", this.getArrivalTime(), this.getId(), this.getCurrentState().getStateInLowerCaseString());
    }
}
