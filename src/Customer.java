public class Customer implements Comparable<Customer> {
    private final Integer id;
    private final double arrivalTime;
    private static final double SERVICE_TIME = 1.0;

    public Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    }

    public Integer getId() {
        return id;
    }

    private int getIdInt() {
        return id;
    }

    public double getServiceTime() {
        return SERVICE_TIME;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public String toString() {
        return String.format("%d arrives at %.3f", this.getIdInt(), this.getArrivalTime());
    }

    @Override
    public int compareTo(Customer customer) {
        return this.getId().compareTo(customer.getId());
    }
}
